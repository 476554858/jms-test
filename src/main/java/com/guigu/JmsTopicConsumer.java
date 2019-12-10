package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicConsumer {
    private static final String url="tcp://127.0.0.1:61616";
    private static final String TOPIC_NAME = "Topic-Persist";

    public static void main(String[] args) throws Exception{
        System.out.println("z3");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z3");

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic= session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark...");
        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("收到的持久化topic"+textMessage.getText());
            message = topicSubscriber.receive();
        }
        session.close();
        connection.close();

    }
}
