package com.mitrais.cdc.mongodbapp.config;

import com.mitrais.cdc.mongodbapp.utility.EmailUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public EmailUtility emailUtility(){
        return new EmailUtility();
    }

   /* @Bean
    public TokenUtility tokenUtility(){
        return new TokenUtility();
    }*/
}
