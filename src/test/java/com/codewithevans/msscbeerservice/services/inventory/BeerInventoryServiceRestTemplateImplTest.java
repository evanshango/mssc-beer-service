package com.codewithevans.msscbeerservice.services.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Qualifier("beerInventoryServiceRestTemplateImpl")
    @Autowired
    BeerInventoryService beerInventoryService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getOnHandInventory() {
        // todo evolve to use UPC
//        Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
//        System.out.println(qoh);
    }
}