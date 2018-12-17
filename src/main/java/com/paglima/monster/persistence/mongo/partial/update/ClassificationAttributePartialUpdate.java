package com.paglima.monster.persistence.mongo.partial.update;

import com.paglima.monster.domain.Sku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClassificationAttributePartialUpdate implements AttributePartialUpdate {

    @Override
    public void update(Update update, Sku sku) {

        if (sku.getClassification() != null) {
            update.set("classification", sku.getClassification());
        }


    }

}
