package com.gooagoo.pos.plugin.agent.writer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class Test {
	public static void main(String[] args) throws AttachNotSupportedException, IOException {
		List<VirtualMachineDescriptor> vms = VirtualMachine.list();

			VirtualMachine jvm = VirtualMachine.attach("8744");
			Properties ps = jvm.getSystemProperties();
			Iterator<Entry<Object, Object>> it = ps.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> kv = it.next();
				
				System.out.println("key==="+kv.getKey().toString()+"---->value=="+kv.getValue().toString());
//				System.out.println(kv.getValue().toString());
//				if (kv.getKey().toString().contains(jvmCharactor)) {
//					return jvm;
//				} else if (kv.getValue().toString().contains(jvmCharactor)) {
//					return jvm;
//				}
				for (VirtualMachineDescriptor vm : vms) {
			}
			System.out.println("-------------------------------------------------------------------------------");
		}
	}
}
