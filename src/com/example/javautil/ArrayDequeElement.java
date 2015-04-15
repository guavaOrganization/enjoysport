package com.example.javautil;

public class ArrayDequeElement {
	private int nodeId;

	public ArrayDequeElement(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public String toString() {
		return "ArrayDequeElement [nodeId=" + nodeId + "]";
	}
}
