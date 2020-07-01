package com.zy.common.kafka;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  * @author :zy  * @创建日期: 2020年5月4日 下午6:44:25  
 */
public class KafkaProduceService implements Runnable {
	private static Logger log = LoggerFactory.getLogger(KafkaProduceService.class);
	private static final String TOPIC_NAME = "zytopic";
	String message = "";
	CountDownLatch countDown;

	public KafkaProduceService(String message, CountDownLatch countDown) {
		super();
		this.message = message;
		this.countDown = countDown;
	}

	public void send(String message) {

		KafkaClient.getProducer().send(new ProducerRecord<String, String>(TOPIC_NAME, "", message), new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					log.error("kafka send error msg:{}", exception.getMessage());
				}
			}

		});
		log.info("kafka send msg:{}", message);

	}

	@Override
	public void run() {

		String msg = "kafka发送：" + message + "!";
		send(msg);
		countDown.countDown();

	}

}
