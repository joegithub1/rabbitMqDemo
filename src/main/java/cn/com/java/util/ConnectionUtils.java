package cn.com.java.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
* @ClassName: ConnectionUtils
* @Description: rabbitMq 连接帮助类
* @author huangjian
* @date 2018年12月23日
*
*/
public class ConnectionUtils {

	/**
	* @Title: getConnection
	* @Description: 获取rabbitmq连接 
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
