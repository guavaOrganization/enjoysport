package com.example.beetl.tags;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.beetl.core.BodyContent;
import org.beetl.core.Tag;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;

public class GuavaTestTag extends Tag {
	@Override
	public void render() {
		if (null == this.args || this.args.length == 0 || this.args.length != 2) {
			throw new RuntimeException("参数错误，请检查");
		}
		// 获取模版配置文件
		String layoutFile = BeetlUtil.getRelPath(this.ctx.getResourceId(), (String) this.args[0]);
		Template t = this.gt.getTemplate(layoutFile, this.ctx.getResourceId());
		t.binding(this.ctx.globalVar);
		t.dynamic(this.ctx.objectKeys);
		if (this.args.length == 2 && null != this.args[1]) {
			Map map = (Map) this.args[1];
			Iterator<String> keys = map.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				t.binding(key, map.get(key));
			}
		}
		BodyContent content = getBodyContent();
		t.binding("bodyContent", content);
		t.renderTo(this.ctx.byteWriter);
	}
}
