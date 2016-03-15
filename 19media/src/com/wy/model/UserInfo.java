package com.wy.model;

public class UserInfo {
	private Integer id=-1;				//自动编号属性
	private String user_name="";		//用户名属性
	private String user_pswd="";		//用户登录密码属性
	private String user_sex="";			//用户性别属性
	private String user_oicq="";		//用户QQ号码属性
	private String user_email="";		//用户联系Email地址属性
	private String user_from="";		//用户来自何方属性
	private String user_ctTime="";		//用户注册时间属性
	private Integer user_hitNum=-1;		//用户访问次数属性	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser_ctTime() {
		return user_ctTime;
	}
	public void setUser_ctTime(String user_ctTime) {
		this.user_ctTime = user_ctTime;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_from() {
		return user_from;
	}
	public void setUser_from(String user_from) {
		this.user_from = user_from;
	}
	public Integer getUser_hitNum() {
		return user_hitNum;
	}
	public void setUser_hitNum(Integer user_hitNum) {
		this.user_hitNum = user_hitNum;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_oicq() {
		return user_oicq;
	}
	public void setUser_oicq(String user_oicq) {
		this.user_oicq = user_oicq;
	}
	public String getUser_pswd() {
		return user_pswd;
	}
	public void setUser_pswd(String user_pswd) {
		this.user_pswd = user_pswd;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	

}
