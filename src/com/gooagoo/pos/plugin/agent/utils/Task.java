package com.gooagoo.pos.plugin.agent.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
// implements Runnable
public class Task implements Runnable{
	public static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	public static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 7, 10, TimeUnit.SECONDS,queue);  
//	 ExecutorService pool = Executors.newFixedThreadPool(8);
	//static
	private  String methodName=null;
	private  Object object =null;
	boolean hasCode = false;
	private  int i = -1;
	public Task(String methodName,Object object,int i){
		this.methodName=methodName;
		this.object=object;
		this.i=i;
		pool.allowCoreThreadTimeOut(true);
	}
	
	@Override
	public void run() {
		
	LocalSocketThread.sendMsg(getTime() + "   " + methodName + ":P[" + i + "]="+JSONUtil.toString(object),Charset.forName("GBK"));
	}
	
	public static String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String date = format.format(System.currentTimeMillis());
		return date;
	}
}
