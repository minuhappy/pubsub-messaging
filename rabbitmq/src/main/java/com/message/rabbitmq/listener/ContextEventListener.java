package com.message.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.message.rabbitmq.consumer.SimpleConsumer;
import com.message.rabbitmq.util.ThreadUtil;


@Component
public class ContextEventListener {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpleConsumer simpleConsumer;

    @EventListener(ContextRefreshedEvent.class)
    public void handleContextRefreshed() {
        try {
            ThreadUtil.doAsynch(simpleConsumer::run);
        } catch (Exception e) {
            log.error("Error occurred when handle contextRefreshed.", e);
        }
    }
}