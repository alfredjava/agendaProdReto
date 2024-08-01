package com.agendapro.demo.service;



import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class StatisticsService {

    private final ConcurrentMap<String, Integer> categoryProductCount = new ConcurrentHashMap<>();

    @Async
    public void updateStatistics(String category) {
        categoryProductCount.merge(category, 1, Integer::sum);
    }

    public int getProductCountByCategory(String category) {
        return categoryProductCount.getOrDefault(category, 0);
    }
}
