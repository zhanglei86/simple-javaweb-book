package com.valuebean;

import com.toolsbean.StringHandler;

public class UserBean {
	private int 		id;						//会员ID值
	private int 		groupId;				//会员所属组的ID值
	private String 		memberName;				//会员名称
	private String 		memberPswd;				//会员密码
	private String 		memberSex;				//会员性别
	private int 		memberAge;				//会员年龄
	private String 		memberOICQ;				//会员QQ号码
	private String 		memberIcon;				//会员头像
	private String 		memberSign;				//会员个性签名
	private String 		memberStatus;			//会员状态：1-活动；0-冻结
	private String 		memberRegTime;			//会员注册时间
	private String 		memberLogonTime;		//会员上次登录时间
	private int			memberPostNum;			//会员发表主题数	
	private int 		memberExperience;		//会员的经验值
	private int 		memberCharm;			//会员的魅力值	
	private GradeBean 	memberGrade;			//会员等级
	private int 		newmessages;			//会员的未读消息数
	private int[] 		assignCategoryId;		//存储为会员分配的类别ID
	private int[] 		assignBoardId;			//存储为会员分配的版块ID
	
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
