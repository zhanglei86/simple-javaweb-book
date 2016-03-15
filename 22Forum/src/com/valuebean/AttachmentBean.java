package com.valuebean;

import com.toolsbean.StringHandler;

public class AttachmentBean {
	private int id;
	private int topicId;
	private int replyId;
	private String attachmentSaveName;
	private String attachmentFileName;
	private String attachmentFileType;
	private String attachmentFileSize;
	private String attachmentUpTime;
	
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
	public int getReplyId() {
		return replyId;
	}
	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}
	public String getAttachmentSaveName() {
		return attachmentSaveName;
	}
	public void setAttachmentSaveName(String attachmentSaveName) {
		this.attachmentSaveName = attachmentSaveName;
	}
	public String getAttachmentFileName() {
		return StringHandler.changehtml(attachmentFileName);
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}	
	public String getAttachmentFileType() {
		return attachmentFileType;
	}
	public void setAttachmentFileType(String attachmentFileType) {
		this.attachmentFileType = attachmentFileType;
	}
	public String getAttachmentFileSize() {
		return attachmentFileSize;
	}
	public void setAttachmentFileSize(String attachmentFileSize) {
		this.attachmentFileSize = attachmentFileSize;
	}
	public String getAttachmentUpTime() {
		return attachmentUpTime;
	}
	public void setAttachmentUpTime(String attachmentUpTime) {
		this.attachmentUpTime = attachmentUpTime;
	}	
}
