package com.paglima.monster.persistence.mongo.partial.update;

import com.paglima.monster.domain.Sku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SellPriceAttributePartialUpdate implements AttributePartialUpdate {

    @Override
    public void update(Update update, Sku sku) {

        if (sku.getSellPrice() != null) {
            update.set("sellPrice", sku.getSellPrice());

            if (sku.getSalesForecast() != null) {
                update.set("salesForecast", sku.getSalesForecast());
            }

            if (sku.getSubstrategy() != null) {
                update.set("subStrategy", sku.getSubstrategy());
            }

        }
    }
}

