package com.Crudexample.crud.security;

import com.Crudexample.crud.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Ler header Authorization
        String authorizationHeader = request.getHeader("Authorization");

        // 2. Se não existir ou não for Bearer, deixa passar
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token
        String token = authorizationHeader.substring(7);

        String role;
        String userId;
        try {
            // 4. Valida o token e extrai Claims
            Claims claims = jwtService.validateToken(token);
            role = (String) claims.get("role");
            // 5. Pega o userId (subject) e outras infos que você colocou como claim
            userId = claims.getSubject();
            // Exemplo: se você também guardou 'role', poderia pegar:
            // String role = (String) claims.get("role");

            // 6. Cria o objeto de autenticação do Spring Security
            //    Por simplicidade, sem roles/perfis:
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,        // principal
                            null,          // credentials
                            new ArrayList<>() // authorities (vazio ou poderia adicionar roles)
                    );

            // 7. Registra no SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            // Token inválido ou expirado → 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 8. Continua o fluxo
        filterChain.doFilter(request, response);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ADMIN".equals(role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

// Cria o objeto de autenticação com roles
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId, // principal (ID)
                        null,   // credentials
                        authorities
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
