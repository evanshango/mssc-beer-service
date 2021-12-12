package com.codewithevans.msscbeerservice.events;

import com.codewithevans.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent{

    private static final long serialVersionUID = 7997004878654992184L;

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
