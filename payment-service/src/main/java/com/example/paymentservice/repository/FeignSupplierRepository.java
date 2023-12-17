package com.example.paymentservice.repository;


import com.example.paymentservice.configuration.feign.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// La anotación @FeignClient se utiliza para declarar una interfaz Feign en Spring Cloud.
// Este cliente Feign se comunica con el servicio "subscription-service".
@FeignClient(name = "supplier-client", url = "http://localhost:8083", configuration = FeignInterceptor.class)
public interface FeignSupplierRepository {

    // La anotación @RequestMapping se utiliza para mapear solicitudes HTTP a métodos de Java.
    // El método isInBlackList mapea una solicitud HTTP GET a la URL "/supplier/blacklist/{name}".
    // Este método se utiliza para consultar si un proveedor está en la lista negra.
    // Acepta una variable de ruta ("name") y devuelve un valor booleano.


    @RequestMapping(method = RequestMethod.GET, value = "/supplier/blacklist/{name}")
    boolean isInBlackList(@PathVariable String name);
}
