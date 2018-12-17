package com.paglima.monster.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sku {

    private static final String COST_PLUS = "COST_PLUS";

    private Long skuId;
    private Brand brand;
    private BigDecimal listPrice;
    private BigDecimal sellPrice;
    private Integer salesForecast;
    private String description;
    private String classification;
    private Boolean dynamic;
    private String substrategy;

    private Long sequentialId;
    private Long productId;
    private String sellerId;
    private String modality;
    private BigDecimal salesPrice;
    private BigDecimal expectedMargin;

    private List<String> schedulings;
    private List<String> promotions;
    private String pricingSummary;

    private Boolean kit;
    private Boolean gift;
    private Boolean subsidized;
    private Boolean funKitchen;
    private Boolean roundingException;

    private Long version;

    private Date createdAt;
    private Date updatedAt;


}
