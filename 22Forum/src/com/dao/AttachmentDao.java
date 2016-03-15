package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.AttachmentBean;

public class AttachmentDao {
	private DB mydb=null;
	public AttachmentDao(){
		mydb=new DB();
	}
	public AttachmentBean getAttachmentSingle(int attachmentid) throws SQLException{
		AttachmentBean single=null;
		String sql="select * from tb_attachment where id=?";
		Object[] params={attachmentid};
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(AttachmentBean)list.get(0);
		return single;
	}
	public int addAttachInfo(Object[] params){
		int i=-1;
		String sql="insert into tb_attachment values(?,?,?,?,?,?,?)";
		mydb.doPstm(sql, params);
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;		
	}
	public boolean isExistByTopic(int topicId){
		boolean isexist=false;
		String sql="select * from tb_attachment where (topic_id=?)";
		Object[] params={topicId};
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				isexist=true;
				rs.close();
			}
			else
				isexist=false;
		} catch (SQLException e) {
			isexist=false; 
			e.printStackTrace();
		}
		return isexist;			
	}
	public boolean isExistByReply(int replyId){
		boolean isexist=false;
		String sql="select * from tb_attachment where (reply_id=?)";
		Object[] params={replyId};
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				isexist=true;
				rs.close();
			}
			else
				isexist=false;
		} catch (SQLException e) {
			isexist=false; 
			e.printStackTrace();
		}
		return isexist;			
	}
	/** @功能：获取指定主题的所有附件 */
	public List getAttachmentsByTopic(int topicId) throws SQLException{
		String sql="select * from tb_attachment where (topic_id=?) and (reply_id=-1) order by attachment_uptime desc,id";
		Object[] params={topicId};
		List attachments=getList(sql,params);		
		return attachments;
	}
	public List getAttachmentsByReply(int replyId) throws SQLException{
		String sql="select * from tb_attachment where (reply_id=?) order by attachment_uptime desc,id";
		Object[] params={replyId};
		List attachments=getList(sql,params);		
		return attachments;
	}
	/** 
	 * @功能：获取指定主题的所有附件的savename
	 * @返回：List集合，用来存储附件保存到磁盘中的文件名 
	 */
	public List getAttachmentSaveNameByTopic(int topicId) throws SQLException{
		List savenames=null;
		String sql="select attachment_savename from tb_attachment where (topic_id=?) and (reply_id=-1)";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			savenames=new ArrayList();
			while(rs.next()){
				savenames.add(rs.getString(1));
			}
			rs.close();
		}
		mydb.closed();
		return savenames; 
	}
	/** 
	 * @功能：获取指定回复帖的所有附件的savename
	 * @返回：List集合，用来存储附件保存到磁盘中的文件名  
	 */
	public List getAttachmentSaveNameByReply(int replyId){
		List savenames=null;
		String sql="select attachment_savename from tb_attachment where reply_id=?";
		Object[] params={replyId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs= mydb.getRs();
			if(rs!=null){
				savenames=new ArrayList();
				while(rs.next()){
					savenames.add(rs.getString(1));
				}
				rs.close();
			}
			mydb.closed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return savenames; 
	}
	/** @功能：获取指定主题下所有回复帖的附件的savename */
	public List getAttachmentSaveNameByTopicAndReply(int topicId) throws SQLException{
		List savenames=null;
		String sql="select attachment_savename from tb_attachment where (topic_id=?) and (reply_id!=-1)";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			savenames=new ArrayList();
			while(rs.next()){
				savenames.add(rs.getString(1));
			}
			rs.close();
		}
		mydb.closed();
		return savenames; 
	}
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				AttachmentBean single=new AttachmentBean();
				single.setId(rs.getInt(1));
				single.setTopicId(rs.getInt(2));
				single.setReplyId(rs.getInt(3));
				single.setAttachmentSaveName(rs.getString(4));
				single.setAttachmentFileName(rs.getString(5));
				single.setAttachmentFileType(rs.getString(6));
				single.setAttachmentFileSize(rs.getString(7));
				single.setAttachmentUpTime(StringHandler.timeTostr(rs.getTimestamp(8)));
				list.add(single);
			}
			rs.close();
		}
		return list;
	}
	/** @功能：删除附件 */
	public int deleteAttachment(int attachmentid){
		String sql="delete from tb_attachment where id=?";
		Object[] params={attachmentid};
		mydb.doPstm(sql, params);
		int i=0;
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;		
	}
	/** @功能：删除指定主题的所有附件 */
	public void deleteAttachmentByTopicId(int topicId){
		String sql="delete from tb_attachment where (topic_id=?) and (reply_id=-1)";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	/** @功能：删除指定回复帖的所有附件 */
	public void deleteAttachmentByReplyId(int replyId){
		String sql="delete from tb_attachment where (reply_id=?)";
		Object[] params={replyId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	/** @功能：删除某主题下所有回复帖具有的附件 */
	public void deleteAttachmentByTopicAndReply(int topicId){
		String sql="delete from tb_attachment where (topic_id=?) and (reply_id!=-1)";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	
	public void closed(){
		mydb.closed();
	}
}
