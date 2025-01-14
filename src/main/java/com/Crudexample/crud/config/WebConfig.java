package com.Crudexample.crud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir acesso tanto de localhost:3000 quanto de localhost:5173
        registry.addMapping("/**") // Permite qualquer caminho da API
                .allowedOrigins("http://localhost:3000", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")  // Pode ser importante dependendo do seu frontend
                .allowCredentials(true); // Permitir credenciais se necessário
    }
}

