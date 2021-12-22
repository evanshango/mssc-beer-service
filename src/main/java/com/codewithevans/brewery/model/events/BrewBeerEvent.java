package com.codewithevans.brewery.model.events;

import com.codewithevans.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent{

    private static final long serialVersionUID = 7997004878654992184L;

    public BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
