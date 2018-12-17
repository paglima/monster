package com.paglima.monster.view.rest.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.paglima.monster.domain.Brand;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "productId",
    "skuId",
    "brand",
    "sellerId",
    "description",
    "classification",
    "listPrice",
    "prices",
    "kit",
    "dynamic",
    "gift",
    "subsidized",
    "strategy",
    "cluster",
    "suggestedPrice",
    "marketingStructure",
    "priceLock"
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "productId")
@Slf4j
public class SkuResponse {

    private Long productId;
    private Long skuId;
    private BigDecimal listPrice;
    private BigDecimal sellPrice;
    private Brand brand;

    private String sellerId;
    private String description;
    private String classification;
    private BigDecimal salesPrice;
    private BigDecimal expectedMargin;
    private Integer salesForecast;
    private BigDecimal upperSellPrice;
    private BigDecimal lowerSellPrice;

    private List<String> promotions;
    private List<String> schedulings;

    private Boolean kit;

    private Boolean roundingException;
    private Boolean dynamic;
    private Boolean gift;
    private Boolean subsidized;

}
