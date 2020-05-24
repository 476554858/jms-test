package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import javax.jms.*;
import java.util.UUID;

public class JmsProduce_AsyncSend {
    private static final String url="tcp://127.0.0.1:61608";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception{
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);
//        activeMQConnectionFactory.setUseAsyncSend(true);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer)session.createProducer(queue);
        TextMessage message = null;

        message = session.createTextMessage("message--");
        message.setJMSMessageID(UUID.randomUUID().toString()+"-order");
        final String msgId = message.getJMSMessageID();
        activeMQMessageProducer.send(message, new AsyncCallback() {
            @Override
            public void onException(JMSException e) {
                System.out.println(msgId+" send failed");
            }

            @Override
            public void onSuccess() {
                System.out.println(msgId+" send success");
            }
        });

        session.close();
        connection.close();
        System.out.println("***消息发布到MQ完成");

    }
}
