package com.queen.sandbox;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.util.logging.Logger;

@MessageEndpoint(value = "")
public class Endpoint {

//    @Resource
//    private Queue<Message> jobChannelQueue;

    public boolean accept(Message<?> message) {
//        Logger.getLogger().info("Channel size: {}", jobChannelQueue.size());
        System.out.println("BLAH");
        return true;
    }
}
