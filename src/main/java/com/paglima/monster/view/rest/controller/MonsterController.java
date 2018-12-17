package com.paglima.monster.view.rest.controller;

import com.paglima.monster.configuration.orika.mapper.OMapper;
import com.paglima.monster.configuration.resttemplate.async.AsyncRestTemplateFactory;
import com.paglima.monster.domain.Brand;
import com.paglima.monster.domain.Cep;
import com.paglima.monster.domain.Sku;
import com.paglima.monster.persistence.mongo.dao.SkuMongoDao;
import com.paglima.monster.view.rest.model.request.SkuUpdateRequest;
import com.paglima.monster.view.rest.model.response.SkuResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.net.URI;

import static com.paglima.monster.util.AllowHeaderUtil.allows;


@RestController
@RequestMapping("/monster")
@Slf4j
public class MonsterController {

    private final OMapper mapper;
    private final SkuMongoDao skuMongoDao;
    private final RestTemplate restTemplate;
    private final AsyncRestTemplateFactory asyncRestTemplateFactory;

    @Autowired
    public MonsterController(OMapper mapper, SkuMongoDao skuMongoDao, RestTemplate restTemplate, AsyncRestTemplateFactory asyncRestTemplateFactory) {
        this.mapper = mapper;
        this.skuMongoDao = skuMongoDao;
        this.restTemplate = restTemplate;
        this.asyncRestTemplateFactory = asyncRestTemplateFactory;
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

    @GetMapping(value = "/cep/{cep}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Cep find(@PathVariable("cep") @NotNull Long cep) throws Exception {
        String url = "http://api.postmon.com.br/v1/cep/" + cep;

        AsyncRestTemplate asyncRestTemplate = asyncRestTemplateFactory.create(20000, 10000, 10000, 200, 200);
        ListenableFuture<ResponseEntity<Cep>> listenableFuture = asyncRestTemplate.getForEntity(url, Cep.class);

        //Cep cepResponse = restTemplate.getForObject(URI.create(url), Cep.class);
        Cep cepResponse = listenableFuture.get().getBody();

        return cepResponse;
    }
}
