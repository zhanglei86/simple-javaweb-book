package com.wy.model;

public class MediaInfo {
	private int id;						//ϵͳ�������
	private String mediaTitle;			//��Ƶ��������
	private String media_type;			//��Ƶ����
	private String mediaSrc;			//��Ƶ�ļ���ַ����
	private String mediaPic;			//��Ƶ��ͼ����
	private String mediaInfo;			//��Ƶ����
	private String mediaUptime;			//��Ƶ�ϴ�ʱ��
	private String lookCount;			//��Ƶ�ϴ�ʱ��
	public int getId() {		
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLookCount() {
		return lookCount;
	}
	public void setLookCount(String lookCount) {
		this.lookCount = lookCount;
	}
	public String getMediaInfo() {
		return mediaInfo;
	}
	public void setMediaInfo(String mediaInfo) {
		this.mediaInfo = mediaInfo;
	}
	public String getMediaPic() {
		return mediaPic;
	}
	public void setMediaPic(String mediaPic) {
		this.mediaPic = mediaPic;
	}
	public String getMediaSrc() {
		return mediaSrc;
	}
	public void setMediaSrc(String mediaSrc) {
		this.mediaSrc = mediaSrc;
	}
	public String getMediaTitle() {
		return mediaTitle;
	}
	public void setMediaTitle(String mediaTitle) {
		this.mediaTitle = mediaTitle;
	}
	public String getMediaUptime() {
		return mediaUptime;
	}
	public void setMediaUptime(String mediaUptime) {
		this.mediaUptime = mediaUptime;
	}

	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	

}
