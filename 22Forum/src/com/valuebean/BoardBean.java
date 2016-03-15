package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class BoardBean {
	private int id;							//���IDֵ
	private int categoryId;					//�洢�����������IDֵ
	private String boardName;				//�������
	private String boardInfo;				//�������
	private String boardStatus;				//���״̬��2-���ţ�1-������0-�ر�
	private int boardOrder;					//�������д���
	private String boardFoundTime;			//��鴴��ʱ��
	private TopicBean lastTopic;			//�������󷢱������
	private int boardAllTopicNum;			//�����������
	private int boardBestTopicNum;			//����о���������
	private List masters;					//�������а�������
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getBoardName() {
		return StringHandler.changehtml(boardName);
	}
	public String getBoardNameForEdit() {
		return boardName;
	}
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	public String getBoardInfo() {
		return StringHandler.changehtml(boardInfo);
	}
	public String getBoardInfoForEdit() {
		return boardInfo;
	}
	public void setBoardInfo(String boardInfo) {
		this.boardInfo = boardInfo;
	}
	public String getBoardStatus() {
		return boardStatus;
	}
	public void setBoardStatus(String boardStatus) {
		this.boardStatus = boardStatus;
	}
	public int getBoardOrder() {
		return boardOrder;
	}
	public void setBoardOrder(int boardOrder) {
		this.boardOrder = boardOrder;
	}
	public String getBoardFoundTime() {
		return boardFoundTime;
	}
	public void setBoardFoundTime(String boardFoundTime) {
		this.boardFoundTime = boardFoundTime;
	}
	public TopicBean getLastTopic() {
		return lastTopic;
	}
	public void setLastTopic(TopicBean lastTopic) {
		this.lastTopic = lastTopic;
	}
	public int getBoardAllTopicNum() {
		return boardAllTopicNum;
	}
	public void setBoardAllTopicNum(int boardAllTopicNum) {
		this.boardAllTopicNum = boardAllTopicNum;
	}
	public int getBoardBestTopicNum() {
		return boardBestTopicNum;
	}
	public void setBoardBestTopicNum(int boardBestTopicNum) {
		this.boardBestTopicNum = boardBestTopicNum;
	}
	public List getMasters() {
		return masters;
	}
	public void setMasters(List masters) {
		this.masters = masters;
	}	
}
