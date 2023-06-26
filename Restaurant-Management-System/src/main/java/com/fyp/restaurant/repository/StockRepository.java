package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository <Stock, UUID> {

//    public Page<Stock> findAll(Pageable pageable);

    Stock findByStockId(UUID stockId);

    Boolean existsByName(String name);
}
