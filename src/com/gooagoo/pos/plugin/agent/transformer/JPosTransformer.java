package com.gooagoo.pos.plugin.agent.transformer;

import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import com.gooagoo.javassist.ClassPool;
import com.gooagoo.javassist.CtClass;
import com.gooagoo.javassist.CtMethod;
import com.gooagoo.pos.plugin.agent.transformer.source.SourceCodeFactory;
import com.gooagoo.pos.plugin.agent.writer.Pencil;

public class JPosTransformer  implements ClassFileTransformer {

	static OutputStream out = null;

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		
		try {
			if (className == null) {
				return null;
			}
			className = className.replaceAll("/", ".");
			for (String useless : Constants.UselessPackagePreix){
				if (className.startsWith(useless)) {
					return null;
				}
			}
			ClassPool pool = ClassPool.getDefault();
			pool.importPackage("com.gooagoo.pos.plugin.agent.writer.Pencil");
			pool.importPackage("com.gooagoo.pos.plugin.agent.utils.JSON");
			pool.importPackage("com.gooagoo.pos.plugin.agent.utils.JSONUtil");
			pool.importPackage("com.gooagoo.pos.plugin.agent.utils.LocalSocketThread");
			pool.importPackage("com.gooagoo.pos.plugin.agent.utils.Task");
			pool.importPackage("java.nio.charset.Charset");
			CtClass cc = pool.get(className);
			Pencil.writeLog1(className);
			if (!cc.isInterface() && !cc.isEnum() && !cc.isAnnotation()) {
				synchronized (cc) {
			CtMethod[] ms = cc.getDeclaredMethods();
				for (CtMethod m : ms) {
					if (!m.isEmpty() && !"main".equals(m.getName())) {
						if (m.getMethodInfo().isMethod()) {
							CtClass[] parameterTypes = m.getParameterTypes();
							if (parameterTypes != null && parameterTypes.length > 0) {
								String before = SourceCodeFactory.createBefore(m.getLongName(), parameterTypes);
									Pencil.writeLog("insert before:"+m.getLongName());
									try{
										m.insertBefore(before);
									}catch(Exception e){ 
										Pencil.writeLog(e);
									}
							}
						}
					}
				}
				}
			}
			return cc.toBytecode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}	
