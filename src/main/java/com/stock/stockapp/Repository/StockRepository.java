package com.stock.stockapp.Repository;

import com.stock.stockapp.entity.StockInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockInfo,Long> {
}
