package cn.com.java.wrokqueue.t2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import cn.com.java.util.ConnectionUtils;
import cn.com.java.util.QueueNameUtils;

/**
* @ClassName: SendT2
* @Description: 轮询分发
* @author huangjian
* @date 2018年12月23日
*
*/
public class SendT2 {

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QueueNameUtils.WORK_QUEUE_NAME_LX, false, false, false, null);
		for (int i = 1; i <= 15; i++) {
			String msg = "公平分发   msg "+i;
			channel.basicPublish("", QueueNameUtils.WORK_QUEUE_NAME_LX, null, msg.getBytes());
			Thread.sleep(1000);
		}
		
	}
}
