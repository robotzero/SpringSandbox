package com.queen.sandbox.internal.configuration;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.queen.sandbox.Order;
import com.queen.sandbox.OrderTest;
import com.queen.sandbox.SQSMessageListener;
import com.queen.sandbox.files.FileProcessor;
import com.queen.sandbox.files.SQSProcessor;
import org.aspectj.weaver.ast.Or;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.aws.inbound.SqsMessageDrivenChannelAdapter;
import org.springframework.integration.aws.outbound.SqsMessageHandler;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToByteArrayTransformer;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.PollableChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class SandboxConfiguration {

    private static final String DIRECTORY = "/home/agnieszka/springtest/";

    @Bean
    public IntegrationFlow processFileFlow() {
        return IntegrationFlows.from("fileInputChannel")
                                .transform(fileToByteArrayTransformer())
                                .handle("fileProcessor", "process").get();
    }

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public FileToStringTransformer fileToStringTransformer() {
        return new FileToStringTransformer();
    }

    @Bean
    FileToByteArrayTransformer fileToByteArrayTransformer() {
        return new FileToByteArrayTransformer();
    }

    @Bean
    public FileProcessor fileProcessor() {
        return new FileProcessor();
    }

    @Bean
    public SQSProcessor sqsProcessor() {
        return new SQSProcessor();
    }

    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
    public MessageSource<File> fileReadingMessageSource() {
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilter(new SimplePatternFileListFilter("*.txt"));
        filters.addFilter(new AcceptOnceFileListFilter<>());

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setAutoCreateDirectory(true);
        source.setUseWatchService(true);
        source.setDirectory(new File(DIRECTORY));
        source.setFilter(filters);
        return source;
    }

    @Bean
    public PollableChannel inputChannel() {
        return new QueueChannel();
    }

//    @Bean
//    public MessageProducer sqsMessageDrivenChannelAdapter(AmazonSQSAsync amazonSqs) {
//        SqsMessageDrivenChannelAdapter adapter = new SqsMessageDrivenChannelAdapter(amazonSqs, "");
//        adapter.setOutputChannel(inputChannel());
//        adapter.setAutoStartup(true);
//        adapter.setMaxNumberOfMessages(1);
//        adapter.setSendTimeout(2000);
//        adapter.setVisibilityTimeout(200);
//        adapter.setWaitTimeOut(20);
//        return adapter;
//    }

//    @Bean
//    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
//        return new QueueMessagingTemplate(amazonSqs);
//    }

//    @Bean
//    @ServiceActivator(inputChannel = "test")
//    public MessageHandler sqsMessageHandler(QueueMessagingTemplate queueMessagingTemplate) {
//        return new SqsMessageHandler(queueMessagingTemplate);
//    }

//    @Bean
//    public SQSMessageListener sqsMessageListener(QueueMessagingTemplate queueMessagingTemplate) {
//        return new SQSMessageListener(queueMessagingTemplate, inputChannel());
//    }

    @Bean
    public List<Order> emptyOrderList() {
        return new ArrayList<>();
    }

    @Bean
    public List<Order> orderList() {
        return IntStream.range(0, 20).mapToObj(Order::new).collect(Collectors.toList());
    }

    @Bean
    public OrderTest orderTest() {
        return new OrderTest(orderList(), emptyOrderList());
    }

//    @Bean
//    public IntegrationFlow flow() {
//        return IntegrationFlows.from("inputChannel").handle("fileProcessor", "handl").get();
//    }

//    @Bean
//    public IntegrationFlow processSQSFlow() {
//        return IntegrationFlows.from("inputChannel")
//                .handle("sqsProcessor", "process").get();
//    }
}
