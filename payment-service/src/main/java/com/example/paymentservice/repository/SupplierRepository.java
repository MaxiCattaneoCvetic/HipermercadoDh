package com.example.paymentservice.repository;

import org.springframework.stereotype.Repository;


/*SupplierRepository, que actúa como un componente de repositorio de Spring y utiliza Feign para interactuar con un servicio remoto. La clase utiliza inyección de dependencias para recibir una instancia de FeignSupplierRepository en su constructor y proporciona un método para verificar si un proveedor está en la lista negra utilizando el cliente Feign.*/

// La anotación @Repository indica que esta clase es un componente de repositorio de Spring.
@Repository
public class SupplierRepository {
    // Inyección de dependencias del cliente Feign en el repositorio.
    private FeignSupplierRepository feignSubscriptionRepository;

    // Constructor que recibe una instancia de FeignSupplierRepository como dependencia.
    public SupplierRepository(FeignSupplierRepository feignSubscriptionRepository) {
        this.feignSubscriptionRepository = feignSubscriptionRepository;
    }

    // Método que utiliza el cliente Feign para verificar si un proveedor está en la lista negra.
    public boolean isInBlackList(String supplierName){
        // Llama al método definido en la interfaz FeignSupplierRepository para realizar la verificación.
        return feignSubscriptionRepository.isInBlackList(supplierName);
    }
}