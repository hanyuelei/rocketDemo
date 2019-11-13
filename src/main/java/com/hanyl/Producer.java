package com.hanyl;

import java.io.UnsupportedEncodingException;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Producer {

	public static void main(String[] args) throws MQClientException, InterruptedException{
//		   Consumer2 consumer = new Consumer2();
//	 		consumer.consumer();
		
		// 声明并初始化一个producer
        // 需要一个producer group名字作为构造方法的参数，这里为producer1
        DefaultMQProducer producer = new DefaultMQProducer("producer1");
        producer.setVipChannelEnabled(false);
        // 设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        // NameServer的地址必须有
        // producer.setClientIP("119.23.211.22");
        // producer.setInstanceName("Producer");
        System.out.println("#### 开始连接....");
        producer.setNamesrvAddr("39.107.235.218:9876");
        System.out.println("#### 连接成功...");
        
        // 调用start()方法启动一个producer实例
        producer.start();
        // 发送1条消息到Topic为TopicTest，tag为TagA，消息内容为“Hello RocketMQ”拼接上i的值
        try {
            // 封装消息
            Message msg = new Message("TopicDemo",// topic
                    "TagA",// tag
                    ("Hello RocketMQ,你好你好44444").getBytes(RemotingHelper.DEFAULT_CHARSET)// body
            );
            // 调用producer的send()方法发送消息
            // 这里调用的是同步的方式，所以会有返回结果
            SendResult sendResult = producer.send(msg);
            // 打印返回结果
            System.out.println(sendResult);
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //发送完消息之后，调用shutdown()方法关闭producer
        System.out.println("发送成功");
     
        producer.shutdown();
 
	}
}
