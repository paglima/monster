package com.paglima.monster.persistence.mongo.document;

import com.paglima.monster.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "skus")
public class SkuDocument {

    @Id
    private String id;

    private Long skuId;
    private Brand brand;
    private BigDecimal listPrice;
    private BigDecimal sellPrice;
    private Integer salesForecast;
    private String description;
    private String classification;
    private Boolean dynamic;
    private String substrategy;

    private Long productId;
    private String sellerId;
    private Boolean kit;
    private Boolean roundingException;
    private Boolean scheduled;
    private Boolean requested;

    private List<String> promotions;
    private List<String> schedulings;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date createdAt;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date updatedAt;

    @DateTimeFormat(iso = ISO.DATE_TIME)
    private Date priceUpdatedAt;


}
