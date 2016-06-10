package com.mychat.mqtest.example.sender;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.mychat.mqtest.TestConstant;
 
/**
 * <b>function:</b> 消息发送者
 * @author hoojo
 * @createDate 2013-6-19 上午11:26:43
 * @file MessageSender.java
 * @package com.hoo.mq.jms
 * @project ActiveMQ-5.8
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class StringMessageSender {
 
    // 发送次数
    public static int SEND_NUM = 5;
    // tcp 地址
    public static String BROKER_URL = "tcp://localhost:61616";
    // 目标，在ActiveMQ管理员控制台创建 http://localhost:8161/admin/queues.jsp
    public static String DESTINATION = TestConstant.CHANNEL;
    
    public static String data = "";
    /**
     * <b>function:</b> 发送消息
     * @author hoojo
     * @createDate 2013-6-19 下午12:05:42
     * @param session
     * @param producer
     * @throws Exception
     */    
    public static void sendMessage(Session session, MessageProducer producer) throws Exception {
    	 TextMessage text = session.createTextMessage(data);
         producer.send(text);
    }
    
    public static void run() throws Exception {
        
        Connection connection = null;
        Session session = null;
        // 创建链接工厂
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
        for (int i = 0; i < SEND_NUM; i++) {
	        try {
	            // 通过工厂创建一个连接
	            connection = factory.createConnection();
	            // 启动连接
	            connection.start();
	            // 创建一个session会话
	            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
	            // 创建一个消息队列
	            Destination destination = session.createQueue(DESTINATION);
	            // 创建消息制作者
	            MessageProducer producer = session.createProducer(destination);
	            // 设置持久化模式
	            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	            sendMessage(session, producer);
	            // 提交会话
	            session.commit();
	            
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            // 关闭释放资源
	            if (session != null) {
	                session.close();
	            }
	            if (connection != null) {
	                connection.close();
	            }
	        }
        }
    }
}

