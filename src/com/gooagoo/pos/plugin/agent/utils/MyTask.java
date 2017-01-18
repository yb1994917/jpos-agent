package com.gooagoo.pos.plugin.agent.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gooagoo.javassist.ClassPool;
import com.gooagoo.javassist.NotFoundException;
import com.gooagoo.pos.plugin.agent.transformer.Constants1;
import com.gooagoo.pos.plugin.agent.writer.Pencil;
// implements Runnable
public class MyTask implements Runnable{
	public static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	public static ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 50, 10, TimeUnit.SECONDS,queue);  
	ClassPool pool1 = ClassPool.getDefault(); 
	//	pool1.insertClassPath(".\\posapp_pub.jar"); 直接把jar包加载了,我的破解就失效了
	private String methodName;
	private String parameterTypes;
	private Object[] objs;

	public MyTask(String methodName,String parameterTypes,Object[] objs) throws NotFoundException {
		pool1.appendSystemPath(); //appendSystemPath一加 速度快不少
		this.methodName=methodName;
		this.parameterTypes=parameterTypes;
		this.objs=objs;
		pool.allowCoreThreadTimeOut(true);//允许线程数低于corePoolSize时，线程也因为空闲而终止
	}


	@Override
	public void run() {
		int len = objs.length;
		StringBuilder cup = new StringBuilder(512);
		cup.append("{\n").append("	   try{\n");
		for (int i = 1; i <= len; i++) {
			Object obj = objs[i - 1];
			if (obj==null) {
				continue;
			}
			boolean skip = false;
			for (String useless : Constants1.UselessPackagePreix) {
				
				if (obj.getClass().getName().startsWith(useless)) {
					skip = true;
					break;
				}
			}
			if (!skip) {
				Pencil.writeServer(getTime() +"	"+ methodName + ":P[" + i
						+ "]=" + JSONUtil.toString(objs[i - 1]),
						Charset.forName("GBK"));
			}
		}
		}
		public static String getTime(){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			String date = format.format(System.currentTimeMillis());
			return date;
		}
}
