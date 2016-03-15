package com.wy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wy.model.AdminInfo;
import com.wy.tool.JDBConnection;

public class AdminDao {
	private static JDBConnection connection = null;

	private String sql = null;

	public AdminDao() {
		connection = new JDBConnection();
	}
	
//根据管理员名称查询当前管理员的数据
	public AdminInfo admin_queryPassword(String account) {
		AdminInfo info = null;
		sql = "select * from tb_admin where account='" + account + "'";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				info=new AdminInfo();
				info.setId(rs.getInt(1));
				info.setAccount(rs.getString(2));
				info.setPassword(rs.getString(3));
				info.setVisit(rs.getInt(4));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return info;
	}
	
	
	
	public void admin_addVisit(){
		sql="update tb_admin set visit=visit+1 where account='mr'";
		connection.executeUpdata(sql);
		
	}
	

}
