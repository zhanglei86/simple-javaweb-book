package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class TopicBean {
	private int id;									//����ID
	private int boardId;							//�洢������������IDֵ
	private String topicTitle;						//�������
	private String topicContent;					//��������
	private String topicAuthorName;					//�������ߵ�����
	private UserBean topicAuthor;					//��������
	private String topicEmotion;					//��������
	private int topicReplyNum;						//����Ļظ���
	private int topicHits;							//������������
	private ReplyBean lastReply;					//��������ظ���
	private String topicType;						//�������ͣ�2-�ö���1-������0-��ͨ
	private String topicStatus;						//����״̬��2-������1-������0-�ر�
	private String topicExistReply;					//�Ƿ���ڻظ����ӵı���ʶ��1-�У�0-��
	private String topicAttachmentSign;				//�Ƿ���и����ı�ʶ��1-�У�0-��
	private List topicAttachment;					//���⸽��
	private String topicPostTime;					//����ķ���ʱ��
	private String topicOperateTime;				//���ⱻ������ʱ��
	
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