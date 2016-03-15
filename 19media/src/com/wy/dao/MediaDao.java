package com.wy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wy.model.MediaInfo;
import com.wy.tool.JDBConnection;

public class MediaDao {

	private static JDBConnection connection = null;

	private String sql = null;

	public MediaDao() {
		connection = new JDBConnection();
	}

	
	public void media_addNumber(Integer id){
		sql="update tb_media set media_lookCount=media_lookCount+1 where id = "+id+" ";
		connection.executeUpdata(sql);
		
	}
	
	public void media_delete(Integer id){
		sql="delete from tb_media where id = "+id+" ";
		connection.executeUpdata(sql);
		
	}
	
	
	
	
	public boolean media_add(MediaInfo info) {
		sql = "insert into tb_media values ('" + info.getMediaTitle() + "','"
				+ info.getMedia_type() + "','" + info.getMediaSrc() + "','"
				+ info.getMediaPic() + "','" + info.getMediaInfo()
				+ "',getDate(),0)";
		return connection.executeUpdata(sql);
	}

	public List media_query(String type) {
		List list = new ArrayList();
		MediaInfo mediaInfo = null;
		if (null == type)
			sql = "select * from tb_media order by media_uptime desc";
		else
			sql = "select * from tb_media where media_type = '" + type
					+ "' order by media_uptime desc";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				mediaInfo = new MediaInfo();
				mediaInfo.setId(rs.getInt(1));
				mediaInfo.setMediaTitle(rs.getString(2));
				mediaInfo.setMedia_type(rs.getString(3));
				mediaInfo.setMediaSrc(rs.getString(4));
				mediaInfo.setMediaPic(rs.getString(5));
				mediaInfo.setMediaInfo(rs.getString(6));
				mediaInfo.setMediaUptime(rs.getString(7));
				mediaInfo.setLookCount(rs.getString(8));
				list.add(mediaInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	
	
	public List media_querySearch(String key) {
		List list = new ArrayList();
		MediaInfo mediaInfo = null;
	    sql="select * from tb_media  where (media_title like '%"+key+"%' or media_info like '%"+key+"%')";	
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				mediaInfo = new MediaInfo();
				mediaInfo.setId(rs.getInt(1));
				mediaInfo.setMediaTitle(rs.getString(2));
				mediaInfo.setMedia_type(rs.getString(3));
				mediaInfo.setMediaSrc(rs.getString(4));
				mediaInfo.setMediaPic(rs.getString(5));
				mediaInfo.setMediaInfo(rs.getString(6));
				mediaInfo.setMediaUptime(rs.getString(7));
				mediaInfo.setLookCount(rs.getString(8));
				list.add(mediaInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	
	

	public List media_queryAuto(String sql) {
		List list = new ArrayList();
		MediaInfo mediaInfo = null;
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				mediaInfo = new MediaInfo();
				mediaInfo.setId(rs.getInt(1));
				mediaInfo.setMediaTitle(rs.getString(2));
				mediaInfo.setMedia_type(rs.getString(3));
				mediaInfo.setMediaSrc(rs.getString(4));
				mediaInfo.setMediaPic(rs.getString(5));
				mediaInfo.setMediaInfo(rs.getString(6));
				mediaInfo.setMediaUptime(rs.getString(7));
				mediaInfo.setLookCount(rs.getString(8));
				list.add(mediaInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public MediaInfo media_query(Integer id) {
		MediaInfo mediaInfo = new MediaInfo();
		sql = "select * from tb_media where id = " + id + "";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				mediaInfo.setId(rs.getInt(1));
				mediaInfo.setMediaTitle(rs.getString(2));
				mediaInfo.setMedia_type(rs.getString(3));
				mediaInfo.setMediaSrc(rs.getString(4));
				mediaInfo.setMediaPic(rs.getString(5));
				mediaInfo.setMediaInfo(rs.getString(6));
				mediaInfo.setMediaUptime(rs.getString(7));
				mediaInfo.setLookCount(rs.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return mediaInfo;

	}

	public Integer media_queryNumber(String type) {
		Integer number = 0;
		if (null == type)
			sql = "select count(*) as number from tb_media";
		else
			sql = "select count(*) as number from tb_media where media_type='"
					+ type + "'";
		ResultSet rs = connection.executeQuery(sql);
		try {
			while (rs.next()) {
				number = rs.getInt("number");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return number;
	}

}
