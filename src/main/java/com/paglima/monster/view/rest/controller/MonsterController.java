package com.paglima.monster.view.rest.controller;

import com.paglima.monster.configuration.orika.mapper.OMapper;
import com.paglima.monster.domain.Brand;
import com.paglima.monster.domain.Sku;
import com.paglima.monster.persistence.mongo.dao.SkuMongoDao;
import com.paglima.monster.view.rest.model.request.SkuUpdateRequest;
import com.paglima.monster.view.rest.model.response.SkuResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.paglima.monster.util.AllowHeaderUtil.allows;


@RestController
@RequestMapping("/monster")
@Slf4j
public class MonsterController {

    private final OMapper mapper;
    private final SkuMongoDao skuMongoDao;

    @Autowired
    public MonsterController(OMapper mapper, SkuMongoDao skuMongoDao) {
        this.mapper = mapper;
        this.skuMongoDao = skuMongoDao;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity rootOptions() {
        return allows(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SkuResponse postFind(@RequestBody @Valid SkuUpdateRequest request) throws Exception {
        skuMongoDao.update(mapper.map(request, Sku.class));

        log.info("Atualizado!!!");
        return mapper.map(request, SkuResponse.class);
    }

    @GetMapping(value = "/{skuId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public SkuResponse find(@PathVariable("skuId") @NotNull Long skuId, @RequestParam(value = "brand") @NotNull Brand brand) throws Exception {
        Sku skuFound = skuMongoDao.findBySkuIdAndBrand(skuId, brand);
        return mapper.map(skuFound, SkuResponse.class);
    }
}
