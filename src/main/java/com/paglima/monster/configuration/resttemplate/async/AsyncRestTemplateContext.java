package com.paglima.monster.configuration.resttemplate.async;

import com.paglima.monster.configuration.resttemplate.setting.MicroserviceSettingFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class AsyncRestTemplateContext {

   private Map<String, AsyncRestTemplate> clients;
    private final MicroserviceSettingFactory settingFactory;
    private final AsyncRestTemplateFactory asyncRestTemplateFactory;

    @Autowired
    public AsyncRestTemplateContext(MicroserviceSettingFactory settingFactory, AsyncRestTemplateFactory asyncRestTemplateFactory) {
        this.settingFactory = settingFactory;
        this.asyncRestTemplateFactory = asyncRestTemplateFactory;
        this.clients = new HashMap<>();
    }

    public AsyncRestTemplate getFor(String ms) {

        if (!clients.containsKey(ms)) {

           /* MicroserviceSetting setting = settingFactory.get(ms);

            AsyncRestTemplate asyncRestTemplate = asyncRestTemplateFactory.create(setting.getReadTimeout(), setting.getConnectionTimeout(),
                    setting.getConnectionRequestTimeout(), setting.getMaxTotalConnections(),
                    setting.getMaxConnectionsPerRoute());

            clients.put(ms, asyncRestTemplate);

            return asyncRestTemplate;*/

           return null;

        } else {

            return clients.get(ms);

        }

    }

}
