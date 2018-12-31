package cn.com.java.delay;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import cn.com.java.util.ConnectionUtils;

/**
* @ClassName: DelayCustomer
* @Description: �����ӳٶ���
* @author huangjian
* @date 2018��12��31��
*
*/
public class DelayCustomer {

	//�ӳ�
	private static final String DLX_QUEUE = "dlx_queue";
	private static final String DLX_EX = "dlx_exchange";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		final Channel channel = connection.createChannel();
		channel.exchangeDeclare(DLX_QUEUE, "topic");
		channel.queueDeclare(DLX_QUEUE, false, false, false, null);
		channel.queueBind(DLX_QUEUE, DLX_EX, "");
		
		channel.basicQos(1);
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body,"GBK");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println(sdf.format(new Date())+" msg:"+msg);
				
				//�ֶ�Ӧ��
				channel.basicAck(envelope.getDeliveryTag(), false);
			};
		};
		
		channel.basicConsume(DLX_QUEUE, false, consumer);
		
	}
}
