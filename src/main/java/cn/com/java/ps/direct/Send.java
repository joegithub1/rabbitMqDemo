package cn.com.java.ps.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

/**
* @ClassName: Send
* @Description: direct方式
* @author huangjian
* @date 2018年12月23日
*
*/
public class Send {

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(QueueNameUtils.EX_COLOR, BuiltinExchangeType.DIRECT);
		
		for (int i = 1; i <= 15; i++) {
			String msg = "send msg mq [direct] "+i;
			channel.basicPublish(QueueNameUtils.EX_COLOR,QueueNameUtils.ROUTINGKEY_BLANK, null, msg.getBytes());
			channel.basicPublish(QueueNameUtils.EX_COLOR,QueueNameUtils.ROUTINGKEY_GREEN, null, msg.getBytes());
			//channel.basicPublish(QueueNameUtils.EX_COLOR,QueueNameUtils.ROUTINGKEY_ORANGE, null, msg.getBytes());
			Thread.sleep(1000);
		}
		channel.close();
		connection.close();
	}
}
