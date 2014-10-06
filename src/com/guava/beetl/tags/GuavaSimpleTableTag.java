package com.guava.beetl.tags;

import java.util.Iterator;
import java.util.Map;

import org.beetl.core.Tag;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;

public class GuavaSimpleTableTag extends Tag {
	private static final String LAYOUT_FILE = "/guava/tags/GuavaSimpleTableTag.btl";
	public void render() {
		if (null == this.args || this.args.length != 1) {
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
		t.renderTo(this.ctx.byteWriter);
	}
}
