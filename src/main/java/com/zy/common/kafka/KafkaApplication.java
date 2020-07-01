package com.zy.common.kafka;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  * @author :zy  * @创建日期: 2020年5月4日 下午6:16:21  
 */
public class KafkaApplication {
	private static Logger log = LoggerFactory.getLogger(KafkaApplication.class);
	private static final int THREAD_NUM = 10;
	private static ExecutorService excutor = Executors.newFixedThreadPool(THREAD_NUM);
	private static final int SIZE_NUM = 10;
	public static void main(String[] args) {
		CountDownLatch countDown = new CountDownLatch(SIZE_NUM);
		try {
			long startTime = System.currentTimeMillis();
			// 发送数据
			for (int i = 1; i <= SIZE_NUM; i++) {
				excutor.execute(new KafkaProduceService("测试数据",countDown));
			}
			countDown.await();
			long took = System.currentTimeMillis() - startTime;
			BigDecimal tps = new BigDecimal(SIZE_NUM).divide(new BigDecimal(took),4,RoundingMode.HALF_UP).multiply(new BigDecimal(1000));
			log.info("发送count:{}条消息，Took：{} ms ，tps：{}", SIZE_NUM,took,tps);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}

		// 消费数据
		KafkaConsumeService.consume();
	}

}
