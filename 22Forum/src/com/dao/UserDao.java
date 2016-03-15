package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.UserBean;

public class UserDao extends SuperDao{
	public UserBean search(Object[] params) throws SQLException{
		UserBean single=new UserBean();
		String sql="select * from tb_member where id=? or member_name=?";
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(UserBean)list.get(0);
		return single;
	}
	public boolean isexistbyid(int memberId) throws SQLException{
		String sql="select member_name from tb_member where id=?";
		Object[] params={memberId};
		boolean mark=isexistby(sql, params);
		return mark;
	}
	public boolean isexistbyname(String memberName) throws SQLException{
		String sql="select id from tb_member where member_name=?";
		Object[] params={memberName};
		boolean mark=isexistby(sql, params);
		return mark;
	}
	private boolean isexistby(String sql,Object[] params) throws SQLException{
		boolean mark=false;
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs= mydb.getRs();
		if(rs!=null&&rs.next()){
			mark=true;
			rs.close();
		}		
		mydb.closed();
		return mark;
	}
	public int insert(Object[] params){
		String sql="insert into tb_member values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int i=getUpdateCount(sql,params);
		return i;
	}
	public int updateUserStatus(Object[] params){
		String sql="update tb_member set member_status=? where id=?";
		int i=getUpdateCount(sql,params);
		return i;
	}
	public int updateMemberPswd(Object[] params){
		String sql="update tb_member set member_pswd=? where member_name=?";
		int i=getUpdateCount(sql,params);
		return i;		
	}
	public int updateMemberSign(Object[] params){
		String sql="update tb_member set member_sign=? where id=?";
		int i=getUpdateCount(sql,params);
		return i;			
	}
	public int updateMyIcon(Object[] params){
		String sql="update tb_member set member_icon=? where id=?";
		int i=getUpdateCount(sql,params);
		return i;			
	}
	public int updateMemberInfo(Object[] params){
		String sql="update tb_member set member_sex=?,member_age=?,member_oicq=? where member_name=?";
		int i=getUpdateCount(sql,params);
		return i;			
	}
	private int getUpdateCount(String sql,Object[] params){
		int i=0;
		DB mydb=new DB();
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
	public void updateLoginTime(String time,int memberId){
		String sql="update tb_member set member_logonTime=? where id=?";
		Object[] params={time,memberId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	/**
	 * @功能：查询指定的用户名是否存在，并同时返回用户ID值
	 * @返回：int值(会员ID)：值大于0表示存在；否则不存在
	 */
	public int validatename(String name) throws SQLException{
		int id=-1;
		String sql="select id from tb_member where member_name=?";
		Object[] params={name};
		DB mydb=new DB();
		mydb.doPstm(sql, params);

		ResultSet rs = mydb.getRs();
		if(rs!=null&&rs.next()){
			id=rs.getInt(1);
			rs.close();
		}

		mydb.closed();
		return id;			
	}
	public boolean validatepswd(String name,String pswd){
		boolean mark=false;
		String sql="select id from tb_member where member_name=? and member_pswd=?";
		Object[] params={name,pswd};
		DB mydb=new DB();
		mydb.doPstm(sql, params);

		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				mark=true;
				rs.close();
			}
		} catch (SQLException e) {
			mark=false;
			e.printStackTrace();
		}
		mydb.closed();
		return mark;			
	}
	public UserBean login(String name,String pswd) throws SQLException{
		Object[] params={name,pswd};
		String sql="select * from tb_member where (member_name=?) and (member_pswd=?)";
		List list=getList(sql,params);
		
		if(list!=null&&list.size()!=0)
			return (UserBean)list.get(0);
		else
			return null;
	}
	/** @功能：获取某用户组下所有会员	 */
	public List getGroupUserList(int groupId,String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		String sqlall="select * from tb_member where group_id=?";
		Object[] params={groupId};
		
		setDaoPerR(5);
		setDaoPerP(10);
		setDaoPage(sqlall,params,strcurrentP, strcurrentG, goWhich);
		
		int currentP=getDaoPage().getCurrentP();
		int top1=getDaoPage().getPerR();
		int top2=(currentP-1)*top1;
		
		String sqlsub="";
		if(currentP==1)
			sqlsub="select top "+top1+" * from tb_member where group_id=? order by member_regTime desc";
		else
			sqlsub="select top "+top1+" * from tb_member i where group_id=? and (member_regTime < (select min(member_regTime) from (select top "+top2+" * from tb_member where group_id=i.group_id order by member_regTime desc) as minv)) order by member_regTime desc";
		
		List list=getList(sqlsub,params);
		return list;
	}
	/** @功能：获取所有会员	 */
	public List getUserList(String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		String sqlall="select * from tb_member order by member_regTime desc";
		
		setDaoPerR(5);
		setDaoPerP(10);
		setDaoPage(sqlall,null,strcurrentP, strcurrentG, goWhich);
		
		int currentP=getDaoPage().getCurrentP();
		int top1=getDaoPage().getPerR();
		int top2=(currentP-1)*top1;
		
		String sqlsub="";
		if(currentP==1)
			sqlsub="select top "+top1+" * from tb_member  order by member_regTime desc";
		else
			sqlsub="select top "+top1+" * from tb_member i where (member_regTime < (select min(member_regTime) from (select top "+top2+" * from tb_member order by member_regTime desc) as minv)) order by member_regTime desc";
		
		List list=getList(sqlsub,null);
		return list;
	}
	/** @功能：通过会员ID获取当前某个会员的详细信息	 */
	public UserBean getUserSingle(int memberId) throws SQLException{
		Object[] params={memberId};
		String sql="select * from tb_member where (id=?)";
		List list=getList(sql,params);
		
		if(list!=null&&list.size()!=0)
			return (UserBean)list.get(0);
		else
			return null;
	}
	/** @功能：通过会员名称获取当前某个会员的详细信息 */
	public UserBean getUserSingle(String memberName) throws SQLException{
		Object[] params={memberName};
		String sql="select * from tb_member where (member_name=?)";
		List list=getList(sql,params);
		
		if(list!=null&&list.size()!=0)
			return (UserBean)list.get(0);
		else
			return null;
	}
	/** @功能：通过会员名称获取只包含用户组id信息的会员*/
	public UserBean getUserSingleForAddGroupMember(String memberName) throws SQLException{
		UserBean single=null;
		Object[] params={memberName};
		String sql="select id,group_id from tb_member where (member_name=?)";
		
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			single=new UserBean();			
			single.setId(rs.getInt(1));
			single.setGroupId(rs.getInt(2));
			rs.close();
		}
		mydb.closed();
		return single;
	}
	/** @throws SQLException 
	 * @功能：获取某用户组下所有会员的名称 */
	public List getGroupUserNames(int groupId) throws SQLException{
		List membernames=null;
		Object[] params={groupId};
		String sql="select member_name from tb_member where (group_id=?)";
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			membernames=new ArrayList();
			while(rs.next())
				membernames.add(rs.getString(1));
			rs.close();
		}
		mydb.closed();
		return membernames;
	}
	
	public UserBean getUserForAssignById(int memberid) throws SQLException{
		UserBean single=null;
		
		Object[] params={memberid};
		String sql="select group_id,member_name from tb_member where (id=?)";
	
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&rs.next()){			
			single=new UserBean();
			single.setGroupId(rs.getInt(1));
			single.setMemberName(rs.getString(2));
			if(single.getGroupId()==2)																//如果用户属于类管理组成员
				single.setAssignCategoryId(new GroupCategoryDao().getAssignCategory(memberid));								//获取为用户分配的类
			else if(single.getGroupId()==1)															//如果用户属于版块管理组成员
				single.setAssignBoardId(new GroupBoardDao().getAssignBoard(memberid));									//获取为用户分配的版块
		}
		rs.close();
		mydb.closed();
		return single;
	}
	public UserBean getUserForAssignByName(String memberName) throws SQLException{
		UserBean single=null;
		
		Object[] params={memberName};
		String sql="select id,group_id from tb_member where (member_name=?)";
	
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&rs.next()){			
			single=new UserBean();
			single.setId(rs.getInt(1));
			single.setGroupId(rs.getInt(2));
			if(single.getGroupId()==2)																//如果用户属于类管理组成员
				single.setAssignCategoryId(new GroupCategoryDao().getAssignCategory(single.getId()));								//获取为用户分配的类
			else if(single.getGroupId()==1)															//如果用户属于版块管理组成员
				single.setAssignBoardId(new GroupBoardDao().getAssignBoard(single.getId()));									//获取为用户分配的版块
		}
		rs.close();
		mydb.closed();
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
				UserBean single=new UserBean();
				single.setId(rs.getInt(1));
				single.setGroupId(rs.getInt(2));
				single.setMemberName(rs.getString(3));
				single.setMemberPswd(rs.getString(4));
				single.setMemberSex(rs.getString(5));
				single.setMemberAge(rs.getInt(6));
				single.setMemberOICQ(rs.getString(7));
				single.setMemberIcon(rs.getString(8));
				single.setMemberSign(rs.getString(9));
				single.setMemberStatus(rs.getString(10));
				single.setMemberRegTime(StringHandler.timeTostr(rs.getTimestamp(11)));
				single.setMemberLogonTime(StringHandler.timeTostr(rs.getTimestamp(12)));
				single.setMemberPostNum(rs.getInt(13));
				single.setMemberExperience(rs.getInt(14));
				single.setMemberCharm(rs.getInt(15));
				single.setNewmessages(new MessageDao().getUnRead(single.getMemberName()));
				if(single.getGroupId()==2)																//如果用户属于类管理组成员
					single.setAssignCategoryId(new GroupCategoryDao().getAssignCategory(single.getId()));								//获取为用户分配的类
				else if(single.getGroupId()==1)															//如果用户属于版块管理组成员
					single.setAssignBoardId(new GroupBoardDao().getAssignBoard(single.getId()));									//获取为用户分配的版块
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}
	public int getGroupMemberNum(int groupId){
		int num=0;
		String sql="select count(id) from tb_member where group_id=?";
		Object[] params={groupId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				num=rs.getInt(1);
				rs.close();
			}
		} catch (SQLException e) {
			num=0;
			e.printStackTrace();
		}
		mydb.closed();
		return num;
	}
	
	public void updatePostNum(String membername){
		String sql="update tb_member set member_postNum=member_postNum+1 where member_name=?";
		Object[] params={membername};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	
	public int updateUserGroupId(int groupId,List members){		
		Object[] params=null;
		String sql="update tb_member set group_id=? where member_name in (member[])";
		
		if(members!=null&&members.size()!=0){
			params=new Object[members.size()+1];
			params[0]=groupId;
			String names="";
			for(int i=0;i<members.size();i++){				
				params[i+1]=members.get(i);
				names+="?,";
			}
			names=names.substring(0,names.length()-1);
			sql=sql.replace("member[]",names);
			
			DB mydb=new DB();
			mydb.doPstm(sql, params);
			int k=0;
			try {
				k = mydb.getCount();
			} catch (SQLException e) {
				k=-1;
				e.printStackTrace();
			}
			mydb.closed();
			return k;
		}
		else
			return -1;
	}
	
	public int delete(int memberId){
		String sql="delete from tb_member where id=?";
		Object[] params={memberId};
		DB mydb=new DB();
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
}