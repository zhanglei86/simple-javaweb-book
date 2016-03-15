package com.dao;

import java.sql.SQLException;

import com.toolsbean.DB;

public class GradeDao {
	private int exper_postPoint;				//�������Ӻ����������ӵľ���ֵ
	private int exper_replyPoint;				//�ظ����Ӻ����������ӵľ���ֵ
	private int exper_bestPoint;				//���ӼӾ������������ӵľ���ֵ
	private int charm_viewPoint;				//�ۿ����Ӻ����������ӵ�����ֵ
	private int charm_replyPoint;				//�ظ����Ӻ����������ӵ�����ֵ
	
	public GradeDao(){
		exper_postPoint=3;
		exper_replyPoint=2;
		exper_bestPoint=10;
		charm_viewPoint=1;
		charm_replyPoint=5;	
	}
	/**
	 *@���ܣ���������ֵ�ļ���
	 *@������memberIdΪ����IDֵ 
	 */
	public void experPost(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_experience=member_experience+"+exper_postPoint+" where id=?";
		int i=update(sql, params);
		if(i<=0)
			System.out.println("���������Ӿ���ֵʧ�ܣ�");
	}
	/**
	 *@���ܣ���������ֵ�ļ���
	 *@������memberIdΪ����IDֵ 
	 */
	public void experReply(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_experience=member_experience+"+exper_replyPoint+" where id=?";
		int i=update(sql, params);
		if(i<=0)
			System.out.println("���������Ӿ���ֵʧ�ܣ�");
	}
	/**
	 *@���ܣ��Ӿ�����ֵ�ļ���
	 *@������memberIdΪ����IDֵ 
	 */
	public void experBest(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_experience=member_experience+"+exper_bestPoint+" where id=?";
		int i=update(sql, params);
		if(i<=0)
			System.out.println("�Ӿ������Ӿ���ֵʧ�ܣ�");
	}
	/**
	 *@���ܣ��ۿ����������ֵ�ļ���
	 *@������memberNameΪ�������� 
	 */
	public void charmLook(String memberName){
		Object[] params={memberName};
		String sql="update tb_member set member_charm=member_charm+"+charm_viewPoint+" where member_name=?";
		int i=update(sql,params);		
		if(i<=0)
			System.out.println("�ۿ������������������ֵʧ�ܣ�");
	}
	/**
	 *@���ܣ��ظ����������ֵ�ļ���
	 *@������memberIdΪ����IDֵ 
	 */
	public void charmReply(int memberId){
		Object[] params={memberId};
		String sql="update tb_member set member_charm=member_charm+"+charm_replyPoint+" where id=?";
		int i=update(sql,params);
		if(i<=0)
			System.out.println("�ظ������������������ֵʧ�ܣ�");
	}
	/**
	 *@���ܣ��ظ����������ֵ�ļ���
	 *@������memberNameΪ��������
	 */
	public void charmReply(String memberName){
		Object[] params={memberName};
		String sql="update tb_member set member_charm=member_charm+"+charm_replyPoint+" where member_name=?";
		update(sql,params);
	}
	private int update(String sql,Object[] params){
		int i=-1;
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
}
