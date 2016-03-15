package com.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.AttachmentDao;
import com.dao.ReplyDao;
import com.dao.TopicDao;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.toolsbean.MyFileHandler;
import com.toolsbean.StringHandler;
import com.valuebean.AttachmentBean;
import com.valuebean.ReplyBean;
import com.valuebean.TopicBean;
import com.valuebean.UserBean;

public class AttachmentServlet extends SuperServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
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
		else{
			invalidate(request,response);
		}
	}
	private void uploadview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		try {			
			Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
			Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
			
			if(topicId==null&&replyId==null){
				message="<li>��ҪΪ˭��Ӹ���������</li>";
				forward=RUNFAIL;
			}
			else if(topicId!=null){								//Ϊ������Ӹ���
				TopicBean topic=new TopicDao().getTopicSingle(topicId);
				if(topic==null){
					message="<li>Ҫ��Ӹ��������ⲻ���ڻ��Ѿ���ɾ����</li>";
					forward=RUNFAIL;
				}	
				else{
					forward=INDEXTEMP;	
					request.setAttribute("mainPage",getInitParameter("addattachmentTopicmain"));
					request.setAttribute("attachtopic",topic);
				}
			}
			else{											//Ϊ�ظ�����Ӹ���
				ReplyBean reply=new ReplyDao().getReplySingle(replyId);
				if(reply==null){
					message="<li>Ҫ��Ӹ����Ļظ��������ڻ��Ѿ���ɾ����</li>";
					forward=RUNFAIL;
				}
				else{
					forward=INDEXTEMP;
					request.setAttribute("mainPage",getInitParameter("addattachmentReplymain"));
					request.setAttribute("attachreply",reply);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(forward);
		rd.forward(request,response);
	}
	
	private void uploadrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		int k=-1;
		String message="";	
	    
		Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
		Integer replyId=StringHandler.strToint(request.getParameter("replyId"));
		if(replyId==null)													//Ϊ������Ӹ���
			replyId=-1;			
		long maxsize=Long.parseLong(getInitParameter("maxsize"));			//��ȡ�����ϴ��ļ����ܳ���
		try{			
			SmartUpload myup=new SmartUpload();
			myup.initialize(this,request,response);
		    myup.setTotalMaxFileSize(maxsize);                     			//���������ϴ��ļ����ܳ���
		    myup.upload();                                         			//�ϴ��ļ�
		    
			Date date=new Date();		    								//��ȡ��ǰʱ��
			String loginername=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		    Files files=myup.getFiles();                        			//��ȡ���е��ϴ��ļ�
		    
		    int count=files.getCount();										//��ȡ�ϴ��ļ�����	    
		    String uptime=StringHandler.timeTostr(date);					//ת����ǰʱ��Ϊ�ַ���
		    String[] serials=StringHandler.getSerial(date,count);			//��ȡcount���Ե�ǰʱ����Ϊ���ݵı�ʶ��,���ǽ����������ϴ����ļ�
		    
		    AttachmentDao attachDao=new AttachmentDao();
		    for(int i=0;i<count;i++){               						//�����ȡ�ϴ����ļ�
		    	File singlefile=files.getFile(i);
		        if(!singlefile.isMissing()){                   				//������ļ�
		        	int filesize=singlefile.getSize();
		        	if(filesize==0)
		        		message+="<li>�ļ�"+(i+1)+"�ϴ�ʧ�ܣ��ϴ����ļ���СΪ0��</li><br>";
		        	else{
		        		String filename=singlefile.getFileName();			//��ȡ�ϴ��ļ�������
		        		String savename=serials[i]+"_"+loginername+"."+singlefile.getFileExt();
		        		String filetype=singlefile.getContentType().trim();
		        		String filedir=getServletContext().getInitParameter("filedir");								//��ȡ����ļ���Ŀ¼(��Ŀ¼λ��webӦ�ø�Ŀ¼��)
		        		
		        		Object[] params={topicId,replyId,savename,filename,filetype,filesize,uptime};
		        		k=attachDao.addAttachInfo(params);										//�����ļ���Ϣ�����ݿ���
		        		if(k<=0){																//��Ϣ����ʧ�ܣ�
		        			message+="<li>�ļ�"+(i+1)+"�ϴ�ʧ�ܣ�</li><br>";		        		
		        		}
		        		else{																	//��Ϣ����ɹ�
		        			if(replyId==-1)									//Ϊ������Ӹ���
		        				new TopicDao().updateTopicAttachment("1",topicId);				//�������topic_attachment�ֶ���Ϊ"1"����ʾ��������и���
		        			else											//Ϊ�ظ�����Ӹ���
		        				new ReplyDao().updateReplyAttachment("1",replyId);				//���ظ�����reply_attachment�ֶ���Ϊ"1"����ʾ�ûظ������и���
		        			singlefile.saveAs(filedir+savename,File.SAVEAS_VIRTUAL);			//�����ļ������̵�ָ��Ŀ¼��
		        			message+="���ļ�"+(i+1)+"��<b><font color='red'>"+singlefile.getFilePathName()+"</b></font> �ϴ��ɹ���</li><br>";
		        		}
		        	}		        	
		        }
		    }
		    attachDao.closed();
		}catch(java.lang.SecurityException e1){
			k=-1;
			message="<li>�ϴ����ļ��ܴ�С��������"+(maxsize/1024/1024)+"�ף�</li><br>";
		    e1.printStackTrace();
		}
		catch(Exception e2){
			k=-1;
		    message="<li>�ļ��ϴ�ʧ�ܣ�</li><br>";
		    e2.printStackTrace();
		}catch(java.lang.OutOfMemoryError e3){
			k=-1;
			message="<li>���ϴ����ļ�̫��</li><br>";
			e3.printStackTrace();
		}
		if(k<=0){
			message+="<a href='javascript:window.history.go(-1)'>>> ��������</a><br>";
			message+="<a href='"+request.getContextPath()+"/visit/topic/a/view?topicId="+topicId+"' target='_parent'>>> ��������</a>";
		}
        else{
        	new TopicDao().updateTopicAttachment("1",topicId);
        	message+="<a href='javascript:window.history.go(-1)'>>> �����ϴ�</a><br>";
        	message+="<a href='"+request.getContextPath()+"/visit/topic/a/view?topicId="+topicId+"' target='_parent'>>> ��������</a>";
        }		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("uploadresult"));
		rd.forward(request,response);
	}	
	private void downloadrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		Integer attachmentid=StringHandler.strToint(request.getParameter("attachmentid"));
		if(attachmentid==null){
		    request.setAttribute("message","<li>����IDֵ����</li><br><a href='javascript:window.history.go(-1)'>����</a>");
		    RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
		    rd.forward(request,response);
		}
		else{
			try{				
				AttachmentDao attachmentDao=new AttachmentDao();
				AttachmentBean attachmentsingle=attachmentDao.getAttachmentSingle(attachmentid);
				attachmentDao.closed();
				if(attachmentsingle==null){
					request.setAttribute("message","<li>Ҫ���ص��ļ������ڻ��Ѿ���ɾ����</li><br><a href='javascript:window.history.go(-1)'>����</a>");
					RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					rd.forward(request,response);
				}
				else{
					String filedir=getServletContext().getInitParameter("filedir");
					String savename=attachmentsingle.getAttachmentSaveName();
					String filename=attachmentsingle.getAttachmentFileName();
					filename=new String(filename.getBytes("gb2312"),"ISO-8859-1");
					String filetype=attachmentsingle.getAttachmentFileType();
					String downfile=filedir+savename;

					String destfilepathname=getServletContext().getRealPath(downfile);
					java.io.File deletefile=new java.io.File(destfilepathname);
					if(deletefile.exists()){
						SmartUpload mydown=new SmartUpload();
						mydown.initialize(this,request,response);
						mydown.setContentDisposition(null);
						mydown.downloadFile(downfile,filetype,filename);
						mydown=null;
					}
					else{
						request.setAttribute("message","<li>Ҫ���ص��ļ������ڻ��Ѿ���ɾ����</li><br><a href='javascript:window.history.go(-1)'>����</a>");
						RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					    rd.forward(request,response);						
					}					
				}			    
			}catch(Exception e){
			    e.printStackTrace();
			    request.setAttribute("message","<li>�ļ�����ʧ�ܣ�</li><br><a href='javascript:window.history.go(-1)'>����</a>");
			    RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
			    rd.forward(request,response);
			}
		}
	}
	private void deleteview(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String message="";
		String forward="";
		
		Integer attachmentid=StringHandler.strToint(request.getParameter("attachmentid"));
		if(attachmentid==null){
			message="<li>����IDֵ����</li>";
		    message+="<br><a href='javascript:window.history.go(-1)'>����</a>";
			forward=RUNFAIL;
		}
		else{
			forward=INDEXTEMP;
			request.setAttribute("mainPage",getInitParameter("deleteview"));
			try {
				AttachmentDao attachmentDao=new AttachmentDao();
				AttachmentBean attachment = attachmentDao.getAttachmentSingle(attachmentid);
				attachmentDao.closed();
				request.setAttribute("attachmentsingle",attachment);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("message",message);
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
			String savename=request.getParameter("savename");
			String filedir=getServletContext().getInitParameter("filedir");
			String deletefilename=filedir+savename;
			String destfilepathname=getServletContext().getRealPath(deletefilename);
			
			//ɾ�������ļ�
			MyFileHandler.deleteFile(new String[]{destfilepathname});
			
			//ɾ�����ݿ����ļ���Ӧ����Ϣ
			Integer attachmentid=StringHandler.strToint(request.getParameter("attachmentid"));
			AttachmentDao attachmentDao=new AttachmentDao();
			int i=attachmentDao.deleteAttachment(attachmentid);
			if(i<=0){
				message="<li>�����е��ļ��Ѿ��ɹ�ɾ��������ɾ�����ݿ����ļ���Ϣʱʧ�ܣ�</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>����</a>";
				forward=RUNFAIL;
			}
			else{
				Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
				Integer replyId=StringHandler.strToint(request.getParameter("replyId"));

				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				message="<li>ɾ�������ɹ���3����Զ�ת������ǰ���⣡</li>";
				String autoforward=request.getParameter("autoforward");			//�Զ�ת������Ӹ���ҳ���·��
				request.setAttribute("autoforward",autoforward);
				
				if(topicId!=null&&replyId!=null){
					if(replyId==-1){							//��ѯ�����Ƿ��и���
						boolean isexist=attachmentDao.isExistByTopic(topicId);
						if(!isexist)										//û�и����������ӵ�topic_attachment�ֶ�ֵ��Ϊ"0"
							new TopicDao().updateTopicAttachment("0", topicId);
					}
					else{										//��ѯ�ûظ����Ƿ��и���
						boolean isexist=attachmentDao.isExistByReply(replyId);
						if(!isexist)										//û�и��������ظ�����reply_attachment�ֶ�ֵ��Ϊ"0"
							new ReplyDao().updateReplyAttachment("0", replyId);
					}					
				}
			}
			attachmentDao.closed();
		}	
		
		request.setAttribute("message",message);
	    RequestDispatcher rd=request.getRequestDispatcher(forward);
	    rd.forward(request,response);
	}
}
