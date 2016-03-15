package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.CollectBean;
import com.valuebean.ReplyBean;
import com.valuebean.TopicBean;

public class CollectDao {
	public boolean isExist(int memberid,int topicid){
		boolean isexist=false;
		String sql="select id from tb_collect where (member_id=?) and (topic_id=?)";
		Object[] params={memberid,topicid};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs;
		try {
			rs = mydb.getRs();
			if(rs!=null&&rs.next())
				isexist=true;			
			else
				isexist=false;
			rs.close();
		} catch (SQLException e) {
			isexist=false;
			e.printStackTrace();
		}
		mydb.closed();
		return isexist;
	}
	public int getCollectNum(int memberid) throws SQLException{
		int num=0;
		String sql="select count(id) from tb_collect where member_id=?";
		Object[] params={memberid};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next())
			num=rs.getInt(1);
		return num;
	}
	public List getCollects(int memberid) throws SQLException{
		List collects=null;
		String sql="select c.id,c.category_name,b.id,b.board_name,t.*,cl.* from tb_category c,tb_board b,tb_topic t,tb_collect cl where (c.id=b.category_id) and (b.id=t.board_id) and(t.id=cl.topic_id) and(cl.member_id=?) order by cl.collect_time desc";
		Object[] params={memberid};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			collects=new ArrayList();
			while(rs.next()){				
				Map category=new HashMap();
				category.put("id",rs.getInt(1));
				category.put("name",rs.getString(2));
				
				Map board=new HashMap();
				board.put("id",rs.getInt(3));
				board.put("name",rs.getString(4));
				
				TopicBean topic=new TopicBean();
				topic.setId(rs.getInt(5));
				topic.setTopicTitle(rs.getString(7));
				topic.setTopicAuthorName(rs.getString(9));
				topic.setTopicEmotion(rs.getString(10));
				topic.setTopicHits(rs.getInt(11));
				topic.setTopicType(rs.getString(12));
				topic.setTopicStatus(rs.getString(13));
				topic.setTopicReplyNum(rs.getInt(14));
				topic.setTopicPostTime(StringHandler.timeTostr(rs.getTimestamp(16)));
				topic.setLastReply(new ReplyDao().getLastReply(topic.getId()));

				CollectBean collect=new CollectBean();
				collect.setCategory(category);
				collect.setBoard(board);
				collect.setCollectTopic(topic);
				collect.setId(rs.getInt(18));
				collect.setCollectTime(StringHandler.timeTostr(rs.getTimestamp(21)));
				
				collects.add(collect);
			}
		}
		return collects;
	}
	public int deleteOne(int topicId,int memberId){
		String sql="delete from tb_collect where (topic_id=?) and (member_id=?)";
		Object[] params={topicId,memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=0;
		try {
			i = mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	public int deleteAll(int memberId){
		String sql="delete from tb_collect where (member_id=?)";
		Object[] params={memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=0;
		try {
			i = mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
}
