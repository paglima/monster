package com.paglima.monster.persistence.mongo.partial.update;

import com.paglima.monster.domain.Sku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DynamicAttributePartialUpdate implements AttributePartialUpdate {

    @Override
    public void update(Update update, Sku sku) {

        if (sku.getDynamic() != null) {

            update.set("dynamic", sku.getDynamic());

        }


    }

}
