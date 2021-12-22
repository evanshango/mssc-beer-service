package com.codewithevans.msscbeerservice.services;

import com.codewithevans.brewery.model.BeerDto;
import com.codewithevans.brewery.model.BeerPagedList;
import com.codewithevans.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto addNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventoryOnHand);

    BeerDto getBeerByUpc(String upc);
}
