package com.valuebean;

public class MessageBean {
	private int id;
	private String messageTitle;
	private String messageContent;
	private String messageSendTime;
	private String messageEmotion;
	private String messageReadmark;
	private String messageSender;
	private String messageGetter;
	private String deleteSender;
	private String deleteGetter;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageSendTime() {
		return messageSendTime;
	}
	public void setMessageSendTime(String messageSendTime) {
		this.messageSendTime = messageSendTime;
	}
	public String getMessageEmotion() {
		return messageEmotion;
	}
	public void setMessageEmotion(String messageEmotion) {
		this.messageEmotion = messageEmotion;
	}
	public String getMessageReadmark() {
		return messageReadmark;
	}
	public void setMessageReadmark(String messageReadmark) {
		this.messageReadmark = messageReadmark;
	}
	public String getMessageSender() {
		return messageSender;
	}
	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}
	public String getMessageGetter() {
		return messageGetter;
	}
	public void setMessageGetter(String messageGetter) {
		this.messageGetter = messageGetter;
	}
	public String getDeleteSender() {
		return deleteSender;
	}
	public void setDeleteSender(String deleteSender) {
		this.deleteSender = deleteSender;
	}
	public String getDeleteGetter() {
		return deleteGetter;
	}
	public void setDeleteGetter(String deleteGetter) {
		this.deleteGetter = deleteGetter;
	}
}
