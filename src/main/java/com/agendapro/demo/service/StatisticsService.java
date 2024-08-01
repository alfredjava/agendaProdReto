package com.agendapro.demo.service;



import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private int productCount = 0;

    @Async
    public void updateProductCount() {
        productCount++;
    }

    public int getProductCount() {
        return productCount;
    }
}
