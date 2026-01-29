package com.resilience.bulkhead.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    /**
     * Get orders from external data provider.
     * @return Orders data
     */
    @Bulkhead(name = "orderService", fallbackMethod = "fallbackOrders")
    public String getOrdersFromUnhealthyExternalApi() throws InterruptedException {
        // Simulating slowing responses with thread sleep.
        log.info("Fetching orders...");
        Thread.sleep(5000);
        return "My orders";
    }

    private String fallbackOrders(Throwable ex){
        log.warn("Workload on order service external API is high");
        return "Fallback data";
    }
}
