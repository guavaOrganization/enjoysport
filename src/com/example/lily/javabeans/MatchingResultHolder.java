package com.example.lily.javabeans;

import java.util.List;

public class MatchingResultHolder {
	private List<String> matchedHead;// 正确匹配结果的头部
	private List<List<String>> matchedResultList;// 正确匹配的结果列表

	private List<String> unmatchHead;// 未正确匹配结果的头部
	private List<List<String>> unmatchResultList;// 未正确匹配结果列表

	public MatchingResultHolder() {
	}

	public MatchingResultHolder(List<String> matchedHead,
			List<List<String>> matchedResultList, List<String> unmatchHead,
			List<List<String>> unmatchResultList) {
		super();
		this.matchedHead = matchedHead;
		this.matchedResultList = matchedResultList;
		this.unmatchHead = unmatchHead;
		this.unmatchResultList = unmatchResultList;
	}

	public List<String> getMatchedHead() {
		return matchedHead;
	}

	public void setMatchedHead(List<String> matchedHead) {
		this.matchedHead = matchedHead;
	}

	public List<List<String>> getMatchedResultList() {
		return matchedResultList;
	}

	public void setMatchedResultList(List<List<String>> matchedResultList) {
		this.matchedResultList = matchedResultList;
	}

	public List<String> getUnmatchHead() {
		return unmatchHead;
	}

	public void setUnmatchHead(List<String> unmatchHead) {
		this.unmatchHead = unmatchHead;
	}

	public List<List<String>> getUnmatchResultList() {
		return unmatchResultList;
	}

	public void setUnmatchResultList(List<List<String>> unmatchResultList) {
		this.unmatchResultList = unmatchResultList;
	}
}
