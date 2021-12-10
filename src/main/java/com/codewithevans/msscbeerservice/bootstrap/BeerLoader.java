package com.codewithevans.msscbeerservice.bootstrap;

import com.codewithevans.msscbeerservice.domain.Beer;
import com.codewithevans.msscbeerservice.repositories.BeerRepository;
import com.codewithevans.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

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
                    .beerStyle(BeerStyleEnum.IPA.name())
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .build()
            );

            beersToSave.add(Beer
                    .builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PILSNER.name())
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("11.95"))
                    .build()
            );

            beersToSave.add(Beer
                    .builder()
                    .beerName("No Hammers On The Bar")
                    .beerStyle(BeerStyleEnum.PALE_ALE.name())
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("11.95"))
                    .build()
            );

            beerRepository.saveAll(beersToSave);
        }
    }
}
