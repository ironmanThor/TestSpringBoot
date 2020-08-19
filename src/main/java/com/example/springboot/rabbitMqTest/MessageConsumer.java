package com.example.springboot.rabbitMqTest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import lombok.SneakyThrows;

public class MessageConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtil.getChannelInstance("消费者");
        Map<String, Object> arguments = new HashMap<String, Object>(16);
        // 设置队列中的消息 10s 钟后过期
        arguments.put("x-message-ttl", 10000);
 
        // 声明队列 (队列名, 是否持久化, 是否排他(是否独一队列，同一个Connection(用单例模式实现)，那么这两个消费者也是可以共享这个排他队列的), 是否自动删除, 队列属性);
        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare("test_simple_queue", false, false, false, arguments);
        //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        // 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 是否是内部交换机, 交换机属性);
       // channel.exchangeDeclare("rabbitmq.wj1", BuiltinExchangeType.TOPIC, true, false, false, new HashMap<>());
        channel.exchangeDeclare("rabbitmq.test", BuiltinExchangeType.TOPIC, true, false, false, new HashMap<>());

        // 将队列Binding到交换机上 (队列名, 交换机名, Binding key(Routing key), 绑定属性);
       // channel.queueBind(declareOk.getQueue(), "rabbitmq.wj1", "add.#", new HashMap<>());
        channel.queueBind(declareOk.getQueue(), "rabbitmq.test", "lcx", new HashMap<>());
        //解绑
        channel.queueUnbind(declareOk.getQueue(),"rabbitmq.test","lj",null);

        // 消费者订阅消息 监听如上声明的队列 (队列名, 是否自动应答(与消息可靠有关 后续会介绍), 消费者标签, 消费者)
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @SneakyThrows
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /*System.out.println(consumerTag);
                System.out.println(envelope.toString());
                System.out.println(properties.toString());*/
                System.out.println("消息内容:" + new String(body));
                Thread.sleep(5);
                //手动回执消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //自动应答关闭 防止消费者挂了生产者也没了消息 true的时候生产者发完就自动删除消息
        boolean autoAck =false;
        //每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
        channel.basicQos(1);
        channel.basicConsume(declareOk.getQueue(), autoAck, "消费者标签",defaultConsumer );
    }
}