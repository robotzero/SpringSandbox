package com.queen.sandbox.controller;

import com.queen.sandbox.OrderTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final OrderTest orderTest;

    public TestController(OrderTest orderTest) {
        this.orderTest = orderTest;
    }

    @GetMapping("/")
    public ResponseEntity<Void> get() {
        System.out.println(this.orderTest.getMaxAndAdd());
        System.out.println(this.orderTest.getMaxAndAddEmpty());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
