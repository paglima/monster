package com.paglima.monster.configuration.orika.setting;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "orika")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrikaSetting {
    private String immutables;
}
