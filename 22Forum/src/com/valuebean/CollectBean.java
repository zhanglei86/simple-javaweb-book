package com.valuebean;

import java.util.Map;

public class CollectBean {
	private int id;									//�ղ�ID
	private int memberId;							//�ղ���ID
	private String collectTime; 					//�ղ�ʱ��
	private TopicBean collectTopic;					//�ղص�����
	private Map category;							//�ղص�������������ID������
	private Map board;								//�ղص�������������ID������
	
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