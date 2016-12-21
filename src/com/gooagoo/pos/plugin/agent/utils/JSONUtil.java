package com.gooagoo.pos.plugin.agent.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.alibaba.fastjson.JSON;
import com.gooagoo.pos.plugin.agent.transformer.Constants;

public class JSONUtil {
	private static File f = new File("d:/test.txt");

	public static String toString(Object obj) {

		if (obj == null) {
			return null;
		}

		String className = obj.getClass().getName();
		for (String useless : Constants.UselessPackagePreix) {
			if (className.startsWith(useless)) {
				return "";
			}
		}
		
		try {
			String json =  JSON.toJSONString(obj);
//			write(json);
			return json;
		} catch (Exception e) {
			System.out.println(e.toString());
			write("JSONUtil" + e.toString());
			return null;
		}
	}

	public static String toString(int i) {
		try {
			return Integer.toString(i);
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(long lng) {
		try {
			return Long.toString(lng);
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(char c) {
		return new String(new char[] { c });
	}

	public static String toString(double d) {
		return Double.toString(d);
	}

	public static String toString(float f) {
		return Double.toString(f);
	}

	public static String toString(short s) {
		return Short.toString(s);
	}

	public static String toString(boolean b) {
		return Boolean.toString(b);
	}

	public static String toString(byte bt) {
		return Byte.toString(bt);
	}

	public static void write(String str) {

		OutputStream out = null;
		try {
			out = new FileOutputStream(f);
			byte b[] = str.getBytes();
			out.write(b);
			out.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
