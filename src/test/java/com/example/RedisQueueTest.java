package com.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RedisQueueTest {

    private QueueService queueService;
    private String queueUrl = "https://us1-saving-anteater-41352.upstash.io";
    private String queueName;

    @Test
    @Before
    public void setUp(){
        queueName = "1";
        this.queueService = new RedisQueueImpl(queueUrl,queueName);
    }

    @Test
    @Before
    public void testSendMessage(){
        queueService.push(this.queueUrl,"hello");
    }


    @Test
    public void testPullMessage(){
        String msg = "hello";
        Message message = queueService.pull(queueUrl);
        assertEquals(msg,message.getBody());
    }

    public void testDeleteMessage(){
       String message = "hello";
        queueService.push(queueUrl,message);
        Message message1 = queueService.pull(queueUrl);
        assertEquals(message,message1.getBody());
        queueService.delete(queueUrl,message);
        Message message2 = queueService.pull(queueUrl);
        assertNull(message2);
    }

}
