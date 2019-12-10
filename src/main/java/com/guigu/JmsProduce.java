package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {
    private static final String url="tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for(int i = 1;i<=3;i++){
           TextMessage textMessage = session.createTextMessage("text msg:"+i);
           textMessage.setStringProperty("key","vip");
           messageProducer.send(textMessage);


           MapMessage mapMessage = session.createMapMessage();
           mapMessage.setString("k1","mapMessge---v");
           messageProducer.send(mapMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("***消息发布到MQ完成");

    }
}
