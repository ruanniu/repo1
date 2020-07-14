package com.cnki.kafka.test;
import org.apache.kafka.clients.consumer.*;

import java.util.Arrays;
import java.util.Properties;
public class MyConsumer {
	 private Consumer<String, String> consumer;	 
	    private static String group = "group-1";	 
	    private static String TOPIC = "cnkitopic";	 
	    private MyConsumer() {
	        Properties props = new Properties();
	        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.25.130:9092");
	        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
	        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
	       
	        //位移提交：设置自动commit
	        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true"); 
	        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // 自动commit的间隔
	      
	        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
	        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	        consumer = new KafkaConsumer<String, String>(props);
	    }
	 
	    private void consume() {
	        consumer.subscribe(Arrays.asList(TOPIC)); // 可消费多个topic,组成一个list
	 
	        while (true) {
	            ConsumerRecords<String, String> records = consumer.poll(1000);
	            for (ConsumerRecord<String, String> record : records) {
	                System.out.printf("offset = %d, key = %s, value = %s \n", record.offset(), record.key(), record.value());
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	 
	    public static void main(String[] args) {
	        new MyConsumer().consume();
	    }
}
