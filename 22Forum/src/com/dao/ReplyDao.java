package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.ReplyBean;

public class ReplyDao extends SuperDao{
	public Map getQuoteReply(int replyId) throws SQLException{
		Map quotereply=null;
		String sql="select reply_content,reply_author from tb_reply where id=?";
		Object[] params={replyId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			quotereply=new HashMap();
			quotereply.put("quotecontent",rs.getString(1));
			quotereply.put("quoteauthor",rs.getString(2));
			rs.close();
		}
		mydb.closed();
		return quotereply;
	}
	public ReplyBean getReplySingle(int id) throws SQLException{
		ReplyBean single=null;
		String sql="select * from tb_reply where id=?";
		Object[] params={id};
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(ReplyBean)list.get(0);
		return single;
	}
	/** @功能：获取主题的最新10条回复 */
	public List getNewReplyList(int id) throws SQLException{
		Object[] params={id};
		String sql="select top 10 * from tb_reply where (topic_id=?) order by reply_replyTime desc";
		List newrelpylist=getList(sql,params);
		return newrelpylist;		
	}
	/** @功能：获取主题的所有回复 */
	public List getMoreReplyList(int id,String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		Object[] params={id};
		String sqlall="select * from tb_reply where (topic_id=?)";
		
		setDaoPerR(8);
		setDaoPerP(10);
		setDaoPage(sqlall,params,strcurrentP, strcurrentG, goWhich);
		
		int currentP=getDaoPage().getCurrentP();
		int top1=getDaoPage().getPerR();
		int top2=(currentP-1)*top1;
		
		String sqlsub="";
		if(currentP==1)
			sqlsub="select top "+top1+" * from tb_reply where (topic_id=?) order by reply_replyTime";
		else
			sqlsub="select top "+top1+" * from tb_reply i where (topic_id=?) and (reply_replyTime > (select max(reply_replyTime) from (select top "+top2+" * from tb_reply where (topic_id=i.topic_id) order by reply_replyTime) as maxv)) order by reply_replyTime";
		
		List topiclist=getList(sqlsub,params);
		return topiclist;		
	}
	
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				ReplyBean single=new ReplyBean();
				single.setId(rs.getInt(1));
				single.setTopicId(rs.getInt(2));
				single.setReplyTitle(rs.getString(3));				
				single.setReplyContent(rs.getString(4));
				single.setReplyAuthorName(rs.getString(5));
				single.setReplyEmotion(rs.getString(6));
				single.setReplyAttachmentSign(rs.getString(7));
				single.setReplyReplyTime(StringHandler.timeTostr(rs.getTimestamp(8)));
				single.setReplyAuthor(new UserDao().getUserSingle(single.getReplyAuthorName()));
				if(single.getReplyAttachmentSign().equals("1"))
					single.setReplyAttachment(new AttachmentDao().getAttachmentsByReply(single.getId()));
				
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}	
	
	/** @功能：获取当前主题的最后回复帖 */
	public ReplyBean getLastReply(int id) throws SQLException{
		ReplyBean last=null;
		String sql="select * from tb_reply i where (topic_id=?) and (reply_replyTime=(select max(reply_replyTime) from tb_reply where (topic_id=i.topic_id)))";
		Object[] params={id};
		
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			last=new ReplyBean();
			last.setId(rs.getInt(1));
			last.setReplyAuthorName(rs.getString(5));
			last.setReplyReplyTime(StringHandler.timeTostr(rs.getTimestamp(8)));
			rs.close();
		}
		mydb.closed();
		return last;
	}
	/** @功能：添加回复 */
	public int reply(Object[] params){
		int i=0;
		String sql="insert into tb_reply values(?,?,?,?,?,?,?)";
		DB mydb =new DB();
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
	public int updateReply(Object[] params){
		String sql="update tb_reply set reply_title=?,reply_content=?,reply_emotion=? where id=?";
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=0;
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		mydb.closed();
		return i;
	}
	public void updateReplyAttachment(String sign,int topicId){
		String sql="update tb_reply set reply_attachment=? where id=?";
		Object[] params={sign,topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			mydb.getCount();
		} catch (SQLException e) {
			System.out.println("更新回复帖的reply_attachment字段失败！");
			e.printStackTrace();
		}
		mydb.closed();
	}
	/** @功能：删除指定ID的回复帖 */
	public void deleteReply(int replyId){
		String sql="delete from tb_reply where id=?";
		Object[] params={replyId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
	}
	/** @功能：删除指定帖子的所有回复 */
	public void deleteReplyByTopicId(int topicId){
		String sql="delete from tb_reply where topic_id=?";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
}