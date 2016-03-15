package com.wy.model;

public class MediaRInfo {
	private Integer id=-1;
	private Integer  mediaR_rootId = -1;
	private String mediaR_author="";
	private String mediaR_content="";
	private String mediaR_time="";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMediaR_author() {
		return mediaR_author;
	}
	public void setMediaR_author(String mediaR_author) {
		this.mediaR_author = mediaR_author;
	}
	public String getMediaR_content() {
		return mediaR_content;
	}
	public void setMediaR_content(String mediaR_content) {
		this.mediaR_content = mediaR_content;
	}
	public Integer getMediaR_rootId() {
		return mediaR_rootId;
	}
	public void setMediaR_rootId(Integer mediaR_rootId) {
		this.mediaR_rootId = mediaR_rootId;
	}
	public String getMediaR_time() {
		return mediaR_time;
	}
	public void setMediaR_time(String mediaR_time) {
		this.mediaR_time = mediaR_time;
	}
	

}
