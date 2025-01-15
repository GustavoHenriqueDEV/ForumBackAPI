package com.Crudexample.crud.config;

import com.Crudexample.crud.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Você pode customizar como quiser
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Aqui definimos quais rotas são públicas e quais exigem login
                        .requestMatchers("/posts", "/posts/{id}", "/posts/{id}/comentarios").permitAll()
                        .requestMatchers("/usuarios/login", "/usuarios/register", "/respostas/{idComentario}").permitAll()
                        .anyRequest().authenticated() // o resto precisa de token
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

