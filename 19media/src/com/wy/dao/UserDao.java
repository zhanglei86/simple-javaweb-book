package com.wy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.wy.model.UserInfo;
import com.wy.tool.JDBConnection;

public class UserDao {
	private JDBConnection connection = null;

	private String sql = null;

	public UserDao() {
		connection = new JDBConnection();
	}

	public boolean user_add(UserInfo userInfo) {
		sql = "insert into tb_user values ('" + userInfo.getUser_name() + "','"
				+ userInfo.getUser_pswd() + "','" + userInfo.getUser_sex()
				+ "','" + userInfo.getUser_oicq() + "','"
				+ userInfo.getUser_email() + "','" + userInfo.getUser_from()
				+ "',getDate(),0)";
		return connection.executeUpdata(sql);
	}

	public List<UserInfo> user_query() {
		List<UserInfo> list = new ArrayList<UserInfo>();
		sql = "select * from tb_user";
		UserInfo userInfo = new UserInfo();
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				userInfo.setId(rs.getInt(1));
				userInfo.setUser_name(rs.getString(2));
				userInfo.setUser_pswd(rs.getString(3));
				userInfo.setUser_sex(rs.getString(4));
				userInfo.setUser_oicq(rs.getString(5));
				userInfo.setUser_email(rs.getString(6));
				userInfo.setUser_from(rs.getString(7));
				userInfo.setUser_ctTime(rs.getString(8));
				userInfo.setUser_hitNum(rs.getInt(9));				
				list.add(userInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public UserInfo user_queryOne(String user_name){		
		sql = "select * from tb_user where user_name = '"+user_name+"'";		
		UserInfo userInfo =null;
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				userInfo=new UserInfo();
				userInfo.setId(rs.getInt(1));
				userInfo.setUser_name(rs.getString(2));
				userInfo.setUser_pswd(rs.getString(3));
				userInfo.setUser_sex(rs.getString(4));
				userInfo.setUser_oicq(rs.getString(5));
				userInfo.setUser_email(rs.getString(6));
				userInfo.setUser_from(rs.getString(7));
				userInfo.setUser_ctTime(rs.getString(8));
				userInfo.setUser_hitNum(rs.getInt(9));							
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return userInfo;				
	}
	
	public void user_addHit(String user_name){
		sql="update tb_user set user_hitNum=user_hitNum+1 where user_name = '"+user_name+"'";
		connection.executeUpdata(sql);
	}
	
	public void user_delete(Integer id){
		sql="delete from tb_user where id ="+id+"";
		connection.executeUpdata(sql);
	}

}
