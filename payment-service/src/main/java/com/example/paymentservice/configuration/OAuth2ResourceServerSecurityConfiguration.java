package com.example.paymentservice.configuration;

// Importa las clases necesarias de Spring Security para configurar la seguridad web y el acceso a métodos
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

// Habilita la seguridad web y la configuración global de métodos de seguridad
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfiguration {

    // Configura la seguridad HTTP
    protected void configure(HttpSecurity http) throws Exception {
        // Configura el servidor de recursos OAuth2 para usar JWT y un convertidor personalizado
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(new KeyCloakJwtAuthenticationConverter());
        // Autoriza todas las solicitudes, requiriendo autenticación para acceder
        http.authorizeRequests().anyRequest().authenticated();
    }

    // Define un bean para decodificar tokens JWT usando NimbusJwtDecoder
    @Bean
    public JwtDecoder jwtDecoder() {
        // Configura el decodificador JWT con la URL del juego de claves JWK
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/market-reino/protocol/openid-connect/certs").build();
    }

}
