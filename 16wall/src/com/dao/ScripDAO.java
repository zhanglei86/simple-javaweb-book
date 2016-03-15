package com.dao;

import com.model.ScripForm;
import com.tools.ConnDB;
import java.util.*;
import java.sql.*;

public class ScripDAO {
	private ConnDB conn;

	public ScripDAO() {
		conn = new ConnDB();
	}

	// ��ѯ������Ϣ������ֵΪList���ϣ����ڱ����ѯ����������Ϣ
	public List<ScripForm> query(String condition) {
		List<ScripForm> list = new ArrayList<ScripForm>();
		String sql = "";
		if (condition == null) {
			sql = "SELECT * FROM tb_scrip";
		} else {
			sql = "SELECT * FROM tb_scrip " + condition;
		}
		System.out.println("ִ�е�SQL��䣺"+sql);
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				ScripForm scripF = new ScripForm();
				scripF.setId(rs.getInt(1));
				scripF.setWishMan(rs.getString(2));
				scripF.setWellWisher(rs.getString(3));
				scripF.setContent(rs.getString(4));
				scripF.setColor(rs.getInt(5));
				scripF.setFace(rs.getInt(6));
				scripF.setSendTime(rs.getDate(7));
				scripF.setHits(rs.getInt(8));
				list.add(scripF); // ��������Ϣ���浽List������
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//conn.close();
		return list;
	}

	// ��ѯ���µ�10��������Ϣ
	public List<ScripForm> queryTop() {
		List<ScripForm> list = new ArrayList<ScripForm>();
		String sql = "SELECT TOP 10 * FROM tb_scrip ORDER BY sendTime DESC";
		ResultSet rs = conn.executeQuery(sql);
		try {
			while (rs.next()) {
				ScripForm scripF = new ScripForm();
				scripF.setId(rs.getInt(1));
				scripF.setWishMan(rs.getString(2));
				scripF.setWellWisher(rs.getString(3));
				scripF.setContent(rs.getString(4));
				scripF.setColor(rs.getInt(5));
				scripF.setFace(rs.getInt(6));
				scripF.setSendTime(rs.getDate(7));
				scripF.setHits(rs.getInt(8));
				list.add(scripF);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//conn.close();
		return list;
	}

	// ��ȡ��������
	public int getRSCount() {
		String sql = "SELECT COUNT(*) AS count FROM tb_scrip";
		ResultSet rs = conn.executeQuery(sql);
		int rsCount = 0;
		try {
			if (rs.next()) {
				rsCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("��ȡ��¼����ʱ�����Ĵ���" + e.getMessage());
		}
		return rsCount;
	}

	// �������
	public String insert(ScripForm SF) {
		String str = "";
		try {
			String sql = "INSERT INTO tb_scrip (wishMan,wellWisher,color,face,content) values('"
					+ SF.getWishMan()
					+ "','"
					+ SF.getWellWisher()
					+ "',"
					+ SF.getColor()
					+ ","
					+ SF.getFace()
					+ ",'"
					+ SF.getContent() + "')";
			int flag = conn.executeUpdate_id(sql);
			if (flag > 0) {
				str = "��ϲ�����������ɹ������ס����������ţ�[" + flag + "]";
			} else {
				str = "�ܱ�ǫ��������������ʧ�ܣ�";
			}
			System.out.println("���������Ϣ��SQL��" + sql);
		} catch (Exception e) {
			str = "�ܱ�ǫ��������������ʧ�ܣ�";
			System.out.println("�������ʱ�Ĵ�����ʾ��" + e.getMessage());
		}
		//conn.close();
		return str;
	}

	// ���֧�֣�����ָ�������ĵ�������1������ֵΪ����ID�����µ��������ӳɵ��ַ���
	public String holdoutAdd(int id) {
		String sql = "UPDATE tb_scrip SET hits=hits+1 WHERE id=" + id + "";
		conn.executeUpdate(sql);
		String sql1 = "SELECT * FROM tb_scrip WHERE id=" + id + "";
		ResultSet rs = conn.executeQuery(sql1);
		String rtn = "";
		try {
			if (rs.next()) {
				rtn = id + "#" + String.valueOf(rs.getInt("hits")); // ��ϳ�һ����ID#Hits����ʽ���ַ���
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
