package com.zy.common.kafka;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :zy
 * @创建日期: 2020年5月4日 下午7:14:42
 */
public class KafkaConsumeService {
	private static Logger log = LoggerFactory.getLogger(KafkaConsumeService.class);
	private static final String TOPIC_NAME = "zytopic";
	private static final String groupId = "zy_grp";
	
	public static void consume(){
		KafkaConsumer<String, String> consumer = KafkaClient.getConsumer(groupId);
		consumer.subscribe(Arrays.asList(TOPIC_NAME));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
            	log.info("kafka recieve msg = {}", record.value());
            	System.out.println("*&*******************************"+ record.value());
            }
        }
    }

}
