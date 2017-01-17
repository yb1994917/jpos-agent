package com.gooagoo.pos.plugin.agent.writer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.gooagoo.com.alibaba.fastjson.JSON;
import com.gooagoo.jackson.databind.MapperFeature;
import com.gooagoo.jackson.databind.ObjectMapper;
import com.gooagoo.jackson.databind.SerializationFeature;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class Test {
	
	private static final Object JTable = null;
	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.disable(MapperFeature.REQUIRE_SETTERS_FOR_GETTERS);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER);
	}

	public static void main(String[] args) throws IOException  {
/*		List<VirtualMachineDescriptor> vms = VirtualMachine.list();
	
//			System.out.println(vms.size());
//			System.out.println(vm.id());
			try {
//				VirtualMachine jvm = VirtualMachine.attach(vm.id());
				VirtualMachine jvm = VirtualMachine.attach(\\\"19536\\");
				Properties ps = jvm.getSystemProperties();
				Iterator<Entry<Object, Object>> it = ps.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Object, Object> kv = it.next();
					
					System.out.println(\\"key===\\"+kv.getKey().toString()+\\"---->value==\\"+kv.getValue().toString());
//					System.out.println(kv.getValue().toString());
//					if (kv.getKey().toString().contains(jvmCharactor)) {
//						return jvm;
//					} else if (kv.getValue().toString().contains(jvmCharactor)) {
//						return jvm;
//					}
				}
			} catch (Exception e) {
			}
//			for (VirtualMachineDescriptor vm : vms) {
			System.out.println(\\"-------------------------------------------------------------------------------\\");*/
//		String s = \"d.a.paintComponent(java.awt.Graphics):P[1]={\\"antialiasHint\\":1,\\"backgroundColor\\":{\\"r\\":255,\\"g\\":255,\\"b\\":255,\\"alpha\\":255},\\"clipRegion\\":{},\\"clipState\\":1,\\"composite\\":{\\"rule\\":3},\\"compositeState\\":0,\\"constrainX\\":0,\\"constrainY\\":0,\\"drawpipe\\":{},\\"eargb\\":-16777216,\\"fillpipe\\":{\\"$ref\\":\\"$.drawpipe\\"},\\"font\\":{\\"name\\":\\"宋体\\",\\"size\\":12,\\"style\\":0},\\"fontInfo\\":{\\"aaHint\\":1,\\"devTx\\":[1.0,0.0,0.0,1.0],\\"font2D\\":{\\"handle\\":{\\"font2D\\":{\\"$ref\\":\\"..\\"}},\\"mapper\\":{},\\"numGlyphs\\":28762,\\"numSlots\\":14,\\"stdComposite\\":false,\\"style\\":0},\\"fontStrike\\":{\\"numGlyphs\\":28762},\\"glyphTx\\":[12.0,0.0,0.0,12.0],\\"lcdRGBOrder\\":false,\\"lcdSubPixPos\\":false,\\"originX\\":0.0,\\"originY\\":0.0,\\"pixelHeight\\":12},\\"fontMetrics\\":{\\"ascent\\":12,\\"descent\\":1,\\"font\\":{\\"$ref\\":\\"$.font\\"},\\"leading\\":2,\\"maxAdvance\\":12},\\"foregroundColor\\":{\\"blue\\":0,\\"green\\":0,\\"red\\":0},\\"imageComp\\":{\\"uniqueID\\":17},\\"imagepipe\\":{\\"$ref\\":\\"$.drawpipe\\"},\\"interpolationType\\":1,\\"lcdTextContrast\\":140,\\"loops\\":{\\"drawGlyphListAALoop\\":{\\"compositeType\\":{\\"uniqueID\\":16},\\"destType\\":{\\"pixelConverter\\":{\\"alphaMask\\"\"+
//			\":0},\\"uniqueID\\":9},\\"primTypeID\\":17,\\"sourceType\\":{\\"pixelConverter\\":{\\"alphaMask\\":0},\\"uniqueID\\":36},\\"uniqueID\\":285806628},\\"drawGlyphListLCDLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.destType\\"},\\"primTypeID\\":18,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":302583844},\\"drawGlyphListLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"pixelConverter\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType.pixelConverter\\"},\\"uniqueID\\":2},\\"primTypeID\\":16,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":268570660},\\"drawLineLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":2,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":33689636},\\"drawParallelogramLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\"+
//			\"\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":9,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":151130148},\\"drawPathLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":12,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":201461796},\\"drawPolygonsLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":11,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":184684580},\\"drawRectLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":10,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":167907364},\\"fillParallelogramLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"p\"+
//			\"rimTypeID\\":8,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":134352932},\\"fillPathLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":13,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":218239012},\\"fillRectLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":6,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":100798500},\\"fillSpansLoop\\":{\\"compositeType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.compositeType\\"},\\"destType\\":{\\"$ref\\":\\"$.loops.drawGlyphListLoop.destType\\"},\\"primTypeID\\":7,\\"sourceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.sourceType\\"},\\"uniqueID\\":117575716}},\\"paint\\":{\\"$ref\\":\\"$.foregroundColor\\"},\\"paintState\\":0,\\"pixel\\":-16777216,\\"renderHint\\":0,\\"shapepipe\\":{\\"$ref\\":\\"$.drawpipe\\"},\\"stroke\\":{},\\"strokeHint\\":0,\\"strokeState\\":0,\\"surfaceData\\":{\\"colorModel\\":{\\"alpha\"+
//			\"Premultiplied\\":false,\\"colorSpace\\":{\\"numComponents\\":3,\\"type\\":5},\\"numColorComponents\\":3,\\"numComponents\\":3,\\"transferType\\":3,\\"transparency\\":1},\\"dirty\\":true,\\"disposerReferent\\":{},\\"numCopies\\":0,\\"surfaceLost\\":false,\\"surfaceType\\":{\\"$ref\\":\\"$.loops.drawGlyphListAALoop.destType\\"},\\"valid\\":true},\\"textAntialiasHint\\":0,\\"textpipe\\":{\\"$ref\\":\\"$.drawpipe\\"},\\"transX\\":-803,\\"transY\\":-9,\\"transform\\":{},\\"transformState\\":1,\\"usrClip\\":{\\"x\\":0,\\"y\\":0,\\"width\\":114,\\"height\\":15}}\";
//		System.out.println(s.getBytes().length);
		
//		File file = new File(\"./config.ini\");
//		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),Charset.forName(\"GBK\")));
//		System.out.println(reader.readLine());
		
//		String s1 = \"2017-01-16 15:21:44:080	com.pos.sell.gui.am.getTableCellRendererComponent(javax.swing.JTable,java.lang.Object,boolean,boolean,int,int):P[1]={\\"detailinfo\\":[{\\"商品编号\\": \\"29\\",\\"商品条形码\\": \\"690000002\\",\\"名称\\": \\"五粮液10年精品\\",\\"数量\\": \\"1\\",\\"原价\\": \\"260.0\\",\\"折扣\\": \\"0.95\\",\\"售价\\": \\"247.0\\",\\"售价小计\\": \\"247.0\\",\\"销售人员\\": \\"\\",\\"销售人员编号\\": \\"0\\",\\"是否积分\\": \\"true\\",\\"是否特价\\": \\"1\\"},{\\"商品编号\\": \\"32\\",\\"商品条形码\\": \\"680000004\\",\\"名称\\": \\"娃哈哈冰红茶\\",\\"数量\\": \\"1\\",\\"原价\\": \\"2.5\\",\\"折扣\\": \\"1.00\\",\\"售价\\": \\"2.5\\",\\"售价小计\\": \\"2.5\\",\\"销售人员\\": \\"\\",\\"销售人员编号\\": \\"0\\",\\"是否积分\\": \\"true\\",\\"是否特价\\": \\"0\\"},{\\"商品编号\\": \\"65\\",\\"商品条形码\\": \\"690000006\\",\\"名称\\": \\"国窖百年珍品1973\\",\\"数量\\": \\"1\\",\\"原价\\": \\"88.0\\",\\"折扣\\": \\"0.95\\",\\"售价\\": \\"83.6\\",\\"售价小计\\": \\"83.6\\",\\"销售人员\\": \\"\\",\\"销售人员编号\\": \\"0\\",\\"是否积分\\": \\"true\\",\\"是否特价\\": \\"1\\"}]}\";
//		String s2 ="2017-01-16 15:28:29:506	d.e.getTableCellRendererComponent(javax.swing.JTable,java.lang.Object,boolean,boolean,int,int):P[1]={\"detailinfo\":[{\"商品编号\": \"29\",\"商品条形码\": \"690000002\",\"名称\": \"五粮液10年精品\",\"数量\": \"1\",\"原价\": \"260.0\",\"折扣\": \"0.95\",\"售价\": \"247.0\",\"售价小计\": \"247.0\",\"销售人员\": \"\",\"销售人员编号\": \"0\",\"是否积分\": \"true\",\"是否特价\": \"1\"},{\"商品编号\": \"32\",\"商品条形码\": \"680000004\",\"名称\": \"娃哈哈冰红茶\",\"数量\": \"1\",\"原价\": \"2.5\",\"折扣\": \"1.00\",\"售价\": \"2.5\",\"售价小计\": \"2.5\",\"销售人员\": \"\",\"销售人员编号\": \"0\",\"是否积分\": \"true\",\"是否特价\": \"0\"},{\"商品编号\": \"65\",\"商品条形码\": \"690000006\",\"名称\": \"国窖百年珍品1973\",\"数量\": \"1\",\"原价\": \"88.0\",\"折扣\": \"0.95\",\"售价\": \"83.6\",\"售价小计\": \"83.6\",\"销售人员\": \"\",\"销售人员编号\": \"0\",\"是否积分\": \"true\",\"是否特价\": \"1\"},{\"商品编号\": \"32\",\"商品条形码\": \"680000004\",\"名称\": \"娃哈哈冰红茶\",\"数量\": \"1\",\"原价\": \"2.5\",\"折扣\": \"1.00\",\"售价\": \"2.5\",\"售价小计\": \"2.5\",\"销售人员\": \"\",\"销售人员编号\": \"0\",\"是否积分\": \"true\",\"是否特价\": \"0\"}]}";
//		System.out.println(s2.getBytes( Charset.forName("GBK")).length);
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < arrayList.size(); i++) {
			arrayList.add("di "+i+"lie ");
		}
		JTable jTable = new JTable();
		StringWriter writer = new StringWriter(512);
		mapper.writeValue(writer, jTable);
		 System.out.println(writer.toString());
//		String jsonString = JSON.toJSONString(jTable);
//		System.out.println(jsonString);
	
	}
}
