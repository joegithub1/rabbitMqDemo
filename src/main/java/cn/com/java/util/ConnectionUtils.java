package cn.com.java.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
* @ClassName: ConnectionUtils
* @Description: rabbitMq ���Ӱ�����
* @author huangjian
* @date 2018��12��23��
*
*/
public class ConnectionUtils {

	/**
	* @Title: getConnection
	* @Description: ��ȡrabbitmq���� 
	* @param @return
	* @param @throws IOException
	* @param @throws TimeoutException
	* @return Connection 
	* @throws
	*/
	public static Connection getConnection() throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setVirtualHost("/vhost_test");
		factory.setUsername("user_joe");
		factory.setPassword("123456");
		
		return factory.newConnection();
	}
	
}
