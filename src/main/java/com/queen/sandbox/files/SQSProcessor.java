package com.queen.sandbox.files;

import org.springframework.messaging.Message;

public class SQSProcessor {
    public void process(Object obj) {
        System.out.println("PROCESSING");
    }
}
