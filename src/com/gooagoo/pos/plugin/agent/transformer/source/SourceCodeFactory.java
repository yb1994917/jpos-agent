package com.gooagoo.pos.plugin.agent.transformer.source;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gooagoo.javassist.CtClass;
import com.gooagoo.pos.plugin.agent.transformer.Constants1;
import com.gooagoo.pos.plugin.agent.utils.LocalSocketThread;
import com.gooagoo.pos.plugin.agent.utils.MyTask;
import com.gooagoo.pos.plugin.agent.utils.Task;
import com.gooagoo.pos.plugin.agent.writer.Pencil;

public class SourceCodeFactory {

	public static String createBefore(String methodName, CtClass[] parameterTypes) {
//		pool.execute(new Thread(){
//			@Override
//			public void run() {
//			}
//		});
		int len = parameterTypes.length;
		StringBuilder cup = new StringBuilder(512);
		
		cup.append("{\n").append("	   try{\n");
		cup.append("Task.pool.execute(new MyTask(\""+methodName+"\",\"parameterTypes\",$args));");
//		boolean hasCode = false;
//		for (int i = 1; i <= len; i++) {
//			CtClass c = parameterTypes[i - 1];
//			boolean skip = false;
//			for (String useless : Constants1.UselessPackagePreix) {
//				if (c.getName().startsWith(useless)) {
//					skip = true;
//					break;
//				}
//			}
//			if (!skip) {
////				hasCode = true;
////				cup.append("	Task.pool.execute(new Task(\""+methodName+"\",$"+i+","+i+"));");
////				cup.append("	   		LocalSocketThread.getThread().sendMsg(\""  + getTime() + "   " + methodName + ":P[" + i + "]=\"+JSON.toString($" + i + "),Charset.forName(\"GBK\"));\n");
//			
//			
//			}
//		}
		cup.append("	   }catch(Exception e){\n").append("}\n").append("	}\n");
		
		return cup.toString();
	}

	
	public static String createAfter() throws Exception {
		throw new Exception("not implements yet!");
	}
	
	public static String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String date = format.format(System.currentTimeMillis());
		return date;
	}
}
