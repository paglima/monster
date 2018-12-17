package com.paglima.monster.configuration.resttemplate.setting;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

public class MicroserviceSetting {

    private static final String URL = "microservice.%s.url";
    private static final String RESOURCE_STATUS = "microservice.%s.resourceStatus";
    private static final String READ_TIMEOUT = "microservice.%s.readTimeout";
    private static final String CONNECTION_TIMEOUT = "microservice.%s.connectionTimeout";
    private static final String CONNECTION_REQUEST_TIMEOUT = "microservice.%s.connectionRequestTimeout";
    private static final String MAX_TOTAL_CONNECTIONS = "microservice.%s.maxTotalConnections";
    private static final String MAX_CONNECTIONS_PER_ROUTE = "microservice.%s.maxConnectionsPerRoute";

    private static final int READ_TIMEOUT_DEFAULT = 2000;
    private static final int CONNECTION_TIMEOUT_DEFAULT = 2000;
    private static final int CONNECTION_REQUEST_TIMEOUT_DEFAULT = 2000;
    private static final int MAX_TOTAL_CONNECTIONS_DEFAULT = 200;
    private static final int MAX_CONNECTIONS_PER_ROUTE_DEFAULT = 200;

    private final Environment environment;
    private final String ms;

    public MicroserviceSetting(Environment environment, String ms) {
        this.environment = environment;
        this.ms = ms;
    }

    private String getPropertyByMs(String propertyStr) {
        return environment.getProperty(String.format(propertyStr, ms));
    }

    public String getUrl() {
        return getPropertyByMs(URL);
    }

    public String getResourceStatus() {
        return getPropertyByMs(RESOURCE_STATUS);
    }

    public int getReadTimeout() {
        return StringUtils.isNotEmpty(getPropertyByMs(READ_TIMEOUT)) ?
                Integer.valueOf(getPropertyByMs(READ_TIMEOUT)) : READ_TIMEOUT_DEFAULT;
    }

    public int getConnectionTimeout() {
        return StringUtils.isNotEmpty(getPropertyByMs(CONNECTION_TIMEOUT)) ?
                Integer.valueOf(getPropertyByMs(CONNECTION_TIMEOUT)) : CONNECTION_TIMEOUT_DEFAULT;
    }

    public int getConnectionRequestTimeout() {
        return StringUtils.isNotEmpty(getPropertyByMs(CONNECTION_REQUEST_TIMEOUT)) ?
                Integer.valueOf(getPropertyByMs(CONNECTION_REQUEST_TIMEOUT)) : CONNECTION_REQUEST_TIMEOUT_DEFAULT;
    }

    public int getMaxTotalConnections() {
        return StringUtils.isNotEmpty(getPropertyByMs(MAX_TOTAL_CONNECTIONS)) ?
                Integer.valueOf(getPropertyByMs(MAX_TOTAL_CONNECTIONS)) : MAX_TOTAL_CONNECTIONS_DEFAULT;
    }

    public int getMaxConnectionsPerRoute() {
        return StringUtils.isNotEmpty(getPropertyByMs(MAX_CONNECTIONS_PER_ROUTE)) ?
                Integer.valueOf(getPropertyByMs(MAX_CONNECTIONS_PER_ROUTE)) : MAX_CONNECTIONS_PER_ROUTE_DEFAULT;
    }


}