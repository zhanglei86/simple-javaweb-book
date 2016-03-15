package com.wy.tool;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.*;

public class JDBConnection {
	
	private static DataSource ds=null;								//设置DataSource类的对象
	private static Connection conn =null;								//设置Connection类的对象
	private static Statement st = null;									//设置Statement类的对象
	private ResultSet rs=null;										//设置ResultSet类的对象
	static {
		try {
			Context ctx = new InitialContext();
			ctx = (Context) ctx.lookup("java:comp/env");
			ds = (DataSource) ctx.lookup("TestJNDI");				//取得连接池数据源
			conn = ds.getConnection();								//取得数据库的连接
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ResultSet executeQuery(String sql) {
		try {	
			st = conn.createStatement();
			rs = st.executeQuery(sql);     					//执行对数据库的查询操作
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Query Exception");				//在控制台中输入异常信息
		}
		return rs;                                           //将查询的结果通过return关键字返回
	}
		public boolean executeUpdata(String sql) {
		try {
			st = conn.createStatement();							//创建声明对象连接
			st.executeUpdate(sql);                           //执行添加、修改、删除操作
			return true;                                   //如果执行成功则返回true
		} catch (Exception e) {
			e.printStackTrace();
	    return false;                                      //如果执行成功则返回false
		}	
	}

	

}
