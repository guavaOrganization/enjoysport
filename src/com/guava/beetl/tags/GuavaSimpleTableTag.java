package com.guava.beetl.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.beetl.core.Tag;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;

public class GuavaSimpleTableTag extends Tag {
	private static final String LAYOUT_FILE = "/guava/tags/GuavaSimpleTableTag.btl";
	public void render() {
		if (null == this.args || this.args.length != 2) {
			throw new RuntimeException("参数错误");
		}
		String layoutFile = BeetlUtil.getRelPath(this.ctx.getResourceId(), LAYOUT_FILE);
		Template t = this.gt.getTemplate(layoutFile, this.ctx.getResourceId());
		t.binding(this.ctx.globalVar);
		t.dynamic(this.ctx.objectKeys);
		Map map = (Map) this.args[0];
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			t.binding(key, map.get(key));
		}
		List<Map> colList = (List<Map>) this.args[1];
		List<Map> dataList = new ArrayList<Map>();
		
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("title", "Beetl 自定义标签");
		dataMap.put("Name", "陈小俊");
		dataMap.put("Age", "26");
		dataMap.put("Province", "江西省");
		dataList.add(dataMap);
		
		dataMap = new HashMap<String, String>();
		dataMap.put("Name", "吴小丽");
		dataMap.put("Age", "25");
		dataMap.put("Province", "四川省");
		dataList.add(dataMap);

		t.binding("dataList", dataList);
		t.binding("colList", colList);
		t.renderTo(this.ctx.byteWriter);
	}
}
