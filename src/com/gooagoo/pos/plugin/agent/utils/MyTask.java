package com.gooagoo.pos.plugin.agent.utils;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gooagoo.javassist.ClassPool;
import com.gooagoo.javassist.CtClass;
import com.gooagoo.pos.plugin.agent.transformer.Constants1;
import com.gooagoo.pos.plugin.agent.writer.Pencil;
// implements Runnable
public class MyTask implements Runnable{
	
	ClassPool pool1 = ClassPool.getDefault();
	private String methodName;
//	private CtClass[] parameterTypes;
	private String parameterTypes;
	private Object[] objs;

	public MyTask(String methodName,String parameterTypes,Object[] objs) {
		pool1.appendSystemPath();
		this.methodName=methodName;
		this.parameterTypes=parameterTypes;
		this.objs=objs;
	}


	@Override
	public void run() {
//		int len = parameterTypes.length;
//		StringBuilder cup = new StringBuilder(512);
//		cup.append("{\n").append("	   try{\n");
//		for (int i = 1; i <= len; i++) {
//			CtClass c = parameterTypes[i - 1];
//			boolean skip = false;
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
