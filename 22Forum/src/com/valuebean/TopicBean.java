package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class TopicBean {
	private int id;									//主题ID
	private int boardId;							//存储主题所属版块的ID值
	private String topicTitle;						//主题标题
	private String topicContent;					//主题内容
	private String topicAuthorName;					//主题作者的名字
	private UserBean topicAuthor;					//主题作者
	private String topicEmotion;					//主题心情
	private int topicReplyNum;						//主题的回复数
	private int topicHits;							//主题的浏览次数
	private ReplyBean lastReply;					//主题的最后回复帖
	private String topicType;						//主题类型：2-置顶；1-精华；0-普通
	private String topicStatus;						//主题状态：2-开发；1-锁定；0-关闭
	private String topicExistReply;					//是否存在回复帖子的标认识：1-有；0-无
	private String topicAttachmentSign;				//是否带有附件的标识：1-有；0-无
	private List topicAttachment;					//主题附件
	private String topicPostTime;					//主题的发表时间
	private String topicOperateTime;				//主题被操作的时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getTopicTitle() {
		return StringHandler.changehtml(topicTitle);
	}
	public String getTopicTitleForEdit() {
		return topicTitle;
	}
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	public String getTopicContent() {
		return topicContent;
	}
	public void setTopicContent(String topicContent) {
		this.topicContent = topicContent;
	}	
	public String getTopicAuthorName() {
		return topicAuthorName;
	}
	public void setTopicAuthorName(String topicAuthorName) {
		this.topicAuthorName = topicAuthorName;
	}
	public UserBean getTopicAuthor() {
		return topicAuthor;
	}
	public void setTopicAuthor(UserBean topicAuthor) {
		this.topicAuthor = topicAuthor;
	}
	public String getTopicEmotion() {
		return topicEmotion;
	}
	public void setTopicEmotion(String topicEmotion) {
		this.topicEmotion = topicEmotion;
	}
	public int getTopicReplyNum() {
		return topicReplyNum;
	}
	public void setTopicReplyNum(int topicReplyNum) {
		this.topicReplyNum = topicReplyNum;
	}
	public int getTopicHits() {
		return topicHits;
	}
	public void setTopicHits(int topicHits) {
		this.topicHits = topicHits;
	}
	public ReplyBean getLastReply() {
		return lastReply;
	}
	public void setLastReply(ReplyBean lastReply) {
		this.lastReply = lastReply;
	}
	public String getTopicType() {
		return topicType;
	}
	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}
	public String getTopicStatus() {
		return topicStatus;
	}
	public void setTopicStatus(String topicStatus) {
		this.topicStatus = topicStatus;
	}
	public String getTopicExistReply() {
		return topicExistReply;
	}
	public void setTopicExistReply(String topicExistReply) {
		this.topicExistReply = topicExistReply;
	}
	public String getTopicAttachmentSign() {
		return topicAttachmentSign;
	}
	public void setTopicAttachmentSign(String topicAttachmentSign) {
		this.topicAttachmentSign = topicAttachmentSign;
	}
	public List getTopicAttachment() {
		return topicAttachment;
	}
	public void setTopicAttachment(List topicAttachment) {
		this.topicAttachment = topicAttachment;
	}
	public String getTopicPostTime() {
		return topicPostTime;
	}
	public void setTopicPostTime(String topicPostTime) {
		this.topicPostTime = topicPostTime;
	}
	public String getTopicOperateTime() {
		return topicOperateTime;
	}
	public void setTopicOperateTime(String topicOperateTime) {
		this.topicOperateTime = topicOperateTime;
	}
}