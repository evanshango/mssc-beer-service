package com.codewithevans.msscbeerservice.services.brewing;

import com.codewithevans.msscbeerservice.config.JmsConfig;
import com.codewithevans.brewery.model.events.BrewBeerEvent;
import com.codewithevans.msscbeerservice.repositories.BeerRepository;
import com.codewithevans.msscbeerservice.services.inventory.BeerInventoryService;
import com.codewithevans.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private BeerInventoryService inventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(
            @Qualifier("beerInventoryServiceRestTemplateImpl") BeerInventoryService beerInventoryService
    ) {
        this.inventoryService = beerInventoryService;
    }

    @Scheduled(fixedRate = 10000)
    public void checkForLowInventory() {
        var beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer quantityOnHand = inventoryService.getOnHandInventory(beer.getId());

            log.debug("Min OnHand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + quantityOnHand);

            if (beer.getMinOnHand() >= quantityOnHand) {
                jmsTemplate.convertAndSend(
                        JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent(beerMapper.beerToBeerDto(beer))
                );
            }
        });


    }
}
