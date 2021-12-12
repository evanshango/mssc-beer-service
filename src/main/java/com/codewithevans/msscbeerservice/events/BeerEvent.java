package com.codewithevans.msscbeerservice.events;

import com.codewithevans.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 3183483945979401305L;

    private final BeerDto beerDto;
}
