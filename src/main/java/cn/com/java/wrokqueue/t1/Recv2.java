package cn.com.java.wrokqueue.t1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

public class Recv2 {
 
	public static void main(String[] args) throws IOException, TimeoutException {
		System.out.println("等待接收消息。。。");
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QueueNameUtils.WORK_QUEUE_NAME_TEST, false, false, false, null);
		
		Consumer consumer = new DefaultConsumer(channel){
			public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body,"GBK");
				System.out.println("recv2 msg:"+msg);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					
				}finally{
					System.out.println("recv2 Done ");
				}
			};
		};
		
		channel.basicConsume(QueueNameUtils.WORK_QUEUE_NAME_TEST, true, consumer);
	}
}
