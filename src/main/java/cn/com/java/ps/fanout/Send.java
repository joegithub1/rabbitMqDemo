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
* @Description: Publish/Subscribe �������ĵķ�ʽ    fanout
* Exchangeһ�����������ͣ�direct��topic��headers ��fanout
* 
* @author huangjian
* @date 2018��12��23��
*
*/
public class Send {

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connection = ConnectionUtils.getConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(QueueNameUtils.EX_LOG, BuiltinExchangeType.FANOUT);
		
		for (int i = 1; i <= 10; i++) {
			String msg = "fanout ��ʽ   msg mq "+i;
			channel.basicPublish(QueueNameUtils.EX_LOG, "", null, msg.getBytes());
			Thread.sleep(1000);
		}
		channel.close();
		connection.close();
	}
}
