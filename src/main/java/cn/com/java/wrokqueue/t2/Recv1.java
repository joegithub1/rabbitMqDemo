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
		/*�����߾ͻ����RabbitMQ����Ҫͬʱ���Ͷ����Ϣ���ң�ÿ��ֻ��1�������Ҵ����������Ϣ������ȷ����Ϣ�����ٷ�������һ����Ϣ��
		��ʱ��RabbitMQ�Ͳ�������ƽ��������Ϣ�ˣ�����Ѱ�����ŵĹ����ߡ�*/
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);//ÿ�ζ��ַ�һ����Ϣ
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
		boolean autoAck = false;//�ر��Զ�Ӧ�𣬸�Ϊ�ֶ�Ӧ��
		channel.basicConsume(QueueNameUtils.WORK_QUEUE_NAME_LX, autoAck, consumer);
		
	}
}
