package com.gooagoo.pos.plugin.agent;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import com.gooagoo.pos.plugin.agent.transformer.Constants;
import com.gooagoo.pos.plugin.agent.transformer.JPosTransformer;
import com.gooagoo.pos.plugin.agent.writer.Pencil;
import com.gooagoo.pos.plugin.agent.writer.WriterFactory.WriterFactoryProperties;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class JavaPosAgent {

	private static VirtualMachine select(String jvmkey, String jvmvalue) {
		VirtualMachine jvm = null;
	
			List<VirtualMachineDescriptor> vms = VirtualMachine.list();
	
			for (VirtualMachineDescriptor vm : vms) {
				try {		
				jvm = VirtualMachine.attach(vm.id());
				System.out.println(vm.id());
				Properties ps = jvm.getSystemProperties();
				Iterator<Entry<Object, Object>> it = ps.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Object, Object> kv = it.next();
					if (kv.getKey().toString().contains(jvmkey) && kv.getValue().toString().contains(jvmvalue)) {
						System.out.println(kv.getKey().toString()+"-----"+kv.getValue().toString());
						return jvm;
				}
				}
				} catch (Exception e) {
					continue;
				}
			}
		return null;
	}

	private static WriterFactoryProperties writerFactorySettings = new WriterFactoryProperties();

	public static void main(String... args) {
		String agent = null;
		String vmc = null;
		String server = null;
		int port = 12345;

		VirtualMachine jvm = null;
		if (args.length < 1) {
			System.out.println("step 1/4. 请指定代理.");
			return;
		} else {
			agent = args[0];
			File agentFile = new File(agent);
			if (!agentFile.exists()) {
				System.out.println("step 1/4. 代理:[" + agent + "]不存在!");
				return;
			} else {
				System.out.println("step 1/4. 代理:[" + agent + "]");
			}
		}
		
		if (args.length==1) {
			List<VirtualMachineDescriptor> vms = VirtualMachine.list();

			for (VirtualMachineDescriptor vm : vms) {
				try {
					jvm = VirtualMachine.attach(vm.id());
					System.out.println(vm.id());
					try {
						File agentFile = new File(agent);
						if (jvm != null) {
							if (agentFile.exists()) {
								jvm.loadAgent(agent);
								jvm.detach();
							} else {
								System.out.println("step 4/4. 文件:[" + agent + "]不存在!");
							}
						}
						System.out.println("step 4/4. 加载java代理成功!");
					} catch (Exception e) {
						System.out.println("step 4/4. 加载java代理失败!");
						continue;
						
					}
					
				} catch (Exception e) {
					continue;
				}
			}
			return;
		}
	
		
		if (args.length > 1 && args[1].contains("-")) {
			String[] split = args[1].split("-");
			jvm = select(split[0],split[1]);
			if (jvm != null) {
				System.out.println("step 2/4. 虚拟机pid:[" + jvm.id() + "]");
			} else {
				System.out.println("step 2/4. 根据[" + vmc + "]没有找到java虚拟机.");
				return;
			}
		}
		
		if (args.length > 2) {
			server = args[2];
			writerFactorySettings.setServer(server);
			System.out.println("step 3/4. 数据接收机:" + "[" + server + "]");
		}
		
		if (args.length > 3) {
			String p = args[3];
			try {
				port = Integer.parseInt(p);
				System.out.println("step 3/4. 数据接收机端口号:" + "[" + port + "]");
			} catch (Exception e) {
			}
			writerFactorySettings.setPort(port);
		}
		try {
			File agentFile = new File(agent);
			if (jvm != null) {
				if (agentFile.exists()) {
					jvm.loadAgent(agent);
					jvm.detach();
				} else {
					System.out.println("step 4/4. 文件:[" + agent + "]不存在!");
				}
			}
			System.out.println("step 4/4. 加载java代理成功!");
		} catch (Exception e) {
			System.out.println("step 4/4. 加载java代理失败!");
			e.printStackTrace();
		}
	}

	public static void premain(String args, Instrumentation inst) {
		Pencil.writeLog("Premain loaded...");
		ClassFileTransformer transformer = new JPosTransformer();
		inst.addTransformer(transformer);
	}

	public static void agentmain(String args, final Instrumentation inst){
		Pencil.writeLog("agent loaded...");
		ClassFileTransformer transformer = new JPosTransformer();
		inst.addTransformer(transformer,true);
		 final Class[] allLoadedClasses = inst.getAllLoadedClasses(); 
		 Pencil.writeLog2("allLoadedClasses.length:"+":"+allLoadedClasses.length);
		 //new Thread(new LocalSocketThread()).start();
			new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					Pencil.writeLog(e1);
				}
		
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
									inst.retransformClasses(allLoadedClasses[i]);
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
