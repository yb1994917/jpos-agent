package com.gooagoo.pos.plugin.agent.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import com.gooagoo.pos.plugin.agent.writer.WriterFactory.WriterFactoryProperties;

public class Pencil {
	private static WriterFactoryProperties FactorySettings = new WriterFactoryProperties();
	private final static Object Lock = new Object();

	public static boolean writeLocalFile(String content, Charset charset) {
		if (content == null) {
			return false;
		}
		WriterFactory factory = WriterFactory.getInstance(FactorySettings);
		OutputStream fos = factory.getFileOutputStream();
		if(fos==null){
			return false;
		}
		byte[] data = (content + "\n").getBytes(charset != null ? charset : Charset.forName("GBK"));
		return fos == null ? false : write(data, fos);
	}

	public static boolean writeServer(String content, Charset charset) {
		if (content == null) {
			return false;
		}
		WriterFactory factory = WriterFactory.getInstance(FactorySettings);
		OutputStream os = factory.getServerOutputStream();
		byte[] data = content.getBytes(charset != null ? charset : Charset.forName("GBK"));
		int len = data.length;
		byte[] header = toBytes(len);

		if (os == null) {
			writeLocalFile(content,charset);
			return false;
		} else {
			synchronized (Lock) {
				return write(header, os) & write(data, os);
			}
			
		}
	}

	public static boolean write(byte[] data, OutputStream os) {
		if (os != null) {
			try {
				if (data != null) {
					os.write(data);
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static byte[] toBytes(int i) {
		byte[] cup = new byte[4];
		int g = i%10;
        int sw = i/10%10;
        int b = i/100%10;
        int q = i/1000%10;
		cup[0] = (byte) (q);
		cup[1] = (byte) (b);
		cup[2] = (byte) (sw);
		cup[3] = (byte) (g);
		return cup;
	}

	public static int toInteger(byte[] cup) {
		int i = (int) ((cup[0] & 0xFF) << 24);

		i += (int) ((cup[1] & 0XFF) << 16);

		i += (int) ((cup[2] & 0XFF) << 8);

		i += (int) (cup[3] & 0XFF);

		return i;
	}

	// java 合并两个byte数组
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}
	
	
	
	public static void writeLog(Exception e) {
		File f1 = new File("./logs/");
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f = new File("./logs/exception.log");
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f,true);
				PrintWriter writer = new PrintWriter(fos);
				writer.append(Thread.currentThread().getName());
				e.printStackTrace(writer);
				writer.flush();
				writer.close();
			} catch (Exception ex) {
			}
		} catch (Exception exc) {
		}
	}
	
	public static void writeLog1(String content){
		File f1 = new File("./logs/");
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f = new File("./logs/transform.log");
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f,true);
				PrintWriter writer = new PrintWriter(fos);
				writer.append(content + "\n");
				writer.flush();
				writer.close();
			} catch (Exception ex) {
			}
		} catch (Exception exc) {
		}
	}
	public static void writeLog2(String content){
		File f1 = new File("./logs/");
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f = new File("./logs/AllLoded.log");
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f,true);
				PrintWriter writer = new PrintWriter(fos);
				writer.append(content + "\n");
				writer.flush();
				writer.close();
			} catch (Exception ex) {
			}
		} catch (Exception exc) {
		}
	}
	

	public static void writeLog(String content){
		File f1 = new File("./logs/");
		if (!f1.exists()) {
			f1.mkdir();
		}
		File f = new File("./logs/insert.log");
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f,true);
				PrintWriter writer = new PrintWriter(fos);
				writer.append(content + "\n");
				writer.flush();
				writer.close();
			} catch (Exception ex) {
			}
		} catch (Exception exc) {
		}
	}
	
	public static void main(String...args){
		try{
			throw new IllegalArgumentException("cccc");
		}catch(Exception e){
			writeLog(e);
		}
	}
}
