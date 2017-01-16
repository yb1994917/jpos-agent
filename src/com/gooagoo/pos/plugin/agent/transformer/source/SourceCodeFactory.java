package com.gooagoo.pos.plugin.agent.transformer.source;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gooagoo.javassist.CtClass;
import com.gooagoo.pos.plugin.agent.transformer.Constants1;
import com.gooagoo.pos.plugin.agent.utils.LocalSocketThread;
import com.gooagoo.pos.plugin.agent.utils.Task;
import com.gooagoo.pos.plugin.agent.writer.Pencil;

public class SourceCodeFactory {

	public static String createBefore(String methodName, CtClass[] parameterTypes) {
		int len = parameterTypes.length;
		StringBuilder cup = new StringBuilder(512);
		cup.append("{\n").append("	   try{\n");
		boolean hasCode = false;
		for (int i = 1; i <= len; i++) {
			CtClass c = parameterTypes[i - 1];
			boolean skip = false;
			for (String useless : Constants1.UselessPackagePreix) {
				if (c.getName().startsWith(useless)) {
					skip = true;
					break;
				}
			}
			//com.gooagoo.pos.plugin.agent.AttachAgent
			//alibaba这个库回崩溃??为啥stackoverflow
			//JSONUtil---出现stackoverflow----换成了最新的fastjson库 就好了 报错的都删掉 --不会java heap space
			//JSON这个用的是Jackson 还是会出现javaheapspace错误
			if (!skip) {
				hasCode = true;
//					Task.pool1.scheduleAtFixedRate(command, initialDelay, period, unit)
//					cup.append("	   		LocalSocketThread.sendMsg(\""+methodName+"\" , $"+i+" ,Charset.forName(\"GBK\"),"+i+");");
					cup.append("	   		LocalSocketThread.getThread().sendMsg(\""  + getTime() + "   " + methodName + ":P[" + i + "]=\"+JSON.toString($" + i + "),Charset.forName(\"GBK\"));\n");
//				cup.append("	Task.pool.execute(new Task(\""+methodName+"\",$"+i+","+i+"));");
//				cup.append("	Task.pool1.scheduleAtFixedRate(new Task(\""+methodName+"\",$"+i+","+i+"),0,100, TimeUnit.MILLISECONDS);");
//				cup.append("	Task.getTask().send(\""+methodName+"\",$"+i+","+i+");");
			}
		}
		// cup.append("if(\"d.e.getTableCellRendererComponent(javax.swing.JTable,java.lang.Object,boolean,boolean,int,int)\".equals(\""+methodName+"\") && $1.getColumnCount()==13){");
		//没法编译过去  都会检查的 报错 getColumnCount() not found in java.awt.event.MouseEvent
		// 给这个方法单独过滤下d.e.getTableCellRendererComponent  暂时不做  // Pencil.writeLog(e); 不要写在catch中 不然几百兆
		cup.append("	   }catch(Exception e){\n").append("}\n").append("	}\n");
		return hasCode ? cup.toString() : "{}";
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
