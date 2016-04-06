package org.xxhh.java300.metric;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Gauge;
import com.yammer.metrics.reporting.ConsoleReporter;

/**
 * TODO
 * 
 * @author scutshuxue.chenxf
 */
public class TestGauges {

	public static Queue<String> queue = new LinkedList<String>();

	public static void main(String[] args) throws InterruptedException {
		ConsoleReporter.enable(3, TimeUnit.SECONDS);   //开启控制台打印结果

		Gauge<Integer> g = Metrics.newGauge(TestGauges.class, "pending-jobs",
				new Gauge<Integer>() {
					@Override
					public Integer value() {
						return queue.size();   //计数
					}
				});

		queue.add("ssss");
		System.out.println(g.value());
		int i = 0;
		while (true) {
			Thread.sleep(1000);
			queue.add("ssss"+i);
			i++;
		}
	}
}