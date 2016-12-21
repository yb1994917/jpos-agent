package com.gooagoo.pos.plugin.agent.transformer.source;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gooagoo.javassist.CtClass;
import com.gooagoo.pos.plugin.agent.transformer.Constants1;

public class SourceCodeFactory {

	public static String createBefore(String methodName, CtClass[] parameterTypes) {
		int len = parameterTypes.length;
		StringBuilder cup = new StringBuilder(512);
		cup.append("{\n").append("	   try{\n");
		boolean hasCode = false;
		for (int i = 1; i <= len; i++) {
			CtClass c = parameterTypes[i - 1];
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			String date = format.format(new Date());
			boolean skip = false;
			for (String useless : Constants1.UselessPackagePreix) {
				if (c.getName().startsWith(useless)) {
					skip = true;
					break;
				}
			}
//			com.gooagoo.pos.plugin.agent.AttachAgent
			if (!skip) {
				hasCode = true;
				cup.append("	   		Pencil.writeServer(\""  + date + "   " + methodName + ":P[" + i + "]=\"+JSON.toString($" + i + "),Charset.forName(\"GBK\"));\n");
			}
		}
		cup.append("	   }catch(Exception e){\n").append(" Pencil.writeLog(e); }\n").append("	}\n");
		return hasCode ? cup.toString() : "{}";
	}

	public static String createAfter() throws Exception {
		throw new Exception("not implements yet!");
	}

}
