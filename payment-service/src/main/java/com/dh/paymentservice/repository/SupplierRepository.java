package com.dh.paymentservice.repository;


import org.springframework.stereotype.Repository;




@Repository
public class SupplierRepository {
    private FeignSupplierRepository feignSubscriptionRepository;
    
    public SupplierRepository(FeignSupplierRepository feignSubscriptionRepository) {
        this.feignSubscriptionRepository = feignSubscriptionRepository;
    }

    public boolean isInBlackList(String supplierName){
        return feignSubscriptionRepository.isInBlackList(supplierName);
    }


}
