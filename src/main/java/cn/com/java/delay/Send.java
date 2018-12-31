package cn.com.java.delay;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.com.java.util.ConnectionUtils;

/**
* @ClassName: Send
* @Description: �ӳٶ���  ������Ϣ    ��������������һ������ʱ��
* @author huangjian
* @date 2018��12��31��
*
*/
public class Send {

	//�ӳ�
	private static final String DLX_QUEUE = "dlx_queue";
	private static final String DLX_EX = "dlx_exchange";
	//����
	private static final String ORDER_QUEUE = "order_queue";
	private static final String ORDER_EX = "order_exchange";
	
	private static final String ORDER_ROUTINGKEY = "order.#";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		//-----------------------�ӳٶ���
		channel.exchangeDeclare(DLX_EX, "topic");
		channel.queueDeclare(DLX_QUEUE, false, false, false, null);
		channel.queueBind(DLX_QUEUE, DLX_EX, ORDER_ROUTINGKEY);
		//-----------------------end
		//-----------------------����
		channel.exchangeDeclare(ORDER_EX, "topic");
		Map<String, Object> arguments = new HashMap<String, Object>();
		//��������Ѿ����������޸���ʱʱ�䣬�����޸���ɾ���ö���
		arguments.put("x-message-ttl", 20000);//�ӳ�ʱ�� 10s  ��������������һ������ʱ��
		arguments.put("x-dead-letter-exchange", DLX_EX);//�ӳ�exchange
		channel.queueDeclare(ORDER_QUEUE, false, false, false, arguments);
		channel.queueBind(ORDER_QUEUE, ORDER_EX, ORDER_ROUTINGKEY);
		//-----------------------end
		String msg = "20191231-order-number-1";
		channel.basicPublish(ORDER_EX, "order.save", null, msg.getBytes());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date())+"  ���Ͷ����ɹ�������");
		channel.close();
		connection.close();
	}
}
