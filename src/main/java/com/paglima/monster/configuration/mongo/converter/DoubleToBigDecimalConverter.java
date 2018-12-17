package com.paglima.monster.configuration.mongo.converter;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double source) {
        return new BigDecimal(source).setScale(2, RoundingMode.HALF_UP);
    }
}