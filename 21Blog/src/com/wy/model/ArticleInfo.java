package com.wy.model;

public class ArticleInfo {
	private Integer id=-1;
	private String typeName="";
	private String title="";
	private String content="";
	private String author="";
	private String sendTime="";
	private Integer visit=0;
	private String commend="·ñ";
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCommend() {
		return commend;
	}
	public void setCommend(String commend) {
		this.commend = commend;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {		
		this.sendTime = sendTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
}
