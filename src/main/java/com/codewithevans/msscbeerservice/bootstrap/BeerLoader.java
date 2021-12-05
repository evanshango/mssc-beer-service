package com.codewithevans.msscbeerservice.bootstrap;

import com.codewithevans.msscbeerservice.domain.Beer;
import com.codewithevans.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            loadBeerObjects();
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {

            List<Beer> beersToSave = new ArrayList<>();

            beersToSave.add(Beer
                    .builder()
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(337010000001L)
                    .price(new BigDecimal("12.95"))
                    .build()
            );

            beersToSave.add(Beer
                    .builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(337010000002L)
                    .price(new BigDecimal("11.95"))
                    .build()
            );

            beerRepository.saveAll(beersToSave);
        }
    }
}
