package com.example.supplierservice.configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// Este convertidor se encarga de transformar un objeto Jwt en un AbstractAuthenticationToken,
// enriqueciendo la información de roles y autoridades extraídas del token.
public class KeyCloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    // Convertidor predeterminado de Spring para extraer autoridades de un token JWT
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    // Método estático para extraer roles y audiencias del token JWT
    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) throws JsonProcessingException {
        // Conjunto para almacenar roles y audiencias
        Set<GrantedAuthority> resourcesRoles = new HashSet<>();
        // Objeto ObjectMapper para procesar JSON
        ObjectMapper objectMapper = new ObjectMapper();
        // Registro del módulo JavaTime para el procesamiento de fechas y horas
        objectMapper.registerModule(new JavaTimeModule());
        // Extrae roles de "resource_access" y "realm_access" del token JWT
        resourcesRoles.addAll(extractRoles("resource_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
        resourcesRoles.addAll(extractRoles("realm_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
        // Extrae audiencias del token JWT
        resourcesRoles.addAll(extractAud("aud", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get("claims")));
        return resourcesRoles;
    }

    // Método estático para extraer roles de un nodo JSON específico en el token JWT
    private static List<GrantedAuthority> extractRoles(String route, JsonNode jwt) {
        // Conjunto para almacenar roles con prefijo "ROLE_"
        Set<String> rolesWithPrefix = new HashSet<>();

        // Itera sobre los elementos del nodo especificado y extrae los roles
        jwt.path(route)
                .elements()
                .forEachRemaining(e -> e.path("roles")
                        .elements()
                        .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText())));

        // Crea una lista de autoridades a partir de los roles con prefijo
        final List<GrantedAuthority> authorityList =
                AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));

        return authorityList;
    }

    // Método estático para extraer audiencias del token JWT
    private static List<GrantedAuthority> extractAud(String route, JsonNode jwt) {
        // Conjunto para almacenar audiencias con prefijo "AUD_"
        Set<String> rolesWithPrefix = new HashSet<>();

        // Itera sobre los elementos del nodo de audiencias y agrega el prefijo "AUD_"
        jwt.path(route)
                .elements()
                .forEachRemaining(e ->rolesWithPrefix.add("AUD_" + e.asText()));

        // Crea una lista de autoridades a partir de las audiencias con prefijo
        final List<GrantedAuthority> authorityList =
                AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));

        return authorityList;
    }

    // Constructor por defecto de la clase
    public KeyCloakJwtAuthenticationConverter() {
    }

    // Método para convertir un objeto Jwt en un AbstractAuthenticationToken
    public AbstractAuthenticationToken convert(final Jwt source) {
        Collection<GrantedAuthority> authorities = null;
        try {
            // Obtiene las autoridades predeterminadas y las autoridades de recursos
            authorities = this.getGrantedAuthorities(source);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // Crea y devuelve un token de autenticación JWT
        return new JwtAuthenticationToken(source, authorities);
    }

    // Método para obtener todas las autoridades, combinando las predeterminadas y las de recursos
    public Collection<GrantedAuthority> getGrantedAuthorities(Jwt source) throws JsonProcessingException {
        // Combina las autoridades predeterminadas con las de recursos y las devuelve como un conjunto
        return (Collection) Stream.concat(this.defaultGrantedAuthoritiesConverter.convert(source).stream(), extractResourceRoles(source).stream()).collect(Collectors.toSet());
    }
}
