package com.cy.pj.common.config;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;

@Setter
@Configuration
@ConfigurationProperties("async-thread-pool")
public class SpringAsyncConfig {
    /**核心线程数*/
	private int corePoolSize=3;
	/**最大线程数*/
	private int maximumPoolSize=5;
	/**线程空闲时间*/
	private int keepAliveTime=30;
	/**构建线程工厂*/
	private ThreadFactory threadFactory=new ThreadFactory() {
		//CAS算法
		private AtomicInteger at=new AtomicInteger(1000);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "db-async-thread-"+at.getAndIncrement());
		}
	};
	/**
	 *   自定义池对象(基于java中的ThreadPoolExecutor类)
	 * 	池对象应用:
	 *  1)当池中线程数没有达到corePoolSize指定的值,每次请求都会创建新的线程并存储到池中
	 *  2)当池中线程数已经达到corePoolSize指定的值,并且线程都在忙,新的请求会进入阻塞队列
	 *  3)当池中线程数已经达到corePoolSize指定的值,并且线程都在忙,并且队列已满,此时会创建新的线程,直到达到maximumPoolSize.
	 * @return
	 */
	@Bean("asyncPoolExecutor")
	public ThreadPoolExecutor newPoolExecutor() {
		System.out.println("corePoolSize="+corePoolSize);
		//创建阻塞式对象:基于数组存储结构,FIFO算法实现的一个阻塞式队列
		BlockingQueue<Runnable> workQueue=
		new ArrayBlockingQueue<>(10);
		//创建池对象
		ThreadPoolExecutor threadPoolExecutor=
		new ThreadPoolExecutor(
				corePoolSize,
				maximumPoolSize,
				keepAliveTime, 
				TimeUnit.SECONDS, 
				workQueue, 
				threadFactory);
		return threadPoolExecutor;
	}
}




