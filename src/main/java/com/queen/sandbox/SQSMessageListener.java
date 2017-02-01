package com.queen.sandbox;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import javax.annotation.PostConstruct;

public class SQSMessageListener {

    private final QueueMessagingTemplate template;
    private final PollableChannel channel;

    public SQSMessageListener(QueueMessagingTemplate template, PollableChannel channel) {
        this.template = template;
        this.channel = channel;
    }

//    @SqsListener("")
//    public void queueListener(Object haha) {
//        System.out.println("TESTING");
//    }

    @PostConstruct
    public void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(20000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("SENDING MESSSAGGGEEEE!");
                Message message = new GenericMessage<>("HELLO");
                template.setDefaultDestinationName("");
                template.send(message);
                System.out.println("MESSAGE SEND");
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(50000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(channel.receive());
            }
        }).start();
    }
}
