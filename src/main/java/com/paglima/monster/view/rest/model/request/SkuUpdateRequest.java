package com.paglima.monster.view.rest.model.request;

import com.paglima.monster.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkuUpdateRequest {

    private Long skuId;
    private Brand brand;
    private BigDecimal listPrice;
    private BigDecimal sellPrice;
    private Integer salesForecast;
    private String description;
    private String classification;
    private Boolean dynamic;
    private String substrategy;

}
