package com.example.paymentservice.service;

import com.example.paymentservice.repository.SupplierRepository;
import org.springframework.stereotype.Service;

// Este servicio de Spring realiza operaciones relacionadas con el pago, interactuando con el repositorio de proveedores.
@Service
public class PaymentService {
    // Inyección de dependencias del repositorio de proveedores en el servicio.
    private SupplierRepository proveedorRepository;

    // Constructor que recibe una instancia de SupplierRepository como dependencia.
    public PaymentService(SupplierRepository subscriptionRepository) {
        this.proveedorRepository = subscriptionRepository;
    }

    // Método que procesa el pago a un proveedor y devuelve un mensaje de respuesta.
    public String paySupplier(String supplierName, Integer monto){
        // Mensaje de respuesta por defecto
        String response = "payment processed";

        // Verifica si el proveedor está en la lista negra a través del repositorio de proveedores.
        if(proveedorRepository.isInBlackList(supplierName)) {
            // Si el proveedor está en la lista negra, se actualiza el mensaje de respuesta.
            response = "payment error due to blocked supplier";
        }

        // Devuelve el mensaje de respuesta final.
        return response;
    }
}
