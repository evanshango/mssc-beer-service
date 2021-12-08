package com.codewithevans.msscbeerservice.services;

import com.codewithevans.msscbeerservice.domain.Beer;
import com.codewithevans.msscbeerservice.repositories.BeerRepository;
import com.codewithevans.msscbeerservice.web.controller.NotFoundException;
import com.codewithevans.msscbeerservice.web.mappers.BeerMapper;
import com.codewithevans.msscbeerservice.web.model.BeerDto;
import com.codewithevans.msscbeerservice.web.model.BeerPagedList;
import com.codewithevans.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.val;
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

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDto(
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

//        beerPagedList = new BeerPagedList(beerPage
//                .getContent().stream().map(beerMapper::beerToBeerDtoWithInventory).collect(Collectors.toList()),
//                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
//                beerPage.getTotalElements()
//        );
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
}
