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

	// 查询字条信息，返回值为List集合，用于保存查询到的字条信息
	public List<ScripForm> query(String condition) {
		List<ScripForm> list = new ArrayList<ScripForm>();
		String sql = "";
		if (condition == null) {
			sql = "SELECT * FROM tb_scrip";
		} else {
			sql = "SELECT * FROM tb_scrip " + condition;
		}
		System.out.println("执行的SQL语句："+sql);
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
				list.add(scripF); // 将字条信息保存到List集合中
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//conn.close();
		return list;
	}

	// 查询最新的10条字条信息
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

	// 获取字条总数
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
			System.out.println("获取记录总数时产生的错误：" + e.getMessage());
		}
		return rsCount;
	}

	// 添加字条
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
				str = "恭喜您，贴字条成功，请记住您的字条编号：[" + flag + "]";
			} else {
				str = "很报谦，您的字条发送失败！";
			}
			System.out.println("添加字条信息的SQL：" + sql);
		} catch (Exception e) {
			str = "很报谦，您的字条发送失败！";
			System.out.println("添加字条时的错误提示：" + e.getMessage());
		}
		//conn.close();
		return str;
	}

	// 添加支持，即将指定字条的单击数加1，返回值为字条ID和最新单击数连接成的字符串
	public String holdoutAdd(int id) {
		String sql = "UPDATE tb_scrip SET hits=hits+1 WHERE id=" + id + "";
		conn.executeUpdate(sql);
		String sql1 = "SELECT * FROM tb_scrip WHERE id=" + id + "";
		ResultSet rs = conn.executeQuery(sql1);
		String rtn = "";
		try {
			if (rs.next()) {
				rtn = id + "#" + String.valueOf(rs.getInt("hits")); // 组合成一个“ID#Hits”格式的字符串
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rtn;
	}
}
