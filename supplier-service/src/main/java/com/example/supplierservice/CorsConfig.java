package com.example.supplierservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfiguration {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir solicitudes desde todos los dominios. Puedes personalizar esto según tus necesidades.
        config.addAllowedOrigin("*");

        // Permitir los métodos HTTP que desees
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        // Permitir los encabezados que desees
        config.addAllowedHeader("*");

        // Desactivar el uso de credenciales en la solicitud (cookies, encabezados de autenticación, etc.)
        config.setAllowCredentials(false);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
