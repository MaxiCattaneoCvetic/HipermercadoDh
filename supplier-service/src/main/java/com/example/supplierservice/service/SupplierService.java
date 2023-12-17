package com.example.supplierservice.service;

import com.example.supplierservice.model.SupplierDTO;
import com.example.supplierservice.repository.ISupplierRepository;
import org.springframework.stereotype.Service;



@Service
public class SupplierService {
    private ISupplierRepository proveedorRepository;

    public SupplierService(ISupplierRepository userRepository) {
        this.proveedorRepository = userRepository;
    }

    public SupplierDTO findById(String id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    public boolean isInBlackList(String name) {
        return !proveedorRepository.findByUsername(name).isEmpty();
    }
}