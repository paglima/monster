package com.paglima.monster.configuration.mongo;

import com.mongodb.*;
import com.paglima.monster.configuration.mongo.converter.BigDecimalToDoubleConverter;
import com.paglima.monster.configuration.mongo.converter.DoubleToBigDecimalConverter;
import com.paglima.monster.configuration.mongo.setting.MongoSetting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoClientOptionsFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Autowired
    private MongoSetting mongoSetting;

    @Autowired
    private MongoClientOptionsFactoryBean mongoClientOptionsFactoryBean;

    @Bean
    public MongoClientOptionsFactoryBean mongoClientOptionsFactoryBean() {

        MongoClientOptionsFactoryBean factoryBean = new MongoClientOptionsFactoryBean();

        if (mongoSetting.getWriteConcern() != null)
            factoryBean.setWriteConcern(WriteConcern.valueOf(mongoSetting.getWriteConcern()));

        if (mongoSetting.getMinConnectionsPerHost() != null)
            factoryBean.setMinConnectionsPerHost(mongoSetting.getMinConnectionsPerHost());

        if (mongoSetting.getConnectionsPerHost() != null)
            factoryBean.setConnectionsPerHost(mongoSetting.getConnectionsPerHost());

        if (mongoSetting.getConnectTimeout() != null)
            factoryBean.setConnectTimeout(mongoSetting.getConnectTimeout());

        if (mongoSetting.getThreadsAllowedToBlockForConnectionMultiplier() != null)
            factoryBean.setThreadsAllowedToBlockForConnectionMultiplier(mongoSetting.getThreadsAllowedToBlockForConnectionMultiplier());

        if (mongoSetting.getMaxWaitTime() != null)
            factoryBean.setMaxWaitTime(mongoSetting.getMaxWaitTime());

        if (mongoSetting.getMaxConnectionIdleTime() != null)
            factoryBean.setMaxConnectionIdleTime(mongoSetting.getMaxConnectionIdleTime());

        if (mongoSetting.getSocketTimeout() != null)
            factoryBean.setSocketTimeout(mongoSetting.getSocketTimeout());

        if (mongoSetting.getSocketKeepAlive() != null)
            factoryBean.setSocketKeepAlive(mongoSetting.getSocketKeepAlive());

        if (mongoSetting.getRequiredReplicaSetName() != null)
            factoryBean.setRequiredReplicaSetName(mongoSetting.getRequiredReplicaSetName());

        if (mongoSetting.getReadPreference() != null && mongoSetting.getReadPreferenceTags()!= null ) {

                List<Tag> tags = new ArrayList<>();

                Arrays.stream(mongoSetting.getReadPreferenceTags().split(",")).map(s -> {
                    String[] fields = s.split(":");
                    return new Tag(fields[0], fields[1]);
                }).forEach(tags::add);

                factoryBean.setReadPreference(ReadPreference.valueOf(mongoSetting.getReadPreference(), Arrays.asList(new TagSet(tags))));

        } else if (mongoSetting.getReadPreference() != null) {
            factoryBean.setReadPreference(ReadPreference.valueOf(mongoSetting.getReadPreference()));
        }

        //factoryBean.setHeartbeatConnectTimeout(0);
        //factoryBean.setHeartbeatFrequency(0);
        //factoryBean.setHeartbeatSocketTimeout(0);
        //factoryBean.setMaxConnectionLifeTime(0);
        //factoryBean.setMinHeartbeatFrequency(0);

        return factoryBean;
    }

    @Override
    public MongoClient mongo() throws Exception {

        MongoClientOptions options = mongoClientOptionsFactoryBean.getObject();

        List<ServerAddress> servers = new ArrayList<>();

        for (String server : mongoSetting.getHost().split(",")) {

            String[] serverAr = server.split(":");

            Integer port = 27006;

            if (serverAr.length == 1 && mongoSetting.getPort() != null) {
                port = mongoSetting.getPort();
            } else if (serverAr.length == 2 && !serverAr[1].isEmpty()) {
                port = Integer.valueOf(serverAr[1]);
            }

            servers.add(new ServerAddress(serverAr[0], port));
        }

        List<MongoCredential> credentials = new ArrayList<>();

        if (!StringUtils.isEmpty(mongoSetting.getUsername()) && !StringUtils.isEmpty(mongoSetting.getPassword())) {

            MongoCredential credential = MongoCredential.createCredential(mongoSetting.getUsername(),
                    getDatabaseName(), mongoSetting.getPassword().toCharArray());
            credentials.add(credential);
        }

        return new MongoClient(servers, credentials, options);
    }

    @Override
    protected String getDatabaseName() {
        return mongoSetting.getDatabase();
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(customConversions());
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }

    public CustomConversions customConversions() {
        return new CustomConversions(Arrays.asList(new DoubleToBigDecimalConverter(), new BigDecimalToDoubleConverter()));
    }

}
