# Casa Digital Hipermercado - Práctica Integradora

## Descripción

Esta práctica integradora tiene como objetivo configurar Keycloak con todas las opciones de seguridad necesarias y luego integrarlo en una solución basada en Springboot para el sistema del hipermercado Casa Digital. El sistema requiere la capacidad de pagar a los proveedores de productos de manera segura y eficiente.

## Requisitos y Objetivos

- Configurar Keycloak con opciones avanzadas de seguridad.
- Integrar Keycloak con una solución basada en Springboot.
- Mejorar la seguridad y autenticación de la versión actual de pago a proveedores.
- Gestionar sesiones de usuario de manera segura y eficiente.
- Validar proveedores a través de una lista negra.
- Implementar roles y atributos para usuarios del hipermercado y proveedores.
- Incluir un IAM para gestionar el flujo de pago a proveedores.
- Permitir la autogestión de credenciales mediante IAM e integraciones con Google y GitHub.
- Cumplir con políticas de seguridad, incluyendo autenticación de dos factores (OTP obligatorio).
- Configurar un flujo de autenticación seguro y eficiente.

## Pasos a seguir

1. **Configuración en Keycloak:**
   - Crear un reino llamado `market-reino`.
   - Crear clientes privados (`market-gateway-client`, `payment-client`, `supplier-client`).
   - Crear usuarios (`user1`, `admin1`, `proveedor`, `proveedor_block`) con contraseñas correspondientes.
   - Asociar el rol `administrador` al usuario `admin`.
   - Crear el grupo `conflictivo` y asociarlo al usuario `proveedor_block`.
   - Crear client scopes para devolver información específica.

2. **Configuración de Seguridad:**
   - Habilitar la posibilidad de dar de alta usuarios en el reino.
   - Configurar política de password según las especificaciones.
   - Configurar un correo válido para el usuario `admin`.
   - Configurar el cliente SMTP.
   - Configurar la validación de correo como acción por defecto de autenticación.
   - Configurar OTP para el usuario `admin` desde el perfil de cuenta.
   - Configurar el alta con OTP en el flujo de autenticación del reino.
   - Modificar el flujo de login para solicitar el segundo factor de autenticación.

3. **Integración con Google y GitHub:**
   - Configurar autenticación de usuarios con Google.
   - Configurar autenticación de usuarios con GitHub.

4. **Configuración de la Solución en Springboot:**
   - Descargar los servicios (`payment-service`, `market-api-gateway`, `supplier-data-service`).
   - Agregar configuración de cada cliente creado en Keycloak.

5. **Modificaciones en Servicios Springboot:**
   - Modificar `payment-service` para invocar de forma segura la API `/supplier/blacklist/` del `supplier-data-service` mediante Feign.
   - Modificar el repositorio de proveedores en `supplier-data-service` para determinar la pertenencia a un grupo conflictivo usando la librería de rest client de Keycloak.
   - Modificar `market-api-gateway` para garantizar la autenticación antes de acceder a los endpoints correspondientes.

6. **Ejecución:**
   - Ejecutar desde el `gateway` el servicio de pago a proveedor, parametrizando proveedores que pertenezcan al grupo conflictivo y otros que no (`/pay/{supplier}/{amount}`).

