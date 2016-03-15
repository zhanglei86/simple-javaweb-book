package com.wy.model;

public class AdminInfo {
	private Integer id=-1;				//自动编号属性设置
	private String account="";			//管理员账号属性设置
	private String password="";			//管理员登录密码属性设置
	private Integer visit=-1;			//管理员访问次数设置
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getVisit() {
		return visit;
	}
	public void setVisit(Integer visit) {
		this.visit = visit;
	}
	
	
	
	

}
