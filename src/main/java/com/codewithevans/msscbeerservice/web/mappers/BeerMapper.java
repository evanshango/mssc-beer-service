package com.codewithevans.msscbeerservice.web.mappers;

import com.codewithevans.msscbeerservice.domain.Beer;
import com.codewithevans.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}