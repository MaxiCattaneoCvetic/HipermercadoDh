// Este componente es un interceptor de solicitudes Feign que agrega el token de acceso JWT a las solicitudes salientes
// para autenticación con servicios remotos.
package com.example.paymentservice.configuration.feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

// Componente de Spring que indica que esta clase debe ser escaneada y gestionada como un componente
// La anotación @Component indica que esta clase debe ser escaneada y gestionada como un componente por el contenedor de Spring.
@Component
public class FeignInterceptor implements RequestInterceptor {

    // Este método se ejecuta antes de enviar una solicitud Feign, permitiendo la modificación de la solicitud.
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Obtiene el token de acceso del método privado getAccessToken()
        String token = getAccessToken();

        // Si se encuentra un token, lo agrega como encabezado de autorización en la solicitud
        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }

    // Método privado utilizado para obtener el token de acceso del contexto de seguridad de Spring
    private String getAccessToken() {
        // Obtiene la información de autenticación del contexto de seguridad de Spring
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = null;

        // Verifica si la información de autenticación no es nula
        if (authentication != null) {
            try {
                // Intenta obtener el token de acceso de la autenticación JWT, lanzando una excepción si no es una JwtAuthenticationToken
                token = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
            } catch (Exception ignored) {
                // Ignora cualquier excepción (por ejemplo, si la autenticación no es de tipo JwtAuthenticationToken)
                // y establece el token como nulo en caso de error
            }
        }

        // Devuelve el token de acceso (puede ser nulo si no se encuentra)
        return token;
    }
}