package com.zy.common.schedule.task;

import com.zy.common.schedule.Task;

/**
 * @author :zy
 * @创建日期: 2020年7月14日 下午10:02:31
 */
public class TestTask extends Task{


	@Override
	public String getDefaultCrontab() {
		return "0 0/1 * * * ?";//一分钟跑一次
	}

	@Override
	public void process() {
		System.out.println("TestTask is running...");
		
	}
}
