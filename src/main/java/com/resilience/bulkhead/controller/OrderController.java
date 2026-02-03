package com.resilience.bulkhead.controller;

import com.resilience.bulkhead.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @RequestMapping("/details")
    public ResponseEntity<String> getOrdersDetails() throws TimeoutException {
       final String orders = orderService.getOrdersFromFailingApi();
       return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getOrders() throws InterruptedException {
       final String orders = orderService.getOrdersFromUnhealthyExternalApi();
       return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String> createOrders() throws InterruptedException {
       // To be implemented
       return new ResponseEntity<>("To be implemented", HttpStatus.OK);
   }
}
