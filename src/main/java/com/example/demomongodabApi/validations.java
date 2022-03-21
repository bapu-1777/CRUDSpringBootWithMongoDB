package com.example.demomongodabApi;

import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class validations {

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(){
        return new ValidatingMongoEventListener(validatorFactoryBean());
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        return new LocalValidatorFactoryBean();

    }
}
