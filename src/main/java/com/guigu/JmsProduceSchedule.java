package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

public class JmsProduceSchedule {
//    private static final String url="nio://127.0.0.1:61618";
//    private static final String url="nio://127.0.0.1:61608";
    private static final String url="tcp://127.0.0.1:61608";
//    private static final String url="failover:(tcp://127.0.0.1:61616,tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=false";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        activeMQConnectionFactory.setUseAsyncSend(true);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //投递延迟时间
        long delay = 3 * 1000;
        //重复投递的时间间隔
        long period = 4 * 1000;
        //重复投递次数
        int repeat = 5;
        TextMessage textMessage = session.createTextMessage("text msg:");

        textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
        textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
        textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);

        messageProducer.send(textMessage);

        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("***消息发布到MQ完成");

    }
}
