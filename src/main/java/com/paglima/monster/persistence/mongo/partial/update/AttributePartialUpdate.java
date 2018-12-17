package com.paglima.monster.persistence.mongo.partial.update;

import com.paglima.monster.domain.Sku;
import org.springframework.data.mongodb.core.query.Update;

public interface AttributePartialUpdate {
    void update(Update update, Sku sku);
}
