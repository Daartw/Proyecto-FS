package com.mollystore.ventas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${sincronizacion.service.url}")
    private String sincronizacionUrl;

    @Bean
    public WebClient sincronizacionWebClient() {
        return WebClient.builder()
                .baseUrl(sincronizacionUrl)
                .build();
    }
}
