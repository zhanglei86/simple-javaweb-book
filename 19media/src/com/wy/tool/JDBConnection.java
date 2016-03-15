package com.wy.tool;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.*;

public class JDBConnection {
	
	private static DataSource ds=null;								//����DataSource��Ķ���
	private static Connection conn =null;								//����Connection��Ķ���
	private static Statement st = null;									//����Statement��Ķ���
	private ResultSet rs=null;										//����ResultSet��Ķ���
	static {
		try {
			Context ctx = new InitialContext();
			ctx = (Context) ctx.lookup("java:comp/env");
			ds = (DataSource) ctx.lookup("TestJNDI");				//ȡ�����ӳ�����Դ
			conn = ds.getConnection();								//ȡ�����ݿ������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ResultSet executeQuery(String sql) {
		try {	
			st = conn.createStatement();
			rs = st.executeQuery(sql);     					//ִ�ж����ݿ�Ĳ�ѯ����
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Query Exception");				//�ڿ���̨�������쳣��Ϣ
		}
		return rs;                                           //����ѯ�Ľ��ͨ��return�ؼ��ַ���
	}
		public boolean executeUpdata(String sql) {
		try {
			st = conn.createStatement();							//����������������
			st.executeUpdate(sql);                           //ִ����ӡ��޸ġ�ɾ������
			return true;                                   //���ִ�гɹ��򷵻�true
		} catch (Exception e) {
			e.printStackTrace();
	    return false;                                      //���ִ�гɹ��򷵻�false
		}	
	}

	

}
