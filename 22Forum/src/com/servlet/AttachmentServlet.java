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
				Method md=getClass().getDeclaredMethod(operate,new Class[]{HttpServletRequest.class,HttpServletResponse.class});		//根据opName变量值获取名称为opName变量值的方法。Class数组指定该方法中各参数的类型
				md.setAccessible(true);													//设为true后将能调用私有方法
				md.invoke(this,new Object[]{request,response});							//调用方法，Object数组指定方法需要的参数
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
				message="<li>你要为谁添加附件？啊！</li>";
				forward=RUNFAIL;
			}
			else if(topicId!=null){								//为主题添加附件
				TopicBean topic=new TopicDao().getTopicSingle(topicId);
				if(topic==null){
					message="<li>要添加附件的主题不存在或已经被删除！</li>";
					forward=RUNFAIL;
				}	
				else{
					forward=INDEXTEMP;	
					request.setAttribute("mainPage",getInitParameter("addattachmentTopicmain"));
					request.setAttribute("attachtopic",topic);
				}
			}
			else{											//为回复帖添加附件
				ReplyBean reply=new ReplyDao().getReplySingle(replyId);
				if(reply==null){
					message="<li>要添加附件的回复帖不存在或已经被删除！</li>";
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
		if(replyId==null)													//为主题添加附件
			replyId=-1;			
		long maxsize=Long.parseLong(getInitParameter("maxsize"));			//获取允许上传文件的总长度
		try{			
			SmartUpload myup=new SmartUpload();
			myup.initialize(this,request,response);
		    myup.setTotalMaxFileSize(maxsize);                     			//设置允许上传文件的总长度
		    myup.upload();                                         			//上传文件
		    
			Date date=new Date();		    								//获取当前时间
			String loginername=((UserBean)request.getSession().getAttribute("loginer")).getMemberName();
		    Files files=myup.getFiles();                        			//获取所有的上传文件
		    
		    int count=files.getCount();										//获取上传文件数量	    
		    String uptime=StringHandler.timeTostr(date);					//转化当前时间为字符串
		    String[] serials=StringHandler.getSerial(date,count);			//获取count个以当前时间作为内容的标识符,它们将用来命名上传的文件
		    
		    AttachmentDao attachDao=new AttachmentDao();
		    for(int i=0;i<count;i++){               						//逐个获取上传的文件
		    	File singlefile=files.getFile(i);
		        if(!singlefile.isMissing()){                   				//如果有文件
		        	int filesize=singlefile.getSize();
		        	if(filesize==0)
		        		message+="<li>文件"+(i+1)+"上传失败！上传的文件大小为0！</li><br>";
		        	else{
		        		String filename=singlefile.getFileName();			//获取上传文件的名称
		        		String savename=serials[i]+"_"+loginername+"."+singlefile.getFileExt();
		        		String filetype=singlefile.getContentType().trim();
		        		String filedir=getServletContext().getInitParameter("filedir");								//获取存放文件的目录(该目录位于web应用根目录下)
		        		
		        		Object[] params={topicId,replyId,savename,filename,filetype,filesize,uptime};
		        		k=attachDao.addAttachInfo(params);										//保存文件信息到数据库中
		        		if(k<=0){																//信息保存失败！
		        			message+="<li>文件"+(i+1)+"上传失败！</li><br>";		        		
		        		}
		        		else{																	//信息保存成功
		        			if(replyId==-1)									//为主题添加附件
		        				new TopicDao().updateTopicAttachment("1",topicId);				//将主题的topic_attachment字段设为"1"，表示该主题带有附件
		        			else											//为回复帖添加附件
		        				new ReplyDao().updateReplyAttachment("1",replyId);				//将回复帖的reply_attachment字段设为"1"，表示该回复帖带有附件
		        			singlefile.saveAs(filedir+savename,File.SAVEAS_VIRTUAL);			//保存文件到磁盘的指定目录下
		        			message+="●文件"+(i+1)+"：<b><font color='red'>"+singlefile.getFilePathName()+"</b></font> 上传成功！</li><br>";
		        		}
		        	}		        	
		        }
		    }
		    attachDao.closed();
		}catch(java.lang.SecurityException e1){
			k=-1;
			message="<li>上传的文件总大小不允许超过"+(maxsize/1024/1024)+"兆！</li><br>";
		    e1.printStackTrace();
		}
		catch(Exception e2){
			k=-1;
		    message="<li>文件上传失败！</li><br>";
		    e2.printStackTrace();
		}catch(java.lang.OutOfMemoryError e3){
			k=-1;
			message="<li>你上传的文件太大！</li><br>";
			e3.printStackTrace();
		}
		if(k<=0){
			message+="<a href='javascript:window.history.go(-1)'>>> 返回重试</a><br>";
			message+="<a href='"+request.getContextPath()+"/visit/topic/a/view?topicId="+topicId+"' target='_parent'>>> 返回主题</a>";
		}
        else{
        	new TopicDao().updateTopicAttachment("1",topicId);
        	message+="<a href='javascript:window.history.go(-1)'>>> 继续上传</a><br>";
        	message+="<a href='"+request.getContextPath()+"/visit/topic/a/view?topicId="+topicId+"' target='_parent'>>> 返回主题</a>";
        }		
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher(getInitParameter("uploadresult"));
		rd.forward(request,response);
	}	
	private void downloadrun(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		Integer attachmentid=StringHandler.strToint(request.getParameter("attachmentid"));
		if(attachmentid==null){
		    request.setAttribute("message","<li>附件ID值错误！</li><br><a href='javascript:window.history.go(-1)'>返回</a>");
		    RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
		    rd.forward(request,response);
		}
		else{
			try{				
				AttachmentDao attachmentDao=new AttachmentDao();
				AttachmentBean attachmentsingle=attachmentDao.getAttachmentSingle(attachmentid);
				attachmentDao.closed();
				if(attachmentsingle==null){
					request.setAttribute("message","<li>要下载的文件不存在或已经被删除！</li><br><a href='javascript:window.history.go(-1)'>返回</a>");
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
						request.setAttribute("message","<li>要下载的文件不存在或已经被删除！</li><br><a href='javascript:window.history.go(-1)'>返回</a>");
						RequestDispatcher rd=request.getRequestDispatcher(RUNFAIL);
					    rd.forward(request,response);						
					}					
				}			    
			}catch(Exception e){
			    e.printStackTrace();
			    request.setAttribute("message","<li>文件下载失败！</li><br><a href='javascript:window.history.go(-1)'>返回</a>");
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
			message="<li>附件ID值错误！</li>";
		    message+="<br><a href='javascript:window.history.go(-1)'>返回</a>";
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
			message="<li>输入的管理员密码错误，请输入当前登录用户密码！</li><br>";
			message+="<a href='javascript:window.history.go(-1)'>返回重试</a>";
			forward=RUNFAIL;
		}
		else{
			String savename=request.getParameter("savename");
			String filedir=getServletContext().getInitParameter("filedir");
			String deletefilename=filedir+savename;
			String destfilepathname=getServletContext().getRealPath(deletefilename);
			
			//删除磁盘文件
			MyFileHandler.deleteFile(new String[]{destfilepathname});
			
			//删除数据库中文件对应的信息
			Integer attachmentid=StringHandler.strToint(request.getParameter("attachmentid"));
			AttachmentDao attachmentDao=new AttachmentDao();
			int i=attachmentDao.deleteAttachment(attachmentid);
			if(i<=0){
				message="<li>磁盘中的文件已经成功删除，但在删除数据库中文件信息时失败！</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>返回</a>";
				forward=RUNFAIL;
			}
			else{
				Integer topicId=StringHandler.strToint(request.getParameter("topicId"));
				Integer replyId=StringHandler.strToint(request.getParameter("replyId"));

				forward=INDEXTEMP;
				request.setAttribute("mainPage",RUNSUCCESS);
				
				message="<li>删除附件成功！3秒后将自动转发到当前主题！</li>";
				String autoforward=request.getParameter("autoforward");			//自动转发到添加附件页面的路径
				request.setAttribute("autoforward",autoforward);
				
				if(topicId!=null&&replyId!=null){
					if(replyId==-1){							//查询主题是否还有附件
						boolean isexist=attachmentDao.isExistByTopic(topicId);
						if(!isexist)										//没有附件，将帖子的topic_attachment字段值设为"0"
							new TopicDao().updateTopicAttachment("0", topicId);
					}
					else{										//查询该回复帖是否还有附件
						boolean isexist=attachmentDao.isExistByReply(replyId);
						if(!isexist)										//没有附件，将回复帖的reply_attachment字段值设为"0"
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
