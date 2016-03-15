package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.CategoryBean;

public class CategoryDao {
	public boolean isexistbyname(String categoryName) throws SQLException{
		String sql="select id from tb_category where category_name=?";
		Object[] params={categoryName};
		boolean mark=isexist(sql,params);
		return mark;		
	}
	public boolean isexistbyid(int categoryId) throws SQLException{
		String sql="select id from tb_category where id=?";
		Object[] params={categoryId};
		boolean mark=isexist(sql,params);
		return mark;
	}
	private boolean isexist(String sql,Object[] params) throws SQLException{
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
	/**
	 * @功能：查询指定的类别名称是否存在，并同时返回类别ID值
	 * @返回：int值(类别ID)：值大于0表示存在；否则不存在
	 */
	public int validatename(String name) throws SQLException{
		int id=-1;
		String sql="select id from tb_category where category_name=?";
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
	public int insert(Object[] params){
		String sql="insert into tb_category values(?,?,?,?)";
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
	public CategoryBean getCategorySingle(int id) throws SQLException{
		CategoryBean single=null;
		Object[] params={id};
		String sql="select * from tb_category where id=?";
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(CategoryBean)list.get(0);
		return single;
	}
	/** @功能：获取所有类别	 */
	public List getCategoryList() throws SQLException{
		String sql="select * from tb_category order by category_order,category_foundTime desc";
		List categorylist=getList(sql,null);
		return categorylist;
	}	
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				CategoryBean single=new CategoryBean();
				single.setId(rs.getInt(1));
				single.setCategoryName(rs.getString(2));
				single.setCategoryInfo(rs.getString(3));
				single.setCategoryOrder(rs.getInt(4));
				single.setCategoryFoundTime(StringHandler.timeTostr(rs.getTimestamp(5)));
				single.setMasters(getMasters(single.getId()));
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}
	/** @功能：获取某类别的所有类主 */
	private List getMasters(int categoryId) throws SQLException{
		List masterlist=null;
		String sql="select member_name from tb_member where id in (select groupCategory_memberId from tb_groupCategory where groupCategory_categoryId="+categoryId+")";
		DB mydb=new DB();
		mydb.doPstm(sql, null);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			masterlist=new ArrayList();
			while(rs.next()){
				masterlist.add(rs.getString(1));
			}
			rs.close();
		}
		mydb.closed();
		return masterlist;		
	}
	/** @功能：获取所有类别的ID和名称 */
	public Map getCategoryMap() throws SQLException{
		Map category=null;
		String sql="select id,category_name from tb_category";
		
		DB mydb=new DB();
		mydb.doPstm(sql,null);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			category=new HashMap();
			while(rs.next()){
				category.put(rs.getInt(1),rs.getString(2));
			}
		}
		return category;
	}
	public int update(Object[] params){
		String sql="update tb_category set category_name=?,category_info=?,category_order=? where id=?";
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
	/** @功能：将类别的排列次序提前 */
	public void updateOrderToUp(int categoryId){
		String sql="update tb_category set category_order=category_order-1 where id=?";
		Object[] params={categoryId};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		mydb.closed();
	}
	/** @功能：将类别的排列次序置后 */
	public void updateOrderToDown(int categoryId){
		String sql="update tb_category set category_order=category_order+1 where id=?";
		Object[] params={categoryId};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		mydb.closed();
	}

	public int delete(int categoryId){
		String sql="delete from tb_category where id=?";
		Object[] params={categoryId};
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
	public List getAssignCategorysName(int[] assigncategoryid){
		Object[] params=null;
		String sql="select category_name from tb_category where id in (assign[])";
		
		if(assigncategoryid!=null&&assigncategoryid.length!=0){
			String ids="";
			params=new Object[assigncategoryid.length];
			for(int i=0;i<assigncategoryid.length;i++){
				params[i]=assigncategoryid[i];
				ids+="?,";
			}
			ids=ids.substring(0,ids.length()-1);
			sql=sql.replace("assign[]",ids);
			
			List categorys=null;
			DB mydb=new DB();
			mydb.doPstm(sql,params);
			try {
				ResultSet rs=mydb.getRs();
				if(rs!=null){
					categorys=new ArrayList();
					while(rs.next())
						categorys.add(rs.getString(1));
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			mydb.closed();
			return categorys;
		}
		else
			return null;
	}
}
