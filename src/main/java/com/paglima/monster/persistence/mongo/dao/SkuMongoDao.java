package com.paglima.monster.persistence.mongo.dao;

import com.paglima.monster.configuration.orika.mapper.OMapper;
import com.paglima.monster.domain.Brand;
import com.paglima.monster.domain.Sku;
import com.paglima.monster.persistence.mongo.document.SkuDocument;
import com.paglima.monster.persistence.mongo.partial.update.AttributePartialUpdate;
import com.paglima.monster.persistence.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class SkuMongoDao implements SkuRepository {

    private final OMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final List<AttributePartialUpdate> partials;

    @Autowired
    public SkuMongoDao(MongoTemplate mongoTemplate, OMapper mapper, List<AttributePartialUpdate> partials) {
        this.mapper = mapper;
        this.mongoTemplate = mongoTemplate;
        this.partials = partials;
    }

    @Override
    public Sku save(Sku sku) {
        SkuDocument document = mapper.map(sku, SkuDocument.class);
        document.setUpdatedAt(new Date());

        mongoTemplate.save(document);
        return sku;
    }

    @Override
    public Sku update(Sku sku) {
        try {

            Criteria c = Criteria.where("skuId").is(sku.getSkuId()).and("brand").is(sku.getBrand().name());
            Query query = query(c);
            Update update = new Update();

            partials.forEach(u -> u.update(update, sku));

            SkuDocument skuDocument = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), SkuDocument.class);

            return mapper.map(skuDocument, Sku.class);

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Sku findBySkuIdAndBrand(Long skuId, Brand brand) {
        Criteria c = Criteria.where("skuId").is(skuId).and("brand").is(brand.name());
        Query query = query(c);

        SkuDocument skuFound = mongoTemplate.findOne(query, SkuDocument.class);

        if (skuFound == null) {
            return null;
        }

        return mapper.map(skuFound, Sku.class);
    }

    /*public Sku update(Sku request, Sku persisted) {


        try {

            Criteria c = getIdentifierCriteria(request.getSkuId(), request.getBrand(), request.getSellerId());

            Query query = query(c);

            Update update = new Update();

            partialUpdates.forEach(u -> u.update(update, request, persisted));

            SkuDocument skuDocument = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), SkuDocument.class);

            return mapper.map(skuDocument, Sku.class);

        } catch (Exception e) {
            throw e;
        }

    }*/
}
