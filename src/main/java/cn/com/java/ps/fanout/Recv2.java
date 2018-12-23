package cn.com.java.ps.fanout;

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

public class Recv2 {

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QueueNameUtils.EX_QUEUE_NAME_T2, false, false, false, null);
		//声明路由器及类型
		channel.exchangeDeclare(QueueNameUtils.EX_LOG, BuiltinExchangeType.FANOUT);
		 //绑定队列到路由器上
		channel.queueBind(QueueNameUtils.EX_QUEUE_NAME_T2, QueueNameUtils.EX_LOG, "");
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"GBK");
				System.out.println("Recv2 msg :"+msg);
			}
		};
		channel.basicConsume(QueueNameUtils.EX_QUEUE_NAME_T2, true,consumer);
	}
}
