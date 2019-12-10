package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer {
    private static final String url="tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer  = session.createConsumer(queue);

        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (null!=message && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("**消费到的消息："+textMessage.getText());
                        System.out.println("消息属性是："+textMessage.getStringProperty("key"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

                if (null!=message && message instanceof MapMessage){
                    MapMessage mapMessage =  (MapMessage)message;
                    try {
                        System.out.println("**消费到的消息："+mapMessage.getString("k1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
//        messageConsumer.close();
//        session.close();
//        connection.close();

    }
}