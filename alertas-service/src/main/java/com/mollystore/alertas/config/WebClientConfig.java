package com.mollystore.alertas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${jugadores.service.url}")
    private String jugadoresUrl;

    @Bean
    public WebClient jugadoresWebClient() {
        return WebClient.builder()
                .baseUrl(jugadoresUrl)
                .build();
    }
}
