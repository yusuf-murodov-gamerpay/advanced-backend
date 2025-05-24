package com.advancedbackend.module_one.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Map;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http,
                                                                 Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter))
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/**").hasRole("USER")
                        .requestMatchers("/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter authenticationConverter(Converter<Map<String, Object>,
            Collection<GrantedAuthority>> authoritiesConverter) {

        var authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwt -> authoritiesConverter.convert(jwt.getClaims())
        );
        return authenticationConverter;
    }
}
