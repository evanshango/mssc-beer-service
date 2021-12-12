package com.codewithevans.msscbeerservice.services.brewing;

import com.codewithevans.msscbeerservice.config.JmsConfig;
import com.codewithevans.msscbeerservice.events.BrewBeerEvent;
import com.codewithevans.msscbeerservice.events.NewInventoryEvent;
import com.codewithevans.msscbeerservice.repositories.BeerRepository;
import com.codewithevans.msscbeerservice.web.controller.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        var beerDto = event.getBeerDto();
        val beer = beerRepository.findById(beerDto.getId()).orElseThrow(NotFoundException::new);

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        var newEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH " + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newEvent);
    }
}
