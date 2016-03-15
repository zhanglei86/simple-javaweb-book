package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.PlacardBean;

public class PlacardDao {
	public int getId(String author,String time){
		int id=0;
		String sql="select id from tb_placard where (placard_postTime=?) and (placard_author=?)";
		Object[] params={author,time};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs=mydb.getRs();
			if(rs!=null&&rs.next()){
				id=rs.getInt(1);
				rs.close();
			}
		} catch (SQLException e) {
			id=-1;
			e.printStackTrace();
		}
		mydb.closed();
		return id;		
	}
	public PlacardBean getPlacardSingle(int placardId) throws SQLException{
		PlacardBean single=null;
		String sql="select * from tb_placard where id=?";
		Object[] params={placardId};
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0){
			single=(PlacardBean)list.get(0);
			single.setPlacardAuthor(new UserDao().getUserSingle(single.getPlacardAuthorName()));
		}
		return single;
	}
	/**
	 * @功能：获取符合条件的公告：论坛公告、当前类公告、当前版块公告
	 * @参数：categoryId为当前类的ID值
	 * @参数：boardId为当前版块的ID值
	 */
	public List getPlacardList(int categoryId,int boardId) throws SQLException{
		Object[] params={categoryId,boardId};
		String sql="select * from tb_placard where (placard_type='2') or (placard_type='1' and category_id=?) or (placard_type='0' and board_id=?) order by placard_type desc,placard_posttime desc";
		List placardlist=getList(sql,params);
		return placardlist;
	}
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				PlacardBean single=new PlacardBean();
				single.setId(rs.getInt(1));
				single.setCategoryId(rs.getInt(2));
				single.setBoardId(rs.getInt(3));
				single.setPlacardTitle(rs.getString(4));
				single.setPlacardContent(rs.getString(5));
				single.setPlacardPostTime(StringHandler.timeTostr(rs.getTimestamp(6)));
				single.setPlacardType(rs.getString(7));
				single.setPlacardAuthorName(rs.getString(8));
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}
	public int add(Object[] params){
		int i=0;
		String sql="insert into tb_placard values(?,?,?,?,?,?,?)";
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
	public int deletePlacard(int placard){
		int i=0;
		String sql="delete from tb_placard where id=?";
		Object[] params={placard};
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
}
