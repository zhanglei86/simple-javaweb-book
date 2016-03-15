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

public class GroupCategoryDao {
	
	/** @功能：添加新类主 */
	public int addnewmaster(int memberId,int categoryId){
		String sql="insert into tb_groupCategory values(?,?)";
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
	/** @功能：获取为用户分配的类别ID */
	public int[] getAssignCategory(int memberId) throws SQLException{
		int[] categorys=null;
		String sql="select groupCategory_categoryId from tb_groupCategory where groupCategory_memberId=?";
		Object[] params={memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			rs.last();
			categorys=new int[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){
				categorys[i]=rs.getInt(1);
				i++;
			}
			rs.close();
		}
		mydb.closed();
		return categorys;
	}
	public int assignCategoryToMember(int memberId,int categoryId){
		String sql="insert into tb_groupCategory values(?,?)";
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
	public int deleteassign(int memberId,int categoryId){
		String sql="delete from tb_groupCategory where groupCategory_memberId=? and groupCategory_categoryId=?";
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
		String sql="delete from tb_groupCategory where groupCategory_memberId=?";
		Object[] params={memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
}
