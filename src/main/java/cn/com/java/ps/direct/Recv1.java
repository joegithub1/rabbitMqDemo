package cn.com.java.ps.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

public class Recv1 {

	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(QueueNameUtils.EX_COLOR, BuiltinExchangeType.DIRECT);
		String []severity = {"orange","green","blank"};
		String queueName = channel.queueDeclare().getQueue();
		for (String s : severity) {
			channel.queueBind(queueName, QueueNameUtils.EX_COLOR, s);
		}
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"GBK");
				System.out.println(envelope.getRoutingKey()+" recv msg ��"+msg);
				
			};
		};
		channel.basicConsume(queueName, true, consumer);
	}
}
