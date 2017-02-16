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
}
