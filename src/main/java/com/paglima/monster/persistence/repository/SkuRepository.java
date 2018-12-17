package com.paglima.monster.persistence.repository;

import com.paglima.monster.domain.Brand;
import com.paglima.monster.domain.Sku;

public interface SkuRepository {

    Sku save(Sku sku);
    Sku update(Sku sku);
    Sku findBySkuIdAndBrand(Long skuId, Brand brand);
}
