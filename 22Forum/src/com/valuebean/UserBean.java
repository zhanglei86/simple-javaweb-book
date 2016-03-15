package com.valuebean;

import com.toolsbean.StringHandler;

public class UserBean {
	private int 		id;						//��ԱIDֵ
	private int 		groupId;				//��Ա�������IDֵ
	private String 		memberName;				//��Ա����
	private String 		memberPswd;				//��Ա����
	private String 		memberSex;				//��Ա�Ա�
	private int 		memberAge;				//��Ա����
	private String 		memberOICQ;				//��ԱQQ����
	private String 		memberIcon;				//��Աͷ��
	private String 		memberSign;				//��Ա����ǩ��
	private String 		memberStatus;			//��Ա״̬��1-���0-����
	private String 		memberRegTime;			//��Աע��ʱ��
	private String 		memberLogonTime;		//��Ա�ϴε�¼ʱ��
	private int			memberPostNum;			//��Ա����������	
	private int 		memberExperience;		//��Ա�ľ���ֵ
	private int 		memberCharm;			//��Ա������ֵ	
	private GradeBean 	memberGrade;			//��Ա�ȼ�
	private int 		newmessages;			//��Ա��δ����Ϣ��
	private int[] 		assignCategoryId;		//�洢Ϊ��Ա��������ID
	private int[] 		assignBoardId;			//�洢Ϊ��Ա����İ��ID
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPswd() {
		return memberPswd;
	}
	public void setMemberPswd(String memberPswd) {
		this.memberPswd = memberPswd;
	}	
	public String getMemberSex() {
		return memberSex;
	}
	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}
	public int getMemberAge() {
		return memberAge;
	}
	public void setMemberAge(int memberAge) {
		this.memberAge = memberAge;
	}	
	public String getMemberOICQ() {
		return memberOICQ;
	}
	public void setMemberOICQ(String memberOICQ) {
		this.memberOICQ = memberOICQ;
	}
	public String getMemberIcon() {
		return memberIcon;
	}
	public void setMemberIcon(String memberIcon) {
		this.memberIcon = memberIcon;
	}
	public String getMemberSign() {
		return StringHandler.changehtml(memberSign);
	}
	public String getMemberSignForEdit() {
		return memberSign;
	}
	public void setMemberSign(String memberSign) {
		this.memberSign = memberSign;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getMemberRegTime() {
		return memberRegTime;
	}
	public void setMemberRegTime(String memberRegTime) {
		this.memberRegTime = memberRegTime;
	}
	public String getMemberLogonTime() {
		return memberLogonTime;
	}
	public void setMemberLogonTime(String memberLogonTime) {
		this.memberLogonTime = memberLogonTime;
	}	
	public int getMemberPostNum() {
		return memberPostNum;
	}
	public void setMemberPostNum(int memberPostNum) {
		this.memberPostNum = memberPostNum;
	}
	public int getMemberExperience() {
		return memberExperience;
	}
	public void setMemberExperience(int memberExperience) {
		this.memberExperience = memberExperience;
	}
	public int getMemberCharm() {
		return memberCharm;
	}
	public void setMemberCharm(int memberCharm) {
		this.memberCharm = memberCharm;
	}	
	public GradeBean getMemberGrade() {
		return memberGrade;
	}
	public void setMemberGrade(GradeBean memberGrade) {
		this.memberGrade = memberGrade;
	}	
	public int getNewmessages() {
		return newmessages;
	}
	public void setNewmessages(int newmessages) {
		this.newmessages = newmessages;
	}
	public int[] getAssignCategoryId() {
		return assignCategoryId;
	}	
	public void setAssignCategoryId(int[] assignCategoryId) {
		this.assignCategoryId = assignCategoryId;
	}
	public int[] getAssignBoardId() {
		return assignBoardId;
	}
	public void setAssignBoardId(int[] assignBoardId) {
		this.assignBoardId = assignBoardId;
	}
}
