package com.gooagoo.pos.plugin.agent.utils;

import java.io.StringWriter;

import javax.swing.JTable;

import com.gooagoo.jackson.databind.MapperFeature;
import com.gooagoo.jackson.databind.ObjectMapper;
import com.gooagoo.jackson.databind.SerializationFeature;
import com.gooagoo.pos.plugin.agent.transformer.Constants1;
import com.gooagoo.pos.plugin.agent.writer.Pencil;

public class JSON {

	private static final Object JTable = null;
	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.disable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
	}

	public static String toString(Object obj) {
		if (obj == null) {
			return null;
		}
		String className = obj.getClass().getName();
		for (String useless : Constants1.UselessPackagePreix) {
			if (className.startsWith(useless)) {
				return "";
			}
		}
		try {
			if (className.equals("javax.swing.JTable")) {
//				Pencil.writeLog("有Jtable对象 不为null");
				JTable jt=(JTable)obj;
				StringBuilder sb = new StringBuilder();
				if (jt.getRowCount()<= 0 || jt.getColumnCount()<=0) {
					return "";
				}
				sb.append("{");
				for (int i = 0; i < jt.getRowCount(); i++) {
					for (int j = 0; j < jt.getColumnCount(); j++) {
						if (i==jt.getRowCount()-1 && j==jt.getColumnCount()-1) {
							sb.append("\"").append(i).append(",").append(j).append("\"").append(": ").append("\"").append(jt.getValueAt(i, j)).append("\"");
						}else{
						sb.append("\"").append(i).append(",").append(j).append("\"").append(": ").append("\"").append(jt.getValueAt(i, j)).append("\"").append(",");
						}				
						}
				}
				sb.append("}");
				return sb.toString();
			}
		} catch (Exception e) {
			Pencil.writeLog(e);
			return "";
		}
		try {
			StringWriter writer = new StringWriter(512);
			mapper.writeValue(writer, obj);
			return writer.toString();
		} catch (Exception e) {
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
	
	public static String toString(String str) {
		return str;
	}
}
