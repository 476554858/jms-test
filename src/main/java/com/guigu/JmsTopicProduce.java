package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicProduce {
    private static final String url="tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "Topic-Persist";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic= session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();

        for(int i = 1;i<=3;i++){
           TextMessage textMessage = session.createTextMessage("text msg:"+i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("mq完成持久化消息的发送");
    }
}
