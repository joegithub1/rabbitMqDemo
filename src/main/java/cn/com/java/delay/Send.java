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
* @Description: 延迟队列  发送消息    对整个队列设置一个过期时间
* @author huangjian
* @date 2018年12月31日
*
*/
public class Send {

	//延迟
	private static final String DLX_QUEUE = "dlx_queue";
	private static final String DLX_EX = "dlx_exchange";
	//订单
	private static final String ORDER_QUEUE = "order_queue";
	private static final String ORDER_EX = "order_exchange";
	
	private static final String ORDER_ROUTINGKEY = "order.#";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		//-----------------------延迟队列
		channel.exchangeDeclare(DLX_EX, "topic");
		channel.queueDeclare(DLX_QUEUE, false, false, false, null);
		channel.queueBind(DLX_QUEUE, DLX_EX, ORDER_ROUTINGKEY);
		//-----------------------end
		//-----------------------订单
		channel.exchangeDeclare(ORDER_EX, "topic");
		Map<String, Object> arguments = new HashMap<String, Object>();
		//如果队列已经存在则不能修改延时时间，如需修改先删除该队列
		arguments.put("x-message-ttl", 20000);//延迟时间 10s  对整个队列设置一个过期时间
		arguments.put("x-dead-letter-exchange", DLX_EX);//延迟exchange
		channel.queueDeclare(ORDER_QUEUE, false, false, false, arguments);
		channel.queueBind(ORDER_QUEUE, ORDER_EX, ORDER_ROUTINGKEY);
		//-----------------------end
		String msg = "20191231-order-number-1";
		channel.basicPublish(ORDER_EX, "order.save", null, msg.getBytes());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date())+"  发送订单成功。。。");
		channel.close();
		connection.close();
	}
}
