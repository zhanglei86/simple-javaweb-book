package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class BoardBean {
	private int id;							//版块ID值
	private int categoryId;					//存储版块所属类别的ID值
	private String boardName;				//版块名称
	private String boardInfo;				//版块描述
	private String boardStatus;				//版块状态：2-开放；1-锁定；0-关闭
	private int boardOrder;					//版块的排列次序
	private String boardFoundTime;			//版块创建时间
	private TopicBean lastTopic;			//版块中最后发表的主题
	private int boardAllTopicNum;			//版块中主题数
	private int boardBestTopicNum;			//版块中精华帖子数
	private List masters;					//版块的所有版主名称
	
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
