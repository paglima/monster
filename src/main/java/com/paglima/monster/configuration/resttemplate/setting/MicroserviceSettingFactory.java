package com.paglima.monster.configuration.resttemplate.setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MicroserviceSettingFactory {

    private final Environment environment;

    @Autowired
    public MicroserviceSettingFactory(Environment environment) {
        this.environment = environment;
    }

    public MicroserviceSetting get(String ms) {
        return new MicroserviceSetting(environment, ms);
    }

}
