package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer_tx {
    private static final String url="tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer  = session.createConsumer(queue);

        while (true){
            TextMessage textMessage = (TextMessage) messageConsumer.receive(3000l);
            if (null != textMessage){
                System.out.println("########接收消息"+textMessage);
//                textMessage.acknowledge();
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.commit();
        session.close();
        connection.close();

    }
}
