package com.zy.common.kafka;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 *  * @author :zy  * @创建日期: 2020年5月4日 下午6:17:06  
 */
public class KafkaClient {
	private static final String brokerList = "192.168.0.100:9092";
	public static KafkaProducer<String, String> kafkaProducer;
	public static KafkaConsumer<String, String> kafkaConsumer;

	static {	
		getProducer();
	}

	public static KafkaProducer<String, String> getProducer() {
		if (kafkaProducer != null) {
			return kafkaProducer;
		}

		Properties props = new Properties();
		// 地址
		props.put("bootstrap.servers", brokerList);
		props.put("acks", "all");
		// 配置value的序列化类
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// 配置key的序列化类
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		kafkaProducer = new KafkaProducer<String, String>(props);

		return kafkaProducer;
	}

	public static KafkaConsumer<String, String> getConsumer(String grp) {
		Properties props = new Properties();
		props.put("bootstrap.servers", brokerList);
		props.put("group.id", grp);
		props.put("auto.offset.reset", "earliest");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		kafkaConsumer = new KafkaConsumer<String, String>(props);

		return kafkaConsumer;
	}

}
