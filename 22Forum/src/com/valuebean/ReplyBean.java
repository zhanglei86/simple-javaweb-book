package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class ReplyBean {
	private int id;
	private int topicId;
	private String replyTitle;
	private String replyContent;
	private String replyAuthorName;
	private UserBean replyAuthor;
	private String replyEmotion;
	private String replyAttachmentSign;
	private List replyAttachment;
	private String replyReplyTime;
	
	public ReplyBean(){
		replyEmotion="face01.gif";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}	
	public String getReplyTitle() {
		return StringHandler.changehtml(replyTitle);
	}
	public String getReplyTitleForEdit() {
		return replyTitle;
	}
	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public String getReplyContentForJsp() {
		return StringHandler.changeQuoteSign(replyContent);
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyAuthorName() {
		return replyAuthorName;
	}
	public void setReplyAuthorName(String replyAuthorName) {
		this.replyAuthorName = replyAuthorName;
	}
	public UserBean getReplyAuthor() {
		return replyAuthor;
	}
	public void setReplyAuthor(UserBean replyAuthor) {
		this.replyAuthor = replyAuthor;
	}
	public String getReplyEmotion() {
		return replyEmotion;
	}
	public void setReplyEmotion(String replyEmotion) {
		this.replyEmotion = replyEmotion;
	}
	public String getReplyAttachmentSign() {
		return replyAttachmentSign;
	}
	public void setReplyAttachmentSign(String replyAttachmentSign) {
		this.replyAttachmentSign = replyAttachmentSign;
	}	
	public List getReplyAttachment() {
		return replyAttachment;
	}
	public void setReplyAttachment(List replyAttachment) {
		this.replyAttachment = replyAttachment;
	}
	public String getReplyReplyTime() {
		return replyReplyTime;
	}
	public void setReplyReplyTime(String replyReplyTime) {
		this.replyReplyTime = replyReplyTime;
	}
}
