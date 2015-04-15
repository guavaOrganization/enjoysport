package com.example.javalang.cloneable;

import java.io.Serializable;

public class DepthCloneInfo implements Serializable {
	private static final long serialVersionUID = -928276749944165157L;

	private String remark;

	private DepthCloneInfoAttr attr;

	public DepthCloneInfo(String remark, DepthCloneInfoAttr attr) {
		super();
		this.remark = remark;
		this.attr = attr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public DepthCloneInfoAttr getAttr() {
		return attr;
	}

	public void setAttr(DepthCloneInfoAttr attr) {
		this.attr = attr;
	}

	@Override
	public String toString() {
		return "ShallowInfo [remark=" + remark + ", attr=" + attr + "]";
	}
}
