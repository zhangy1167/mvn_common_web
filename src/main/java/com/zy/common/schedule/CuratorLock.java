package com.zy.common.schedule;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  * @author :zy  * @创建日期: 2020年7月15日 下午8:27:25  
 */
public class CuratorLock {

	private static final Logger log = LoggerFactory.getLogger(CuratorLock.class);

	private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
	private static CuratorFramework client;

	private static ConcurrentMap<String, InterProcessMutex> map = new ConcurrentHashMap<>();

	static {
		String zkList = "zk.address";
		client = CuratorFrameworkFactory.newClient(zkList, retryPolicy);
		client.start();
	}

	public static boolean lock(String path, int waitMills) {

		InterProcessMutex lock = new InterProcessMutex(client, path);
		map.put(path, lock);
		try {
			return lock.acquire(waitMills, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean release(String path) {
		InterProcessMutex lock = null;
		try {
			lock = map.get(path);
			if (lock == null) {
				lock = new InterProcessMutex(client, path);
			}
			lock.release();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return false;
		}
		
	}
}
