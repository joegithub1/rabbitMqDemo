package cn.com.java.ps.fanout;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

/**
* @ClassName: Send
* @Description: Publish/Subscribe 发布订阅的方式    fanout
* Exchange一共有四种类型：direct、topic、headers 和fanout
* 
* @author huangjian
* @date 2018年12月23日
*
*/
public class Send {

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(QueueNameUtils.EX_LOG, BuiltinExchangeType.FANOUT);
		
		for (int i = 1; i <= 10; i++) {
			String msg = "fanout 方式   msg mq "+i;
			channel.basicPublish(QueueNameUtils.EX_LOG, "", null, msg.getBytes());
			Thread.sleep(1000);
		}
		channel.close();
		connection.close();
	}
}
