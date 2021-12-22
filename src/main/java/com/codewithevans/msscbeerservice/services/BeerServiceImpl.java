package com.codewithevans.msscbeerservice.services;

import com.codewithevans.msscbeerservice.domain.Beer;
import com.codewithevans.msscbeerservice.repositories.BeerRepository;
import com.codewithevans.msscbeerservice.web.controller.NotFoundException;
import com.codewithevans.msscbeerservice.web.mappers.BeerMapper;
import com.codewithevans.brewery.model.BeerDto;
import com.codewithevans.brewery.model.BeerPagedList;
import com.codewithevans.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventoryOnHand == false")
    @Override
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
        return showInventoryOnHand ? beerMapper.beerToBeerDtoWithInventory(
                beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
        ) : beerMapper.beerToBeerDto(
                beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDto addNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beerDto))
        );
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        val beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beer.getUpc());

        return beerMapper.beerToBeerDto(
                beerRepository.save(beer)
        );
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(
            String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand
    ) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!isEmpty(beerName) && !isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!isEmpty(beerName) && isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (isEmpty(beerName) && !isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent().stream().map(beerMapper::beerToBeerDtoWithInventory).collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements()
            );
        } else {
            beerPagedList = new BeerPagedList(beerPage.getContent().stream().map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getBeerByUpc(String upc) {
        return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));
    }
}
