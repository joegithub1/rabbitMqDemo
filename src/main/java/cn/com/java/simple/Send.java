package cn.com.java.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

/**
* @ClassName: Send
* @Description: ��ģʽ simple
* @author huangjian
* @date 2018��12��23��
*
*/
public class Send {

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		System.out.println("���ӳɹ�������");
		
		Channel channel = connection.createChannel();
		channel.queueDeclare(QueueNameUtils.QUEUE_NAME_S_HELLO, false, false, false, null);
		String msg = "Hello simple mq ������";
		channel.basicPublish("", QueueNameUtils.QUEUE_NAME_S_HELLO, null, msg.getBytes());
		channel.close();
		connection.close();
	}
}
