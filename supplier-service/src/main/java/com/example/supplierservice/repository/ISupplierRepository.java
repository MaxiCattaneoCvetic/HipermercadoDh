package com.example.supplierservice.repository;


import com.example.supplierservice.model.SupplierDTO;

import java.util.List;
import java.util.Optional;

public interface ISupplierRepository {

    Optional<SupplierDTO> findById(String id);

    List<SupplierDTO> findByUsername(String username);
}