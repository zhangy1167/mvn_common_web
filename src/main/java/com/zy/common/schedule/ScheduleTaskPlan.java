package com.zy.common.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zy
 * @创建日期: 2020年7月14日 下午9:18:03
 */
public class ScheduleTaskPlan {

	private List<Task> jobs = new ArrayList<Task>();

	public List<Task> getJobs() {
		return jobs;
	}

	public void setJobs(List<Task> jobs) {
		this.jobs = jobs;
	}
	
}
