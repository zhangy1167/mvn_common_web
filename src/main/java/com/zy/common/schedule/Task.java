package com.zy.common.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  * @author :zy  * @创建日期: 2020年7月14日 下午8:27:28  
 */

public abstract class Task implements Callable<Boolean> {
	private static final Logger log = LoggerFactory.getLogger(Task.class);

	private String crontab;
	private boolean initlize = false;
	private boolean initlized = false;

	protected boolean isSatisfiedNow(String cron) {
		return isSatisfiedNow(cron, "yyyy-MM-dd HH:mm");
	}

	protected boolean isSatisfiedNow(String cron, String format) {

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			String strDateTime = dateFormat.format(new Date());
			Date dateTime = dateFormat.parse(strDateTime);

			return isSatisfied(cron, dateTime);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isSatisfied(String cron, Date dateTime) throws Exception {

		boolean result = false;
		CronExpression expression = new CronExpression(cron);
		result = expression.isSatisfiedBy(dateTime);
		return result;
	}

	@Override
	public Boolean call() {
		log.debug("{} () start", Thread.currentThread().getStackTrace()[1].getMethodName());
		boolean result = false;
		String crontab = getCrontab();

		if (initlize && !initlized) {
			result = true;
			initlized = true;
			log.info("Job initialed:jobName={},crontab={}", this.getClass().getName(), crontab);
		} else {
			result = isSatisfiedNow(crontab);
		}

		if (result) {
			log.info("Job Executed: JobName={}, Crontab={}", this.getClass().getName(), crontab);
			long startTime = System.currentTimeMillis();

			try {
				process();
				result = true;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			log.info("Took={}ms", System.currentTimeMillis() - startTime);

		}
		return result;
	}

	// 具体任务执行操作
	public abstract void process();

	public abstract String getDefaultCrontab();

	public String getCrontab() {
		this.crontab = getDefaultCrontab();
		return crontab;
	}

	public void setCrontab(String crontab) {
//		if(!getDefaultCrontab().isEmpty() || null != getDefaultCrontab()) {
//			this.crontab = getDefaultCrontab();
//		}
		this.crontab = crontab;
	}

	public boolean isInitlize() {
		return initlize;
	}

	public void setInitlize(boolean initlize) {
		this.initlize = initlize;
	}

	public boolean isInitlized() {
		return initlized;
	}

	public void setInitlized(boolean initlized) {
		this.initlized = initlized;
	}
}
