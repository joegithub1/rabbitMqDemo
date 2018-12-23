package cn.com.java.wrokqueue.t1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

/**
* @ClassName: SendT1
* @Description: 公平分发方式
* @author huangjian
* @date 2018年12月23日
*
*/
public class SendT1 {

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QueueNameUtils.WORK_QUEUE_NAME_TEST, false, false, false, null);
		
		for (int i = 1; i <= 10; i++) {
			String msg = "send mq "+i;
			channel.basicPublish("", QueueNameUtils.WORK_QUEUE_NAME_TEST, null, msg.getBytes());
			Thread.sleep(1000);
		}
	}
}
