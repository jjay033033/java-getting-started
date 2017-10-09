/**
 * 
 */
package top.lmoon.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author LMoon
 * @date 2017年10月9日
 * 
 */
public class ThreadPool {

	private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

	public static Future<?> submit(Runnable runnable) {
		return threadPool.submit(runnable);
	}

}
