package com.gooagoo.pos.plugin.agent;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.gooagoo.pos.plugin.agent.transformer.Constants;
import com.gooagoo.pos.plugin.agent.transformer.JPosTransformer;
import com.gooagoo.pos.plugin.agent.writer.Pencil;
import com.gooagoo.pos.plugin.agent.writer.WriterFactory.WriterFactoryProperties;
import com.sun.tools.attach.VirtualMachine;

public class JavaPosAgent {

	private static void inject(String agentPath,String id) {
				try {
					VirtualMachine jvm = VirtualMachine.attach(id);
					jvm.loadAgent(agentPath);
				} catch (Exception e ) {
					Pencil.writeLog(e);
				}
	}

	private static WriterFactoryProperties writerFactorySettings = new WriterFactoryProperties();

	public static void main(String... args) {
		String agent = null;
		String id = null;
		String server = "127.0.0.1";
		int port = 12345;

		writerFactorySettings.setServer(server);
		writerFactorySettings.setPort(port);
		
		if (args.length < 1) {
			System.out.println("step 1/2. 请指定代理.");
			return;
		} else {
			agent = args[0];
			id = args[1];
			File agentFile = new File(agent);
			if (!agentFile.exists()) {
				System.out.println("step 2/2. 代理:[" + agent + "]不存在!");
				return;
			} else {
				inject(agent,id);
				System.out.println("step 2/2. 代理:[" + agent + "]");
			}
		}
	}

	public static void premain(String args, Instrumentation inst) {
		Pencil.writeLog("Premain loaded...");
		ClassFileTransformer transformer = new JPosTransformer();
		inst.addTransformer(transformer);
	}

	public static void agentmain(String args, Instrumentation inst){
		Pencil.writeLog("agent loaded...");
		ClassFileTransformer transformer = new JPosTransformer();
		 final Instrumentation is=inst;
		 final Class[] allLoadedClasses = is.getAllLoadedClasses(); 
		inst.addTransformer(transformer,true);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					Pencil.writeLog(e1);
				}
				 Pencil.writeLog2("allLoadedClasses.length:"+":"+allLoadedClasses.length);
					for (int i = 0 ; i < allLoadedClasses.length ; i++) {
						boolean skip = false;
						for (String useless : Constants.UselessPackagePreix) {
							skip = allLoadedClasses[i].getName().startsWith(useless);
							if(skip){
								break;
							}
//							skip = allLoadedClasses[i].getName().startsWith("java.lang")||allLoadedClasses[i].getName().startsWith("javax.swing");//这个时候必须过滤java.lang不然UnmodifiableClassException
//							//retransformClasses0(Native Method)
//							if (skip) {
//								break;
//							}
						}
						if(skip){
							continue;
						}
						Pencil.writeLog2(allLoadedClasses[i].getName());
								try {
									is.retransformClasses(allLoadedClasses[i]);
									Pencil.writeLog2(allLoadedClasses[i]+"执行了retransformClasses");
								} catch (UnmodifiableClassException e) {
									Pencil.writeLog(e);
									continue;
								}
					}
				
			}
		}).start();

		}
	}
