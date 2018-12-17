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
public class Cep {

    private String complemento;
    private String bairro;
    private String cidade;
    private String logradouro;
    private String estado;
    private String cep;

}
