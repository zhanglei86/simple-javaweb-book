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

public class BoardDao {
	public boolean isexistbyname(String boardName) throws SQLException{
		String sql="select id from tb_board where board_name=?";
		Object[] params={boardName};
		boolean mark=isexist(sql,params);
		return mark;
	}
	public boolean isexistbyid(int boardId) throws SQLException{
		String sql="select id from tb_board where id=?";
		Object[] params={boardId};
		boolean mark=isexist(sql,params);
		return mark;
	}
	public boolean isexistincategory(int categoryId) throws SQLException{
		String sql="select top 1 id from tb_board where category_id=?";
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
	 * @���ܣ���ѯָ���İ�������Ƿ���ڣ���ͬʱ���ذ��IDֵ
	 * @���أ�intֵ(���ID)��ֵ����0��ʾ���ڣ����򲻴���
	 */
	public int validatename(String name) throws SQLException{
		int id=-1;
		String sql="select id from tb_board where board_name=?";
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
		String sql="insert into tb_board values(?,?,?,?,?,?,?,?)";
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
	public int getBelongToCategoryId(int boardId){
		int categoryId=-1;
		String sql="select category_id from tb_board where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next())
				categoryId=rs.getInt(1);
		} catch (SQLException e) {
			categoryId=-1;
			e.printStackTrace();
		}
		return categoryId;
	}
	public String getBoardStatus(int boardId){
		String status="0";
		String sql="select board_status from tb_board where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next())
				status=rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	/** @���ܣ���ȡĳ��������а��ID������ */
	public Map getBoardMap(int categoryId) throws SQLException{
		Map boards=null;
		String sql="select id,board_name from tb_board where category_id=? order by board_order,board_name";
		Object[] params={categoryId};
			
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			boards=new HashMap();
			while(rs.next()){
				boards.put(rs.getInt(1),rs.getString(2));
			}
		}
		return boards;
	}
	
	/** @���ܣ���ȡĳ����� */
	public BoardBean getBoardSingle(int boardId) throws SQLException{
		BoardBean single=null;
		Object[] params={boardId};
		String sql="select * from tb_board where (id=?)";
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0)
			single=(BoardBean)list.get(0);
		return single;
	}
	/** @���ܣ���ȡĳ����µ����а�� */
	public List getBoardList(int categoryId) throws SQLException{
		Object[] params={categoryId};
		String sql="select * from tb_board where (category_id=?) and (board_status!='0') order by board_order,board_foundTime desc";
		List boardlist=getList(sql,params);		
		return boardlist;
	}
	/** @���ܣ���̨��ȡĳ����µ����а�� */
	public List getAdminBoardList(int categoryId) throws SQLException{
		Object[] params={categoryId};
		String sql="select * from tb_board where (category_id=?) order by board_order,board_foundTime desc";
		List boardlist=getList(sql,params);		
		return boardlist;
	}
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				BoardBean single=new BoardBean();
				single.setId(rs.getInt(1));
				single.setCategoryId(rs.getInt(2));
				single.setBoardName(rs.getString(3));
				single.setBoardInfo(rs.getString(4));
				single.setBoardStatus(rs.getString(5));
				single.setBoardOrder(rs.getInt(6));
				single.setBoardAllTopicNum(rs.getInt(7));
				single.setBoardBestTopicNum(rs.getInt(8));
				single.setBoardFoundTime(StringHandler.timeTostr(rs.getTimestamp(9)));
				single.setLastTopic(getLastTopic(single.getId()));
				single.setMasters(getMasters(single.getId()));
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;		
	}
	/** @���ܣ���ȡ��ǰ�������·������� */
	private TopicBean getLastTopic(int id) throws SQLException{
		TopicBean last=new TopicDao().getLastTopic(id);
		return last;
	}
	/** @���ܣ���ȡĳ�������а��� */
	private List getMasters(int boardId) throws SQLException{
		List masterlist=null;
		String sql="select member_name from tb_member where id in (select groupBoard_memberId from tb_groupBoard where groupBoard_boardId="+boardId+")";
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
	/** @���ܣ��ۼӵ�ǰ����е������� */
	public void addTopicNum(int boardId){
		String sql="update tb_board set board_allTopicNum=board_allTopicNum+1 where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			mydb.getCount();
		} catch (SQLException e) {
			System.out.println("�ۼӵ�ǰ����е�������ʧ�ܣ�");
			e.printStackTrace();
		}
		mydb.closed();
	}
	/** @���ܣ�ʹ�������������� */
	public void reduceTopicNum(String topicType,int boardId){
		String sql="";
		if("1".equals(topicType))						//����Ǿ�������ͬʱ���¾��еľ���������
			sql="update tb_board set board_allTopicNum=board_allTopicNum-1,board_bestTopicNum=board_bestTopicNum-1 where id=?";
		else
			sql="update tb_board set board_allTopicNum=board_allTopicNum-1 where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();		
	}
	/** @���ܣ�ʹ�����������ӣ� */
	public void addTopicNum(String topicType,int boardId){
		String sql="";
		if("1".equals(topicType))						//����Ǿ�������ͬʱ���¾��еľ���������
			sql="update tb_board set board_allTopicNum=board_allTopicNum+1,board_bestTopicNum=board_bestTopicNum+1 where id=?";
		else
			sql="update tb_board set board_allTopicNum=board_allTopicNum+1 where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();		
	}
	/** @���ܣ�����operate����(ֵΪadd��ʾ�ӣ���reduce��ʾ����)���°��ľ������� */
	public void updateBestTopicNum(String operate,int boardId){
		String sql="";
		if("add".equals(operate))						//����Ǿ����������Ӿ���������
			sql="update tb_board set board_bestTopicNum=board_bestTopicNum+1 where id=?";
		else if("reduce".equals(operate))
			sql="update tb_board set board_bestTopicNum=board_bestTopicNum-1 where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();		
	}
	/** @���ܣ����������д�����ǰ */
	public void updateOrderToUp(int boardId){
		String sql="update tb_board set board_order=board_order-1 where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		mydb.closed();
	}
	/** @���ܣ����������д����ú� */
	public void updateOrderToDown(int boardId){
		String sql="update tb_board set board_order=board_order+1 where id=?";
		Object[] params={boardId};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		mydb.closed();
	}
	
	public int update(Object[] params){
		String sql="update tb_board set category_id=?,board_name=?,board_info=?,board_status=?,board_order=? where id=?";
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
	/** @���ܣ���ȡָ�����IDֵ(���)�İ������ */
	public List getAssignBoardsName(int[] assignboardsid){
		Object[] params=null;
		String sql="select board_name from tb_board where id in (assign[])";
		
		if(assignboardsid!=null&&assignboardsid.length!=0){
			String ids="";
			params=new Object[assignboardsid.length];
			for(int i=0;i<assignboardsid.length;i++){
				params[i]=assignboardsid[i];
				ids+="?,";
			}
			ids=ids.substring(0,ids.length()-1);
			sql=sql.replace("assign[]",ids);
			
			List boards=null;
			DB mydb=new DB();
			mydb.doPstm(sql,params);
			try {
				ResultSet rs=mydb.getRs();
				if(rs!=null){
					boards=new ArrayList();
					while(rs.next())
						boards.add(rs.getString(1));
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			mydb.closed();
			return boards;
		}
		else
			return null;
	}
	public int delete(int categoryId){
		String sql="delete from tb_board where id=?";
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
}
