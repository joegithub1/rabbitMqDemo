package cn.com.java.ps.topics;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.BuiltinExchangeType;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

/**
* @ClassName: Send
* @Description: TODO
* @author huangjian
* @date 2018年12月23日
*
*/
public class Send {

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(QueueNameUtils.EX_TOPICS_NAME, BuiltinExchangeType.TOPIC);
		//String queueName = channel.queueDeclare().getQueue();
		//定义路由键和消息
        String routingKey[] = {"name.zhang.name","name.li.name","age.zhang.age","age.li.age"};
        
		for (String rk : routingKey) {
			String message = "hello  from "+rk;
			channel.basicPublish(QueueNameUtils.EX_TOPICS_NAME, rk, null, message.getBytes());
			System.out.println(rk +" send msg :"+message);
		}
		channel.close();
		connection.close();
	}
}
