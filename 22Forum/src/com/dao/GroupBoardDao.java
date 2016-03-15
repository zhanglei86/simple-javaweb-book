package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.BoardBean;
import com.valuebean.TopicBean;

public class GroupBoardDao {
	
	/** @功能：添加新版主 */
	public int addnewmaster(int memberId,int categoryId){
		String sql="insert into tb_groupBoard values(?,?)";
		Object[] params={memberId,categoryId};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
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
	/** @功能：获取为用户分配的版块ID */
	public int[] getAssignBoard(int memberId) throws SQLException{
		int[] boards=null;
		String sql="select groupBoard_boardId from tb_groupBoard where groupBoard_memberId=?";
		Object[] params={memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			rs.last();
			boards=new int[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				boards[i]=rs.getInt(1);
				i++;
			}
			rs.close();
		}
		mydb.closed();
		return boards;
	}
	public int assignBoardToMember(int memberId,int boardId){
		String sql="insert into tb_groupBoard values(?,?)";
		Object[] params={memberId,boardId};
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
	public int deleteassign(int memberId,int categoryId){
		String sql="delete from tb_groupBoard where groupBoard_memberId=? and groupBoard_boardId=?";
		Object[] params={memberId,categoryId};
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
	public void deleteallassignformember(int memberId){
		String sql="delete from tb_groupBoard where groupBoard_memberId=?";
		Object[] params={memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
}
