package com.gooagoo.pos.plugin.agent.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// implements Runnable
public class Task implements Runnable{
	public static ExecutorService pool = Executors.newSingleThreadExecutor();
	 
	//static
	private  String methodName=null;
	private  Object object =null;
	boolean hasCode = false;
	private  int i = -1;
	public Task(String methodName,Object object,int i){
		this.methodName=methodName;
		this.object=object;
		this.i=i;
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
