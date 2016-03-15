package com.toolsbean;

public class PathHandler {	
	public String[] dividePath(String servletPath,String pathInfo){
		String[] subPaths=null;
		if(servletPath!=null&&pathInfo!=null){
			String temp1=StringHandler.delBlank(servletPath.substring(1));										//ȥ��·���е����ַ���/��
			String temp2=StringHandler.delBlank(pathInfo.substring(1));
	
			String[] subtemp1=divideSub(temp1);
			String[] subtemp2=divideSub(temp2);
			
			if(subtemp1!=null&&subtemp2!=null){
				subPaths=new String[4];
				subPaths[0]=subtemp1[0];			//�洢ǰ��������ı�ʶ��ǰ̨-visit����̨-admin
				subPaths[1]=subtemp1[1];			//�洢ģ�飺��board��ʾ���ģ�顢topic��ʾ����ģ���
				subPaths[2]=subtemp2[0];			//�洢Ȩ�ޱ�ʶ��a-���С�b-�Լ����ϡ�c-�������ϡ�
				subPaths[3]=subtemp2[1];			//�洢������list���б���ʾ��add-���				
			}							
		}
		return subPaths;
	}
	/**
	 * @���ܣ���֤·���Ƿ�Ϊ"A/B"��ʽ������"/"���ŷָ��·��
	 * @������tempΪ��������·��
	 * @���أ��ַ������飬�洢�ָ��ĵ�Ԫ·��
	 */
	private String[] divideSub(String temp){
		int start=temp.indexOf("/");						//��ȡ·���е�һ��"/"����λ��
		int end=temp.lastIndexOf("/");						//��ȡ·�������һ��"/"����λ��
		if(start==end){										//����ȣ����ʾ·����ֻ��һ��"/"����
			String[] subtemp=temp.split("/");				//��"/"���ŷָ�·��
			if(subtemp.length==2&&!subtemp[0].equals("")&&!subtemp[1].equals(""))		//���·��Ϊ"A/B"��ʽ
				return subtemp;
		}
		return null;
	}
}
