package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.MessageBean;

public class MessageDao {
	private DB mydb=null;
	public MessageDao(){
		mydb=new DB();
	}
	public int getUnRead(String membername) throws SQLException{
		int num=0;
		String sql="select count(id) from tb_message where (message_readmark='1') and (message_getter=?)";
		Object[] params={membername};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next())
			num=rs.getInt(1);
		return num;
	}
	public int insert(Object[] params){
		int i=0;
		String sql="insert into tb_message values(?,?,?,?,?,?,?,?,?)";
		mydb.doPstm(sql, params);
		try {
			i = mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	public MessageBean getDeleteSingle(int messageId) throws SQLException{
		MessageBean single=null;
		String sql="select delete_sender,delete_getter from tb_message where id=?";
		Object[] params={messageId};
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			single=new MessageBean();
			single.setDeleteSender(rs.getString(1));
			single.setDeleteGetter(rs.getString(2));
			rs.close();
		}		
		return single;
	}
	public MessageBean getMessageSingle(int messageId) throws SQLException{
		MessageBean single=null;
		String sql="select * from tb_message where id=?";
		Object[] params={messageId};
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(MessageBean)list.get(0);
		return single;
	}
	
	public List getInceptMessages(String name,int messagemax) throws SQLException{
		String sql="select top "+messagemax+" * from tb_message where (message_getter=?) and (delete_getter='0') order by message_readmark desc,message_sendTime desc";
		Object[] params={name};
		List list=getList(sql,params);
		return list;		
	}
	public List getSendMessages(String name) throws SQLException{
		String sql="select * from tb_message where (message_sender=?) and (delete_sender='0') order by message_sendTime desc";
		Object[] params={name};
		List list=getList(sql,params);
		return list;		
	}
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				MessageBean single=new MessageBean();
				single.setId(rs.getInt(1));
				single.setMessageTitle(rs.getString(2));
				single.setMessageContent(rs.getString(3));
				single.setMessageSendTime(StringHandler.timeTostr(rs.getTimestamp(4)));
				single.setMessageEmotion(rs.getString(5));
				single.setMessageReadmark(rs.getString(6));
				single.setMessageSender(rs.getString(7));
				single.setMessageGetter(rs.getString(8));
				single.setDeleteSender(rs.getString(9));
				single.setDeleteGetter(rs.getString(10));
				list.add(single);
			}
			rs.close();
		}
		return list;
	}
	public int getInceptAllNum(String name){
		String sql="select count(id) as num from tb_message where (message_getter=?) and (delete_getter='0')";
		Object[] params={name};
		int num=getNum(sql,params);
		return num;		
	}
	public int getInceptUnReadNum(String name){
		String sql="select count(id) as num from tb_message where (message_getter=?) and (message_readmark='1') and (delete_getter='0')";
		Object[] params={name};
		int num=getNum(sql,params);
		return num;		
	}
	public int getSendAllNum(String name){
		String sql="select count(id) as num from tb_message where (message_sender=?) and (delete_sender='0')";
		Object[] params={name};
		int num=getNum(sql,params);
		return num;		
	}
	private int getNum(String sql,Object[] params){
		int num=0;
		mydb.doPstm(sql, params);
		try {
			ResultSet rs=mydb.getRs();
			if(rs!=null&&rs.next())
				num=rs.getInt(1);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return num;
	}
	public int deleteMessage(int messageId){
		int i=0;
		String sql="delete from tb_message where id=?";
		Object[] params={messageId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	public int updateDeleteForInceptMessage(int messageId){
		int i=0;
		String sql="update tb_message set delete_getter='1' where id=?";
		Object[] params={messageId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	public int updateDeleteForSendMessage(int messageId){
		int i=0;
		String sql="update tb_message set delete_sender='1' where id=?";
		Object[] params={messageId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	public void updateReadMarkForInceptMessage(int messageId){
		String sql="update tb_message set message_readmark='0' where id=?";
		Object[] params={messageId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
	}
	public void closed(){
		mydb.closed();
	}
}
