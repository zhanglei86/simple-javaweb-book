package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AttachmentDao;
import com.dao.ReplyDao;
import com.dao.TopicDao;
import com.toolsbean.MyFileHandler;
import com.toolsbean.StringHandler;
import com.valuebean.ReplyBean;

public class ReplyServlet extends SuperServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
		if(replyId==null){
			request.setAttribute("message","<li>�ظ���IDֵ����</li>");
			RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			rd.forward(request,response);	
		}
		else{
			String[] subPaths=(String[])request.getAttribute("subPaths");
			if(subPaths!=null&&subPaths.length==4){
				String operate=subPaths[3];
				try {
					Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//����opName����ֵ��ȡ����ΪopName����ֵ�ķ�����Class����ָ���÷����и�����������
					md.setAccessible(true);													//��Ϊtrue���ܵ���˽�з���
					md.invoke(this,new Object[]{request,response});							//���÷�����Object����ָ��������Ҫ�Ĳ���
				}catch (Exception e) {
					e.printStackTrace();
					invalidate(request,response);	
				}			
			}
			else
				invalidate(request,response);			
		}
	}	
	/** @���ܣ��������༭�ظ���ҳ�������Servlet */
	private void modifyview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		try {
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("modifyview"));
			
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			ReplyBean single=new ReplyDao().getReplySingle(replyId);
			if(single==null){
				message="<li>Ҫ�༭�Ļظ��������ڻ��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else
				request.setAttribute("replysingle",single);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** @���ܣ�����༭�ظ��������Servlet */
	private void modifyrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
		
		String title=request.getParameter("title");
		String content=StringHandler.saveQuoteText(request.getParameter("content"));
		String emotion=request.getParameter("emotion");
		String attachment=request.getParameter("attachment");
		Object[] params={title,content,emotion,replyId};

		int i=new ReplyDao().updateReply(params);
		if(i<=0){
			message="<li>�༭ʧ�ܣ�</li>";
			message+="<a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);
			
			if("1".equals(attachment)){								//��Ӹ���
				String autoforward=request.getContextPath()+"/visit/attachment/c/uploadview?replyId="+replyId;			//�Զ�ת������Ӹ���ҳ���·��
				request.setAttribute("autoforward",autoforward);
				message="�� �༭�ɹ������潫ת������Ӹ���ҳ�棡";
			}
			else{													//û�и���
				String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;						//�Զ�ת�����ۿ����������·��
				request.setAttribute("autoforward",autoforward);
				message="�� �༭�ɹ���3����Զ�ת�������޸ĵ����ӣ�";
			}
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleteview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward=INDEXTEMP;		
		request.setAttribute("mainPage",getInitParameter("deleteview"));
		
		try {
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			ReplyBean replysingle=new ReplyDao().getReplySingle(replyId);
			if(replysingle==null){
				message="<li>Ҫɾ���Ļظ��������ڻ��Ѿ���ɾ����</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else
				request.setAttribute("replysingle",replysingle);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	private void deleterun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		boolean mark=validatepswd(request);
		if(!mark){
			message="<li>����Ĺ���Ա������������뵱ǰ��¼�û����룡</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>��������</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",RUNSUCCESS);

			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));			
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			
			TopicDao topicDao=new TopicDao();
			ReplyDao replyDao=new ReplyDao();
			AttachmentDao attachmentDao=new AttachmentDao();
			
			replyDao.deleteReply(replyId);			// ɾ���ûظ���
			topicDao.reduceReplyNum(topicId);		// ���ûظ�����������Ļظ�������
			
			/** ɾ���ûظ������еĸ��� */
			List savenames=attachmentDao.getAttachmentSaveNameByReply(replyId);		//��ȡ�ûظ��������б����ڴ����еĸ�������
			deleteFiles(savenames);													//ɾ�������ļ�
			attachmentDao.deleteAttachmentByReplyId(replyId);						//Ȼ��ɾ�����ݿ�����Ϣ
			
			String autoforward=request.getContextPath()+"/visit/topic/a/view?topicId="+topicId;			//�Զ�ת����·��
			request.setAttribute("autoforward",autoforward);
			message="�� ɾ���ظ����ɹ���3����Զ�ת������ǰ���⣡";
		}			

		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	/** 
	 * @���ܣ�����MyFileHandler���deleteFile()��������ɾ���ļ�
	 * @������List����savenames����ʾҪɾ�����ļ����� 
	 */
	private void deleteFiles(List savenames){
		String filedir=getServletContext().getInitParameter("filedir");					//��ȡ�ڵ�ǰWebӦ���´�����и�����Ŀ¼
		String[] destfilepathname=null;													//�����洢Ҫɾ������������·��
		if(savenames!=null&&savenames.size()!=0){
			destfilepathname=new String[savenames.size()];
			for(int k=0;k<savenames.size();k++){
				String savename=(String)savenames.get(k);								//��ȡ���������ڴ�����ʹ�õ��ļ���
				String deletefilename=filedir+savename;									//��ǰWebӦ���¸����Ĵ��·��
				destfilepathname[k]=getServletContext().getRealPath(deletefilename);	//��ȡ����������·��������Ŀ¼�µ�·����
			}
			MyFileHandler.deleteFile(destfilepathname);
		}
	}
}
