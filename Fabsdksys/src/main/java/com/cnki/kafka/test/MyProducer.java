package com.cnki.kafka.test;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

//消息生成者
public class MyProducer {

	private final KafkaProducer<String, String> producer;	 
    public final static String TOPIC = "cnkitopic";
 
    //构建生产者对象
    private MyProducer() {
        Properties props = new Properties();
        //设置集群地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.25.130:9092");//多个用逗号隔开
        //设置值序列化器
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
      //设置key序列化器
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
 
        producer = new KafkaProducer<String, String>(props);
    }
 
    //生成发布消息
    public void produce() {
        int messageNo = 1;
        final int COUNT = 10;
 
        while(messageNo < COUNT) {
            String key = String.valueOf(messageNo);
            String data = String.format("hello KafkaProducer message %s", key);
            
            try {
                producer.send(new ProducerRecord<String, String>(TOPIC, data));
            } catch (Exception e) {
                e.printStackTrace();
            } 
            messageNo++;
        }
        
        producer.close();
    }
 
    public static void main(String[] args) {    	
		/*出现错误：java.io.IOException: Can't resolve address: ubuntu:9092
		     原因：原来是无法解析ubuntu.
		     解决办法：到C:\Windows\System32\drivers\etc ，打开hosts文件，添加：192.168.23.139  ubuntu
         */
        new MyProducer().produce();
    }
 
}
