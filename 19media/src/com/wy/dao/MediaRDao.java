package com.wy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wy.model.MediaRInfo;
import com.wy.tool.JDBConnection;

public class MediaRDao {
	private static JDBConnection connection = null;

	private String sql = null;

	public MediaRDao() {
		connection = new JDBConnection();
	}

	public List mediaR_query(Integer mediaR_rootId) {
		List list = new ArrayList();
		MediaRInfo mediaRInfo = null;
		sql = "select * from tb_mediaR where mediaR_rootId = " + mediaR_rootId
				+ " order by id desc";
		ResultSet rs = connection.executeQuery(sql);			
		try {
			while (rs.next()) {
				mediaRInfo = new MediaRInfo();
				mediaRInfo.setId(rs.getInt(1));
				mediaRInfo.setMediaR_rootId(rs.getInt(2));
				mediaRInfo.setMediaR_author(rs.getString(3));
				mediaRInfo.setMediaR_content(rs.getString(4));
				mediaRInfo.setMediaR_time(rs.getString(5));
				list.add(mediaRInfo);
			}
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		return list;
	}

	public void media_add(MediaRInfo mediaRInfo) {
		sql = "insert into tb_mediaR values (" + mediaRInfo.getMediaR_rootId()
				+ ",'" + mediaRInfo.getMediaR_author() + "','"
				+ mediaRInfo.getMediaR_content() + "',getDate())";		
		connection.executeUpdata(sql);
	}
	
	public void media_deleteAllUser(String mediaR_author){
		sql="delete from tb_mediaR where mediaR_author = '"+mediaR_author+"'";
		connection.executeUpdata(sql);
	}
	
	public void media_deleteAllId(Integer mediaR_rootId){
		sql="delete from tb_mediaR where mediaR_rootId = "+mediaR_rootId+"";
		connection.executeUpdata(sql);
	}
	
	
	public void media_delete(Integer id){
		sql="delete from tb_mediaR where id = "+id+"";
		connection.executeUpdata(sql);
	}
	
	
	
	
}
