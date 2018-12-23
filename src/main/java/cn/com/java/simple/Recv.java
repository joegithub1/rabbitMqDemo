package cn.com.java.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

public class Recv {

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QueueNameUtils.QUEUE_NAME_S_HELLO, false, false, false, null);
		
		Consumer consumer = new DefaultConsumer(channel){
			public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body,"GBK");
				System.out.println("Recvived :"+msg);
			};
		};
		
		channel.basicConsume(QueueNameUtils.QUEUE_NAME_S_HELLO, false, consumer);
		
	}
}
