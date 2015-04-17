package com.adam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AdamConfig {

    @Bean
    public RestTemplate transferService() {
        return new RestTemplate();
    }

}
