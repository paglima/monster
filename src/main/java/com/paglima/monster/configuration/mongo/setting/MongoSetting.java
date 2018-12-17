package com.paglima.monster.configuration.mongo.setting;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MongoSetting {

    private String writeConcern;
    private String readPreference;
    private String readPreferenceTags;
    private Integer minConnectionsPerHost;
    private Integer connectionsPerHost;
    private Integer connectTimeout;
    private Integer threadsAllowedToBlockForConnectionMultiplier;
    private Integer maxWaitTime;
    private Integer maxConnectionIdleTime;
    private Integer socketTimeout;
    private Boolean socketKeepAlive;
    private String requiredReplicaSetName;
    private String host;
    private Integer port;
    private String database;
    private String username;
    private String password;
    private String uri;

}

