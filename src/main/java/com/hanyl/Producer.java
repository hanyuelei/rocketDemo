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
		
		// ��������ʼ��һ��producer
        // ��Ҫһ��producer group������Ϊ���췽���Ĳ���������Ϊproducer1
        DefaultMQProducer producer = new DefaultMQProducer("producer1");
        producer.setVipChannelEnabled(false);
        // ����NameServer��ַ,�˴�Ӧ��Ϊʵ��NameServer��ַ�������ַ֮���ã��ָ�
        // NameServer�ĵ�ַ������
        // producer.setClientIP("119.23.211.22");
        // producer.setInstanceName("Producer");
        System.out.println("#### ��ʼ����....");
        producer.setNamesrvAddr("39.107.235.218:9876");
        System.out.println("#### ���ӳɹ�...");
        
        // ����start()��������һ��producerʵ��
        producer.start();
        // ����1����Ϣ��TopicΪTopicTest��tagΪTagA����Ϣ����Ϊ��Hello RocketMQ��ƴ����i��ֵ
        try {
            // ��װ��Ϣ
            Message msg = new Message("TopicDemo",// topic
                    "TagA",// tag
                    ("Hello RocketMQ,������44444").getBytes(RemotingHelper.DEFAULT_CHARSET)// body
            );
            // ����producer��send()����������Ϣ
            // ������õ���ͬ���ķ�ʽ�����Ի��з��ؽ��
            SendResult sendResult = producer.send(msg);
            // ��ӡ���ؽ��
            System.out.println(sendResult);
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //��������Ϣ֮�󣬵���shutdown()�����ر�producer
        System.out.println("���ͳɹ�");
     
        producer.shutdown();
 
	}
}
