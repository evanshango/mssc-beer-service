package com.codewithevans.brewery.model.events;

import com.codewithevans.brewery.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 3183483945979401305L;

    private BeerDto beerDto;
}
