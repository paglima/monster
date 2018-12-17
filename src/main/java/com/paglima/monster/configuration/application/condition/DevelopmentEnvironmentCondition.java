package com.paglima.monster.configuration.application.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DevelopmentEnvironmentCondition implements Condition{

    private static final String PROP = "APP_ENVIRONMENT";

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment env = conditionContext.getEnvironment();

        if (!env.containsProperty(PROP) || (env.containsProperty(PROP) && env.getProperty(PROP).toLowerCase().equals("development"))) {
            return true;
        }

        return false;
    }
}
