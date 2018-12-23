package cn.com.java.wrokqueue.t2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.sun.xml.internal.ws.api.model.wsdl.editable.EditableWSDLBoundFault;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

public class Recv1 {

	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();
		channel.queueDeclare(QueueNameUtils.WORK_QUEUE_NAME_LX, false, false, false, null);
		/*工作者就会告诉RabbitMQ：不要同时发送多个消息给我，每次只发1个，当我处理完这个消息并给你确认信息后，你再发给我下一个消息。
		这时候，RabbitMQ就不会轮流平均发送消息了，而是寻找闲着的工作者。*/
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);//每次都分发一个消息
		Consumer consumer = new DefaultConsumer(channel){
			public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
				String msg = new String(body,"GBK");
				System.out.println("Recv1 msg :"+msg);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}finally{
					System.out.println("Recv1 done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			};
		};
		boolean autoAck = false;//关闭自动应答，改为手动应答
		channel.basicConsume(QueueNameUtils.WORK_QUEUE_NAME_LX, autoAck, consumer);
		
	}
}
