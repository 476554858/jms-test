package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_tx {
    private static final String url="tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for(int i = 1;i<=3;i++){
           TextMessage textMessage = session.createTextMessage("text msg:"+i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.commit();
        session.close();
        connection.close();

    }
}
