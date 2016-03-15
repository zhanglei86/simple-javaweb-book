package com.dao;

import java.sql.SQLException;

import com.toolsbean.DB;

public class GradeDao {
	private int exper_postPoint;				//发表帖子后，作者所增加的经验值
	private int exper_replyPoint;				//回复帖子后，作者所增加的经验值
	private int exper_bestPoint;				//帖子加精后，帖主所增加的经验值
	private int charm_viewPoint;				//观看帖子后，帖主所增加的魅力值
	private int charm_replyPoint;				//回复帖子后，帖主所增加的魅力值
	
	public GradeDao(){
		exper_postPoint=3;
		exper_replyPoint=2;
		exper_bestPoint=10;
		charm_viewPoint=1;
		charm_replyPoint=5;	
	}
	/**
	 *@功能：发帖后经验值的计算
	 *@参数：memberId为作者ID值 
	 */
	public void experPost(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_experience=member_experience+"+exper_postPoint+" where id=?";
		int i=update(sql, params);
		if(i<=0)
			System.out.println("发帖后，增加经验值失败！");
	}
	/**
	 *@功能：回帖后经验值的计算
	 *@参数：memberId为作者ID值 
	 */
	public void experReply(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_experience=member_experience+"+exper_replyPoint+" where id=?";
		int i=update(sql, params);
		if(i<=0)
			System.out.println("回帖后，增加经验值失败！");
	}
	/**
	 *@功能：加精后经验值的计算
	 *@参数：memberId为帖主ID值 
	 */
	public void experBest(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_experience=member_experience+"+exper_bestPoint+" where id=?";
		int i=update(sql, params);
		if(i<=0)
			System.out.println("加精后，增加经验值失败！");
	}
	/**
	 *@功能：观看主题后魅力值的计算
	 *@参数：memberName为帖主名字 
	 */
	public void charmLook(String memberName){
		Object[] params={memberName};
		String sql="update tb_member set member_charm=member_charm+"+charm_viewPoint+" where member_name=?";
		int i=update(sql,params);		
		if(i<=0)
			System.out.println("观看主题后，增加帖主魅力值失败！");
	}
	/**
	 *@功能：回复主题后魅力值的计算
	 *@参数：memberId为帖主ID值 
	 */
	public void charmReply(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_charm=member_charm+"+charm_replyPoint+" where id=?";
		int i=update(sql,params);
		if(i<=0)
			System.out.println("回复主题后，增加帖主魅力值失败！");
	}
	/**
	 *@功能：回复主题后魅力值的计算
	 *@参数：memberName为帖主名称
	 */
	public void charmReply(String memberName){
		Object[] params={memberName};
		String sql="update tb_member set member_charm=member_charm+"+charm_replyPoint+" where member_name=?";
		update(sql,params);
	}
	private int update(String sql,Object[] params){
		int i=-1;
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			 i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		mydb.closed();
		return i;
	}	
}
