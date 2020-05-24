package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;

public class JmsConsumerRelivery {
//    private static final String url="nio://127.0.0.1:61618";
//    private static final String url="nio://127.0.0.1:61608";
    private static final String url="tcp://127.0.0.1:61608";
//    private static final String url="failover:(tc p://127.0.0.1:61616,tcp://127.0.0.1:61617,tcp://127.0.0.1:61618)?randomize=false";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);

        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
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
