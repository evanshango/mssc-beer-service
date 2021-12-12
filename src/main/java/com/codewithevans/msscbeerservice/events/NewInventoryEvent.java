package com.codewithevans.msscbeerservice.events;

import com.codewithevans.msscbeerservice.web.model.BeerDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent{

    private static final long serialVersionUID = 94313998454973000L;

    public NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
