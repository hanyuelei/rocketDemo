package com.hanyl;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class Consumer {
	
	public static void main(String[] args) throws InterruptedException, MQClientException {
		   //��������ʼ��һ��consumer
        //��Ҫһ��consumer group������Ϊ���췽���Ĳ���������Ϊconsumer1
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer1");
        consumer.setVipChannelEnabled(false);
        //ͬ��ҲҪ����NameServer��ַ
        consumer.setNamesrvAddr("39.107.235.218:9876");

        //�������õ���һ��consumer�����Ѳ���
        //CONSUME_FROM_LAST_OFFSET Ĭ�ϲ��ԣ��Ӹö�����β��ʼ���ѣ���������ʷ��Ϣ
        //CONSUME_FROM_FIRST_OFFSET �Ӷ����ʼ��ʼ���ѣ�����ʷ��Ϣ����������broker�ģ�ȫ������һ��
        //CONSUME_FROM_TIMESTAMP ��ĳ��ʱ��㿪ʼ���ѣ���setConsumeTimestamp()���ʹ�ã�Ĭ���ǰ��Сʱ��ǰ
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
      //���ù㲥����ģʽ
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //����consumer�����ĵ�Topic��Tag��*����ȫ����Tag
        consumer.subscribe("TopicDemo", "*");

        //����һ��Listener����Ҫ������Ϣ���߼�����
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
            	try {
					for(MessageExt msg:msgs) {
						String msgbody=new String(msg.getBody(),"utf-8");
						System.out.println("������1 ���յ���Ϣ��MessageBody:"+msgbody);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
                //��������״̬
                //CONSUME_SUCCESS ���ѳɹ�
                //RECONSUME_LATER ����ʧ�ܣ���Ҫ�Ժ���������
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

		
        });
        //����start()��������consumer
        consumer.start();
        System.out.println("������׼������....%n");
	}
}
