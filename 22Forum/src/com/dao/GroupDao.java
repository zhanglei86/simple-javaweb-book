package com.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.toolsbean.DB;
import com.valuebean.GroupBean;


public class GroupDao {
	public List getGroupList(){
		List grouplist=null;
		String sql="select * from tb_group order by group_id";
		try {
			grouplist = getList(sql,null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grouplist;			
	}
	public GroupBean getGroupSingle(int groupId) throws SQLException{
		GroupBean single=null;
		String sql="select * from tb_group where group_id=?";
		Object[] params={groupId};
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(GroupBean)list.get(0);
		return single;			
	}
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				GroupBean single=new GroupBean();
				single.setGroupId(rs.getInt(1));
				single.setGroupName(rs.getString(2));
				single.setGroupInfo(rs.getString(3));
				single.setMemberNum(new UserDao().getGroupMemberNum(single.getGroupId()));
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}
	public int update(Object[] params){
		String sql="update tb_group set group_info=? where group_id=?";
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=0;
		try {
			 i= mydb.getCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
}
