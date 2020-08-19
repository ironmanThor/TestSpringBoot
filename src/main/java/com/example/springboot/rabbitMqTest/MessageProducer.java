package com.example.springboot.rabbitMqTest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
 
public class MessageProducer {
    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {
        Channel channel = ConnectionUtil.getChannelInstance("生产者");
 
        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
        channel.exchangeDeclare("rabbitmq.wj1", BuiltinExchangeType.TOPIC, true, false, false, new HashMap<>());

        // 设置消息属性 ;
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().deliveryMode(2).contentType("UTF-8").build();



        for (int i=0;i<50;i++){
            //生成者在发送消息过程中也可能出现错误或者网络延迟灯故障，导致消息未成功发送到交换机或者队列，或重复发送消息，为了解决这个问题
            channel.confirmSelect();
            String message="测试轮询发送++"+(i+1);
            //发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体)
            channel.basicPublish("rabbitmq.test","lcx",false, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

            if (!channel.waitForConfirms()){
                channel.basicPublish("rabbitmq.test","lcx",false, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            }

            System.out.println("发送了+"+message+"!!!");
            Thread.sleep(10);
        }

        //发布消息 (交换机名, Routing key, 可靠消息相关属性 后续会介绍, 消息属性, 消息体)
        //channel.basicPublish("rabbitmq.wj1", "add.test", false, basicProperties, "body中的消息内容1！".getBytes());
    }
}