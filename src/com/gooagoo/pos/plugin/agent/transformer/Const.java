package com.gooagoo.pos.plugin.agent.transformer;

public class Const {
	private static Const constant;
	public static Const gen(){
		if (constant ==null) {
			synchronized (new Object()) {
				if (constant ==null) {
					constant = new Const();
				}
			}
			
		}
		return constant;
	}
	private  String methodName=null;
	private  Object object =null;
	private  int i = -1;
	private String jstr;
	public String getMethod(){
		return this.methodName;
	}
	public Object getObject(){
		return this.object;
	}
	
	public String getJstr(){
		return this.jstr;
	}
	public int getI(){
		return this.i;
	}
	public void setMethod(String meth){
		this.methodName = meth;
	}
	public void setJstr(String jstr){
	 this.jstr=jstr;
	}
	public void setI(int t){
		this.i=t;
	}
	
}
