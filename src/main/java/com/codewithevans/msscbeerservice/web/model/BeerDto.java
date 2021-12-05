package com.codewithevans.msscbeerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    private UUID id;
    private int version;
    private OffsetDateTime createdDate;
    private OffsetDateTime lastModified;
    private String beerName;
    private BeerStyleEnum beerStyle;
    private long upc;
    private BigDecimal price;
    private int quantityOnHand;
}
