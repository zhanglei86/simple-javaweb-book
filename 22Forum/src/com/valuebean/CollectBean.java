package com.valuebean;

import java.util.Map;

public class CollectBean {
	private int id;									//收藏ID
	private int memberId;							//收藏者ID
	private String collectTime; 					//收藏时间
	private TopicBean collectTopic;					//收藏的帖子
	private Map category;							//收藏的帖子所属类别的ID和名称
	private Map board;								//收藏的帖子所属版块的ID和名称
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	public TopicBean getCollectTopic() {
		return collectTopic;
	}
	public void setCollectTopic(TopicBean collectTopic) {
		this.collectTopic = collectTopic;
	}
	public Map getCategory() {
		return category;
	}
	public void setCategory(Map category) {
		this.category = category;
	}
	public Map getBoard() {
		return board;
	}
	public void setBoard(Map board) {
		this.board = board;
	}	
}