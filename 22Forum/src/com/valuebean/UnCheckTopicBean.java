package com.valuebean;

import java.util.Map;

public class UnCheckTopicBean {
	private Map category;						//δ����������������ID������
	private Map board;							//δ������������İ��ID������
	private TopicBean topic;					//δ�������
	
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