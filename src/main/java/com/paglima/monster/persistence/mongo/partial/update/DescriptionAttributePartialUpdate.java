package com.paglima.monster.persistence.mongo.partial.update;

import com.paglima.monster.domain.Sku;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("mongo")
public class DescriptionAttributePartialUpdate implements AttributePartialUpdate {

    private static final String UPDATE_DESCRIPTION_DEBUG_MESSAGE = "Atualizando descricao do produto [:product_id] com o sku [sku_id] da marca [:brand] para [:description]";

    @Override
    public void update(Update update, Sku sku) {

        if (sku.getDescription() != null) {

            String description = StringEscapeUtils.unescapeJava(sku.getDescription());
            update.set("description", description);

        }


    }

}
