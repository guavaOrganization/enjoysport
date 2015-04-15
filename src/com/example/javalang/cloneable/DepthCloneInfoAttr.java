package com.example.javalang.cloneable;

import java.io.Serializable;

public class DepthCloneInfoAttr implements Serializable {
	private static final long serialVersionUID = 7288325143851267616L;
	
	private String attrName;

	public DepthCloneInfoAttr(String attrName) {
		super();
		this.attrName = attrName;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	@Override
	public String toString() {
		return "ShallowInfoAttr [attrName=" + attrName + "]";
	}
}
