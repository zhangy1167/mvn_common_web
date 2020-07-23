package com.zy.common.schedule;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author :zy
 * @创建日期: 2020年7月14日 下午9:20:03
 */
@SuppressWarnings("unused")
@Component
public class ScheduleTaskInvoker {
	private static final Logger log = LoggerFactory.getLogger(ScheduleTaskInvoker.class);

	
	private static final String LOCK_NAME = "/schedule/lock";
	private static AtomicBoolean curatorLock = new AtomicBoolean(false);
	
	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(10);
	
	@Autowired
	private ScheduleTaskPlan task;
	
	@Scheduled(fixedDelay = 60000, initialDelay = 3000)//设置初始化定时执行
	public void invokeTasks() {
//		if(!curatorLock.get()) {
//			curatorLock.set(CuratorLock.lock(LOCK_NAME,1000));
//			if(curatorLock.get()) {
//				log.info("lock sucessfully lock={}",curatorLock.get());
//			}else {
//				log.info("Lock Filed lock={}",curatorLock.get());
//				return;
//			}
//		}
//		ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring.xml");
//		ScheduleTaskPlan Task = context.getBean("scheduleTaskPlan",ScheduleTaskPlan.class);
		
		List<Task> jobs = task.getJobs();
		for(Task job: jobs) {
			executor.submit(job);
		}
	}
}
