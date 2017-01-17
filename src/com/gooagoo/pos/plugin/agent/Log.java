package com.gooagoo.pos.plugin.agent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Log {

	public static void write(String content) {
		OutputStreamWriter pw = null;// 定义一个流
		try {
			pw = new OutputStreamWriter(new FileOutputStream("D:/test.txt"), "GBK");
			pw.write(content);
			pw.close();// 关闭流
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
