package com.codewithevans.msscbeerservice.services;

import com.codewithevans.msscbeerservice.web.model.BeerDto;
import com.codewithevans.msscbeerservice.web.model.BeerPagedList;
import com.codewithevans.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto addNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest of, Boolean showInventoryOnHand);

    BeerDto getBeerByUpc(String upc);
}
