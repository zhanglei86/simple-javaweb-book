package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.toolsbean.DB;
import com.toolsbean.StringHandler;
import com.valuebean.CollectBean;
import com.valuebean.TopicBean;
import com.valuebean.UnCheckTopicBean;

public class TopicDao extends SuperDao {	
	public int getBoardId(int topicId){
		int boardId=-1;
		String sql="select board_id from tb_topic where id=?";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				boardId=rs.getInt(1);
				rs.close();
			}
		} catch (SQLException e) {
			boardId=-1;
			e.printStackTrace();
		}
		mydb.closed();
		return boardId;
	}
	public String getTopicStatus(int topicId){
		String status="0";
		String sql="select topic_status from tb_topic where id=?";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next())
				status=rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mydb.closed();
		return status;
	}
	/** @功能：获取主题所属版块、类别的ID和名称 */
	public Map getCategoryBoardMap(int topicId) throws SQLException{
		Map both=null;
		String sql="select c.id,c.category_name,b.id,b.board_name from tb_category c,tb_board b,tb_topic t where (c.id=b.category_id) and (b.id=t.board_id) and (t.id=?)";
		Object[] params={topicId};
		
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			both=new HashMap();
			Map category=new HashMap();
			category.put("id",rs.getInt(1));
			category.put("name",rs.getString(2));
			Map board=new HashMap();
			board.put("id",rs.getInt(3));
			board.put("name",rs.getString(4));
			both.put("category",category);
			both.put("board",board);
			rs.close();
		}
		mydb.closed();
		return both;
	}
	/** @功能：获取当前版块中的置顶帖子 */
	public List getTopTopicList(int boardId) throws SQLException{
		Object[] params={boardId};
		String sql="select * from tb_topic where (board_id=?) and (topic_type='2') and (topic_status!='0') order by topic_operateTime desc";
		List topTopiclist=getList(sql,params);
		return topTopiclist;
	}
	/** @功能：获取指定会员发表的帖子 */
	public List getMemberPostTopicList(String memberName,String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		Object[] params={memberName};
		String sqlall="select * from tb_topic where (topic_author=?) and (topic_status!='0') order by topic_postTime desc";
		
		setDaoPerR(5);
		setDaoPerP(10);
		setDaoPage(sqlall,params,strcurrentP, strcurrentG, goWhich);
		
		int currentP=getDaoPage().getCurrentP();
		int top1=getDaoPage().getPerR();
		int top2=(currentP-1)*top1;
		
		String sqlsub="";
		if(currentP==1)
			sqlsub="select top "+top1+" * from tb_topic where (topic_author=?) and (topic_status!='0') order by topic_postTime desc";
		else
			sqlsub="select top "+top1+" * from tb_topic i where (topic_author=?) and (topic_status!='0') and (topic_postTime < (select min(topic_postTime) from (select top "+top2+" * from tb_topic where (topic_author=i.topic_author) and (topic_status!='0') order by topic_postTime desc) as minv)) order by topic_postTime desc";
		
		List list=getList(sqlsub,params);
		return list;
	}
	/** @功能：获取指定会员回复的帖子 */
	public List getMyReplyTopicList(String memberName,String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		String sqlall="select * from tb_topic where (topic_status!='0') and id in (select distinct topic_id from tb_reply where reply_author='"+memberName+"')";
		
		setDaoPerR(5);
		setDaoPerP(10);
		setDaoPage(sqlall,null,strcurrentP, strcurrentG, goWhich);
		
		int currentP=getDaoPage().getCurrentP();
		int top1=getDaoPage().getPerR();
		int top2=(currentP-1)*top1;
		
		String sqlsub="";
		if(currentP==1)
			sqlsub="select top "+top1+" * from tb_topic where (topic_status!='0') and id in (select distinct topic_id from tb_reply where reply_author='"+memberName+"') order by topic_postTime desc";
		else
			sqlsub="select top "+top1+" * from tb_topic where and (topic_status!='0') and id in (select distinct topic_id from tb_reply where reply_author='"+memberName+"') and (topic_postTime<(select min (topic_postTime) from (select top "+top2+" * from tb_topic where and (topic_status!='0') and id in (select distinct topic_id from tb_reply where reply_author='"+memberName+"') order by topic_postTime desc) as minv)) order by topic_postTime desc";
		
		List list=getList(sqlsub,null);
		return list;
	}
	/** @功能：获取当前版块中的非置顶帖子 */
	public List getTopicList(int boardId,String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		Object[] params={boardId};
		String sqlall="select * from tb_topic where (board_id=?) and (topic_type!='2') and (topic_status!='0') order by topic_operateTime desc";
		
		setDaoPerR(5);
		setDaoPerP(3);
		setDaoPage(sqlall,params,strcurrentP, strcurrentG, goWhich);
		
		int currentP=getDaoPage().getCurrentP();
		int top1=getDaoPage().getPerR();
		int top2=(currentP-1)*top1;
		
		String sqlsub="";
		if(currentP==1)
			sqlsub="select top "+top1+" * from tb_topic where (board_id=?) and (topic_type!='2') and (topic_status!='0') order by topic_operateTime desc";
		else
			sqlsub="select top "+top1+" * from tb_topic i where (board_id=?) and (topic_type!='2') and (topic_status!='0') and (topic_operateTime < (select min(topic_operateTime) from (select top "+top2+" * from tb_topic where (board_id=i.board_id) and (topic_type!='2') and (topic_status!='0') order by topic_operateTime desc) as minv)) order by topic_operateTime desc";
		
		List topiclist=getList(sqlsub,params);
		return topiclist;
	}
	/** @功能：获取当前某个主题的详细内容 */
	public TopicBean getTopicSingle(int topicId) throws SQLException{
		TopicBean single=null;
		Object[] params={topicId};
		String sql="select * from tb_topic where (id=?)";
		
		List list=getList(sql,params);
		if(list!=null&&list.size()!=0){
			single=(TopicBean)list.get(0);
			single.setTopicAuthor(new UserDao().getUserSingle(single.getTopicAuthorName()));
			if(single.getTopicAttachmentSign().equals("1"))
				single.setTopicAttachment(new AttachmentDao().getAttachmentsByTopic(single.getId()));
		}
		return single;
	}
	public TopicBean getEditViewSingle(int topicId) throws SQLException{
		TopicBean single=null;
		Object[] params={topicId};
		String sql="select * from tb_topic where (id=?)";
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			single=new TopicBean();
			single.setId(rs.getInt(1));
			single.setBoardId(rs.getInt(2));
			single.setTopicTitle(rs.getString(3));
			single.setTopicContent(rs.getString(4));
			single.setTopicAuthorName(rs.getString(5));
			single.setTopicEmotion(rs.getString(6));
			single.setTopicType(rs.getString(8));
			single.setTopicStatus(rs.getString(9));
			single.setTopicReplyNum(rs.getInt(10));
			single.setTopicAttachmentSign(rs.getString(11));
			single.setTopicPostTime(StringHandler.timeTostr(rs.getTimestamp(12)));
			rs.close();
		}
		mydb.closed();
		return single;
	}
	/**
	 * @功能：获取上一主题和下一主题
	 * @返回：Map对象，该对象中存储的是主题ID值
	 */
	public Map getBothSidesTopic(String consultTime,int boardId) throws SQLException{
		Map bothSidesMap=new HashMap();
		DB mydb=new DB();

		String sqlprev="select id from tb_topic where (topic_status!=0) and (topic_operateTime=(select min(topic_operateTime) from tb_topic where topic_operateTime>'"+consultTime+"' and (board_id="+boardId+"))) order by topic_operateTime desc";
		mydb.doPstm(sqlprev,null);
		ResultSet rsprev=mydb.getRs();
		if(rsprev!=null&&rsprev.next()){
			bothSidesMap.put("prevId",rsprev.getInt(1));
			rsprev.close();
		}
		
		String sqlnext="select id from tb_topic where (topic_status!=0) and (topic_operateTime=(select max(topic_operateTime) from tb_topic where topic_operateTime<'"+consultTime+"' and (board_id="+boardId+"))) order by topic_operateTime desc";
		mydb.doPstm(sqlnext,null);
		ResultSet rsnext=mydb.getRs();
		if(rsnext!=null&&rsnext.next()){
			bothSidesMap.put("nextId",rsnext.getInt(1));
			rsnext.close();
		}
		mydb.closed();
		return bothSidesMap;
	}
	/**
	 * @功能：查询数据库，封装记录
	 * @参数：sql为查询语句
	 * @参数：params为sql语句中?占位符赋值的数组
	 * @参数：getAuthor为boolean值，表示是否获取作者信息的标志：true为获取，false为不获取
	 */
	private List getList(String sql,Object[] params) throws SQLException{
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		ResultSet rs=mydb.getRs();
		if(rs!=null){
			list=new ArrayList();
			while(rs.next()){
				TopicBean single=new TopicBean();
				single.setId(rs.getInt(1));
				single.setBoardId(rs.getInt(2));
				single.setTopicTitle(rs.getString(3));
				single.setTopicContent(rs.getString(4));
				single.setTopicAuthorName(rs.getString(5));
				single.setTopicEmotion(rs.getString(6));
				single.setTopicHits(rs.getInt(7));
				single.setTopicType(rs.getString(8));
				single.setTopicStatus(rs.getString(9));
				single.setTopicReplyNum(rs.getInt(10));
				single.setTopicAttachmentSign(rs.getString(11));
				single.setTopicPostTime(StringHandler.timeTostr(rs.getTimestamp(12)));
				single.setTopicOperateTime(StringHandler.timeTostr(rs.getTimestamp(13)));				
				single.setLastReply(new ReplyDao().getLastReply(single.getId()));
				list.add(single);
			}
			rs.close();
		}
		mydb.closed();
		return list;
	}

	/** @功能：获取当前版块的最新发表主题 */
	public TopicBean getLastTopic(int boardId) throws SQLException{
		TopicBean last=null;
		String sql="select * from tb_topic i where (board_id=?) and (topic_postTime=(select max(topic_postTime) from tb_topic where (board_id=i.board_id)))";
		Object[] params={boardId};
		
		DB mydb=new DB();
		mydb.doPstm(sql,params);		
		ResultSet rs=mydb.getRs();		
		if(rs!=null&&rs.next()){
			last=new TopicBean();
			last.setId(rs.getInt(1));
			last.setTopicTitle(rs.getString(3));
			last.setTopicAuthorName(rs.getString(5));
			last.setTopicPostTime(StringHandler.timeTostr(rs.getTimestamp(12)));
			rs.close();
		}
		mydb.closed();
		return last;
	}
	/** @功能：发表帖子 */
	public int postTopic(Object[] params){
		int i=0;
		String sql="insert into tb_topic values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
	/** @功能：将主题的访问次数加１ */
	public void addTopicHits(int id){
		String sql="update tb_topic set topic_hits=topic_hits+1 where id=?";
		Object[] params={id};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		try {
			mydb.getCount();
		} catch (SQLException e) {
			System.out.println("累加主题的访问次数失败！");
			e.printStackTrace();
		}
		mydb.closed();
	}
	/** @功能：将主题的回复数加１ */
	public void addTopicReplyNum(int id){
		String sql="update tb_topic set topic_replyNum=topic_replyNum+1 where id=?";
		Object[] params={id};
		DB mydb=new DB();
		mydb.doPstm(sql,params);
		try {
			mydb.getCount();
		} catch (SQLException e) {
			System.out.println("累加主题的回复数失败！");
			e.printStackTrace();
		}
		mydb.closed();
	}
	public int updateTopic(Object[] params){
		String sql="update tb_topic set topic_title=?,topic_content=?,topic_emotion=? where id=?";
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
	/** @功能：修改帖子类型 */
	public int updateType(Object[] params){
		String sql="update tb_topic set topic_type=?,topic_operateTime=? where id=?";
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
	/** @功能：修改帖子状态 */
	public int updateStatus(Object[] params){
		String sql="update tb_topic set topic_status=?,topic_operateTime=? where id=?";
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
	/** @功能：移动帖子(修改帖子的所属版块ID值) */
	public int updateBoard(Object[] params){
		String sql="update tb_topic set board_id=?,topic_operateTime=? where id=?";
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=0;
		try {
			i= mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		mydb.closed();
		return i;		
	}
	/** @功能：收藏帖子 */
	public int addCoolect(Object[] params){
		String sql="insert into tb_collect values(?,?,?)";
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		int i=0;
		try {
			i = mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		mydb.closed();
		return i;
	}
	/** @功能：更新主题的被操作时间为当前时间 */
	public void updateOperateTime(int id,String time){
		String sql="update tb_topic set topic_operateTime=? where id=?";
		Object[] params={time,id};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			mydb.getCount();
		} catch (SQLException e) {
			System.out.println("更新主题的被操作时间失败！");
			e.printStackTrace();
		}
		mydb.closed();
	}
	public void updateTopicAttachment(String sign,int topicId){
		String sql="update tb_topic set topic_attachment=? where id=?";
		Object[] params={sign,topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			mydb.getCount();
		} catch (SQLException e) {
			System.out.println("更新主题的topic_attachment字段失败！");
			e.printStackTrace();
		}
		mydb.closed();
	}
	/** @功能：获取刚插入记录的ID */
	public int getJustTopicId(String author,String time){
		int id=-1;
		String sql="select id from tb_topic where (topic_author=?) and (topic_postTime=?)";
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
			e.printStackTrace();
		}
		mydb.closed();
		return id;
	}
	/** @功能：获取主题作者名称*/
	public String getAuthorName(int topicId){
		String authorname="";
		String sql="select topic_author from tb_topic where id=?";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				authorname=rs.getString(1);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mydb.closed();
		return authorname;
	}
	/** @功能：删除指定ID的主题 */
	public int deleteTopic(int topicId){
		String sql="delete from tb_topic where id=?";
		Object[] params={topicId};
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
	/** @功能：使主题回复数减１ */
	public void reduceReplyNum(int topicId){
		String sql="update tb_topic set topic_replyNum=topic_replyNum-1 where id=?";
		Object[] params={topicId};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
	public List getAllUnCheckTopics(){
		String sql="select c.id,c.category_name,b.id,b.board_name,t.* from tb_category c,tb_board b,tb_topic t where (c.id=b.category_id) and (b.id=t.board_id) and(t.topic_status='0') order by t.topic_postTime desc";
		List list=getUnCheckList(sql, null);
		return list;
	}
	public List getMyCategoryUnCheckTopics(int[] assigncategoryids){
		Object[] params=null;
		String sql="select c.id,c.category_name,b.id,b.board_name,t.* from tb_category c,tb_board b,tb_topic t where (c.id=b.category_id) and (b.id=t.board_id) and(t.topic_status='0') and (c.id in (assign[])) order by t.topic_postTime desc";
		if(assigncategoryids!=null&&assigncategoryids.length!=0){
			params=new Object[assigncategoryids.length];
			String ids="";
			for(int i=0;i<assigncategoryids.length;i++){				
				params[i]=assigncategoryids[i];
				ids+="?,";
			}
			ids=ids.substring(0,ids.length()-1);
			sql=sql.replace("assign[]",ids);
			
			List list=getUnCheckList(sql, params);
			return list;
		}
		else
			return null;
	}
	public List getMyBoardUnCheckTopics(int[] assignboardids){
		Object[] params=null;
		String sql="select c.id,c.category_name,b.id,b.board_name,t.* from tb_category c,tb_board b,tb_topic t where (c.id=b.category_id) and (b.id=t.board_id) and(t.topic_status='0') and (b.id in (assign[])) order by t.topic_postTime desc";
		if(assignboardids!=null&&assignboardids.length!=0){
			params=new Object[assignboardids.length];
			String ids="";
			for(int i=0;i<assignboardids.length;i++){				
				params[i]=assignboardids[i];
				ids+="?,";
			}
			ids=ids.substring(0,ids.length()-1);
			sql=sql.replace("assign[]",ids);
			
			List list=getUnCheckList(sql, params);
			return list;
		}
		else
			return null;
	}
	private List getUnCheckList(String sql,Object[] params){
		List list=null;
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null){
				list=new ArrayList();
				while(rs.next()){				
					Map category=new HashMap();
					category.put("id",rs.getInt(1));
					category.put("name",rs.getString(2));
					
					Map board=new HashMap();
					board.put("id",rs.getInt(3));
					board.put("name",rs.getString(4));
					
					TopicBean topic=new TopicBean();
					topic.setId(rs.getInt(5));
					topic.setTopicTitle(rs.getString(7));
					topic.setTopicAuthorName(rs.getString(9));
					topic.setTopicEmotion(rs.getString(10));
					topic.setTopicHits(rs.getInt(11));
					topic.setTopicReplyNum(rs.getInt(14));
					topic.setTopicPostTime(StringHandler.timeTostr(rs.getTimestamp(16)));
					topic.setLastReply(new ReplyDao().getLastReply(topic.getId()));
					
					UnCheckTopicBean uncheck=new UnCheckTopicBean();
					uncheck.setCategory(category);
					uncheck.setBoard(board);
					uncheck.setTopic(topic);
					
					list.add(uncheck);
				}
			}
		} catch (SQLException e) {
			list=null;
			e.printStackTrace();
		}
		return list;
	}
}