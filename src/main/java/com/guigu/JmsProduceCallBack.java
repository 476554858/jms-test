package com.guigu;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

public class JmsProduceCallBack {
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
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer)session.createProducer(queue);
        activeMQMessageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for(int i = 1;i<=3;i++){
           TextMessage textMessage = session.createTextMessage("text msg:"+i);
           textMessage.setStringProperty("key","vip");
           final String messageID = UUID.randomUUID()+"order";
           textMessage.setJMSMessageID(messageID);
           activeMQMessageProducer.send(textMessage, new AsyncCallback() {
               public void onSuccess() {
                   System.out.println(messageID+"send success");
               }

               public void onException(JMSException e) {
                   System.out.println(messageID+"send faild");
               }
           });

        }
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("***消息发布到MQ完成");

    }
}
