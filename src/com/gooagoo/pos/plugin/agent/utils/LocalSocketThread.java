package com.gooagoo.pos.plugin.agent.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.gooagoo.pos.plugin.agent.writer.Pencil;
import com.gooagoo.pos.plugin.agent.writer.WriterFactory;
import com.gooagoo.pos.plugin.agent.writer.WriterFactory.WriterFactoryProperties;

public class LocalSocketThread implements Runnable {
	private static WriterFactoryProperties FactorySettings = new WriterFactoryProperties();
    private static LocalSocketThread localSocketThread;
	private static Socket ServerConnection;
	public static  String host = "127.0.0.1";
	public static  int port=12345;
	private static OutputStream mOutputStream;

    private static final Object Lock = new Object();
//    public LocalSocketThread(String host,int port){
//    	this.host=host;
//    	this.port=port;
//    }

    public static LocalSocketThread getThread() {

	if (localSocketThread == null) {
	    localSocketThread = new LocalSocketThread();
	}
	return localSocketThread;
    }



    @Override
    public void run() {
    	monitor();
    	while (true) {
			try {
				ServerConnection = new Socket(host, port);
				synchronized (Lock) {
					try {
						Lock.wait();
					} catch (InterruptedException ex) {

					}
				}
			} catch (Exception e) {
				synchronized (Lock) {
					try {
						ServerConnection.close();
					} catch (Exception ex) {
						ServerConnection = null;
					}
					try {
						Lock.wait(2000);
					} catch (InterruptedException ex) {
					}
				}
			}
		}
	    }

	/**
	 * 检查与服务器是否连接
	 */
	private final Runnable monitorRunnable = new Runnable() {
		public void run() {
			while (true) {
				synchronized (Lock) {
					try {
						if (ServerConnection!=null&&!ServerConnection.isClosed()) {
							ServerConnection.sendUrgentData(1);
						}
					} catch (Exception e) {
						Lock.notifyAll();
					}
					try {
						Lock.wait(5000);
					} catch (Exception e) {
					}
				}
			}
		}
	};

	private void monitor() {
		Pencil.writeLog("monitor执行了");
		Thread t = new Thread(monitorRunnable);
		t.start();
	}

	public static OutputStream getServerOutputStream() {
		OutputStream os = null;
			if (ServerConnection != null) {
				try {
					os = ServerConnection.getOutputStream();
				} catch (Exception e) {
					synchronized (Lock) {
						Lock.notifyAll();
					}
				}
			}
		return os;
	}

    public static String getNowDate() {
	Date currentTime = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	String dateString = formatter.format(currentTime);
	return dateString;
    }


    
//    /**
//     * 
//     * @param type
//     *            1-心跳 2-通信
//     * @param str
//     *            basicinfo内容
//     * 
//     * 
//     */
//    public synchronized  static void sendMsg(String methodName,Object obj,Charset charset,int i) {
//    	if (methodName == null) {   //准备把参数传出来进行解析的,但是一传就编译不过去compile error: syntax error near "lay.a(int) ,  $1,Cha"
//			return;
//		}
//    	mOutputStream=getServerOutputStream();
//    	String jstr = JSONUtil.toString(obj);
//    	String content=getTime()+"   "+methodName+":P[" + i + "]="+jstr;
//		 byte[] data = content.getBytes(charset != null ? charset : Charset.forName("GBK"));
//    	//"  + getTime() + "   " + methodName + ":P[" + i + "]=\"+JSONUtil.toString($" + i + "),Charset.forName(\"GBK\")
//		int len = data.length;
//		final byte[] header = toBytes(len);
//		if ( mOutputStream == null) {
//			writeLocalFile(content,charset);
//			return;
//		} else {
//			try {
//				if (mOutputStream!=null) {
//				mOutputStream.write(header);
//				mOutputStream.write(data);
//				}
//			} catch (Exception e) {
//				close();
//				}
//		
//			}
//    }
    
    
    public static String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String date = format.format(System.currentTimeMillis());
		return date;
	}
    /**
     * 
     * @param type
     *            1-心跳 2-通信
     * @param str
     *            basicinfo内容
     * 
     * 
     */
    public synchronized  static void sendMsg(String content,Charset charset) {
    	Pencil.writeLog("sendMsg执行在---"+Thread.currentThread().getName());
    	if (content == null) {
			return;
		}
    	mOutputStream=getServerOutputStream();
		 byte[] data = content.getBytes(charset != null ? charset : Charset.forName("GBK"));
		int len = data.length;
		 byte[] header = toBytes(len);
		if ( mOutputStream == null) {
			writeLocalFile(content,charset);
			return;
		} else {
			try {
				if (mOutputStream!=null) {
				mOutputStream.write(header);
				mOutputStream.write(data);
				}
			} catch (Exception e) {
				close();
				}
		
			}
    }
    
	private static void close() {
		if (ServerConnection != null) {
		    try {
			if (mOutputStream != null)
				mOutputStream.close();
			ServerConnection.close();
		    } catch (IOException e) {
			Pencil.writeLog(e);
		    }
		}	
	}


	public static void writeLocalFile(String content, Charset charset) {
		if (content == null) {
			return;
		}
	
		WriterFactory factory = WriterFactory.getInstance(FactorySettings);
		final OutputStream fos = factory.getFileOutputStream();
		if(fos==null){
			return ;
		}
		byte[] data = (content + "\n").getBytes(charset != null ? charset : Charset.forName("GBK"));
		write(data, fos);
	}
	public static void write(  byte[] data,   OutputStream os) {
		//writePool.execute(new Runnable() {  //经测试 线程确实有效果  但是界面还是有点卡
				if (os != null) {
					try {
						if (data != null) {
							os.write(data);
							os.flush();
						}
					} catch (IOException e) {
						Pencil.writeLog(e);
					}
				}
			
	}

	public static byte[] toBytes(int i) {
		byte[] cup = new byte[4];
		int g = i%10;
        int sw = i/10%10;
        int b = i/100%10;
        int q = i/1000%10;
		cup[0] = (byte) (q+48);
		cup[1] = (byte) (b+48);
		cup[2] = (byte) (sw+48);
		cup[3] = (byte) (g+48);
		
		return cup;
	}
}