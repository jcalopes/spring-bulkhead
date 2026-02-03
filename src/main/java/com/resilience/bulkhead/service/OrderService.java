package com.resilience.bulkhead.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

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

    @Retryable(value = TimeoutException.class,
                    maxAttempts = 3,
                    backoff = @Backoff(delay = 1000, multiplier = 1.5, random = true))
    public String getOrdersFromFailingApi() throws TimeoutException {
        log.info("Establishing http connection...");
        return this.getDetailsFromApi();
    }

    private String getDetailsFromApi() throws TimeoutException {
        throw new TimeoutException("Not Responding");
    }

    @Recover
    public String recover(TimeoutException ex) {
        log.info("API not available, retrieving fallback data from cache");
        return "Fallback Data.";
    }
}
