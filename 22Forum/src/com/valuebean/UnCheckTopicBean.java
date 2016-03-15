package com.valuebean;

import java.util.Map;

public class UnCheckTopicBean {
	private Map category;						//未审核帖子所属的类别ID和名称
	private Map board;							//未审核帖子所属的版块ID和名称
	private TopicBean topic;					//未审核帖子
	
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
	public TopicBean getTopic() {
		return topic;
	}
	public void setTopic(TopicBean topic) {
		this.topic = topic;
	}	
}