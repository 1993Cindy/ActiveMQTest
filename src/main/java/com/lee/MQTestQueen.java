package com.lee;

import org.apache.activemq.*;

import javax.jms.*;
import javax.jms.Message;


/**
 * Created by Administrator on 2020/2/11.
 */
public class MQTestQueen {
    public static void main (String args[]) throws JMSException {
        //1、创建工厂连接对象，需要制定ip和端口号
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.108:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("test-queue");
        //6、使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7、使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!test-queue");
        //8、发送消息
        for(int i=1;i<1000;i++){
            producer.send(textMessage);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

            }
        }

        //9、关闭资源
        producer.close();
        session.close();
        connection.close();


//        try {
//            testMQProducerQueue();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
//            TestMQConsumerQueue();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
//    public static void testMQProducerQueue() throws Exception{
//        //1、创建工厂连接对象，需要制定ip和端口号
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.108:61616");
//        //2、使用连接工厂创建一个连接对象
//        Connection connection = connectionFactory.createConnection();
//        //3、开启连接
//        connection.start();
//        //4、使用连接对象创建会话（session）对象
//        Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
//        Queue queue = session.createQueue("test-queue");
//        //6、使用会话对象创建生产者对象
//        MessageProducer producer = session.createProducer(queue);
//        //7、使用会话对象创建一个消息对象
//        TextMessage textMessage = session.createTextMessage("hello!test-queue");
//        //8、发送消息
//        while(true){
//            producer.send(textMessage);
//
//            Thread.sleep(100);
//        }
//
//        //9、关闭资源
//        producer.close();
//        session.close();
//        connection.close();
//    }

    public static void TestMQConsumerQueue() throws Exception{
        //1、创建工厂连接对象，需要制定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.108:61616");
        //2、使用连接工厂创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接
        connection.start();
        //4、使用连接对象创建会话（session）对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用会话对象创建目标对象，包含queue和topic（一对一和一对多）
        Queue queue = session.createQueue("test-queue");
        //6、使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7、向consumer对象中设置一个messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {

           // @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        //8、程序等待接收用户消息
        System.in.read();
        //9、关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

}



