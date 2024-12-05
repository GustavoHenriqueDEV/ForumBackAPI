package com.Crudexample.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permitir acesso tanto de localhost:3000 quanto de localhost:5173
                registry.addMapping("/posts").allowedOrigins("http://localhost:3000", "http://localhost:5173");
            }
        };
    }
}
