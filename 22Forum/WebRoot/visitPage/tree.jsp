<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.valuebean.*" %>
<%@ page import="java.util.*"%>
<%
	String webname=request.getContextPath();

	//���ɵ�ǰλ����״�˵�
	String forumicon="<img src='"+webname+"/images/icon/forum.gif'>&nbsp;&nbsp;";
	String categoryicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/category.gif'>&nbsp;&nbsp;";
	String boardicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/board.gif'>&nbsp;&nbsp;";
	String topicicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/topic.gif'>&nbsp;&nbsp;";
	String color="red";	
	String tree=forumicon+"<a href='"+webname+"/index'>������̳</a><br>";				//λ����״�˵���Ĭ��ֵ(���ڵ�)
	
	String[] subPaths=(String[])request.getAttribute("subPaths");
	if(subPaths!=null&&subPaths.length!=0){
		String way=subPaths[0];						//����ǰ��̨�ı�ʶ
		String module=subPaths[1];					//����ģ��ı�ʶ
		String operate=subPaths[3];					//���ֲ����ı�ʶ��
	
		if("visit".equals(way)){
			if("myself".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/visit/myself/a/index'>��������</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("mycollect".equals(operate))
					tree+=boardicon+"<font color='red'>�ҵ��ղؼУ�</font>";
				else if("editmyinfoview".equals(operate))
					tree+=boardicon+"<font color='red'>�༭��������</font>";
				else if("editmyinforun".equals(operate))
					tree+=boardicon+"<font color='red'>�༭�������ϳɹ���</font>";
				else if("deletemycollect".equals(operate))
					tree+=boardicon+"<font color='red'>ɾ���ղ����ɹ���</font>";
				else if("mymessagebox".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>�ҵ����䣡</font>";
				else if("sendmessageview".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>�ҵ����䣡</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>������Ϣ��</font>";
				}
				else if("sendmessagerun".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>�ҵ����䣡</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>������Ϣ�ɹ���</font>";
				}
				else if("viewmymessage".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>�ҵ����䣡</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>�鿴��Ϣ��</font>";
				}
				else if("deleteonemessage".equals(operate)||"deleteselectmessages".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>�ҵ����䣡</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>ɾ����Ϣ�ɹ���</font>";
				}
				else if("editmyiconview".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>����ͷ��</font>";
				else if("editmysignview".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>���¸���ǩ����</font>";
				else if("editmypswdview".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>�������룡</font>";
				else if("cancelautologin".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>ȡ���Զ���¼�ɹ���</font>";
				else if("listmyposttopic".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>�ҷ�������ӣ�</font>";
				else if("listmyreplytopic".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>�һظ������ӣ�</font>";
			}
			else if("user".equals(module)){
				tree+=categoryicon+"<font color='"+color+"'>��Ա����</font><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("viewmember".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>�鿴��Ա���ϣ�</font>";
				else if("listmemberposttopic".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>�鿴��Ա��������£�</font>";				
			}
			else if("category".equals(module)){
				if("onelist".equals(operate)){								//�鿴ĳһ�����Ĳ���
					CategoryBean category=(CategoryBean)request.getAttribute("category");
					tree+=categoryicon+"<font color='"+color+"'>���ࣺ"+category.getCategoryName()+"</font>";
				}
			}
			else{ 
				Integer visitcategoryId=(Integer)session.getAttribute("visitcategory");
				Integer visitboardId=(Integer)session.getAttribute("visitboard");
			
				String visitcategoryName="";
				String visitboardName="";
				if(visitcategoryId!=null&&visitboardId!=null){
					if(visitcategoryId>0&&visitboardId>0){
						Map categorys=(Map)session.getAttribute("categorys");
						Map boards=(Map)session.getAttribute("boards");
						if((categorys!=null&&categorys.size()!=0)&&(boards!=null&&boards.size()!=0)){
							visitcategoryName=(String)categorys.get(visitcategoryId);
							visitboardName=(String)(((Map)boards.get(visitcategoryId)).get(visitboardId));
							tree+=categoryicon+"���ࣺ<a href='"+webname+"/visit/category/a/onelist?categoryId="+visitcategoryId+"'>"+visitcategoryName+"</a><br>";
							tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}						
					}
				}			
				if("board".equals(module)){
					if("listspecialtopic".equals(operate))
						tree+=boardicon+"<font color='"+color+"'>��飺"+visitboardName+"</font>";
					else if("postview".equals(operate)){
						tree+=boardicon+"��飺<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
						tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tree+=topicicon+"<font color='"+color+"'>�������ӣ�</font>";
					}
					else if("postrun".equals(operate)){
						tree+=boardicon+"��飺<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
						tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tree+=topicicon+"<font color='"+color+"'>�������ӳɹ���</font>";
					}
				}
				else if("topic".equals(module)){
					if("listuncheck".equals(operate)){
						tree=forumicon+"<a href='"+webname+"/index'>������̳</a><br>";
						tree+=categoryicon+"�鿴��������ӣ�";
					}
					else{
						TopicBean topic=(TopicBean)request.getAttribute("topicsingle");
						tree+=boardicon+"��飺<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
						tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						if("view".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�鿴���ӣ�"+topic.getTopicTitle()+"</font>";
						else if("replyview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�ظ������ӣ�"+topic.getTopicTitle()+"</font>";
						else if("replyrun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�ظ����ӳɹ���</font>";
						else if("modifyview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�༭���ӣ�"+topic.getTopicTitle()+"</font>";
						else if("modifyrun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�༭���ӳɹ���</font>";
						else if("changetypeview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�޸��������ͣ�"+topic.getTopicTitle()+"</font>";
						else if("changetyperun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�޸��������ͳɹ���</font>";
						else if("changestatusview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�޸�����״̬��"+topic.getTopicTitle()+"</font>";
						else if("changestatusrun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�޸�����״̬�ɹ���</font>";
						else if("moveview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�ƶ����ӣ�"+topic.getTopicTitle()+"</font>";
						else if("moverun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�ƶ����ӳɹ���</font>";
						else if("collecttopic".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>�ղ����ӳɹ���</font>";
						else if("movefirst".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>��ǰ���ӳɹ���</font>";
						else if("deleteview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>ɾ�����ӣ�"+topic.getTopicTitle()+"</font>";
						else if("deleterun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>ɾ�����ӳɹ���</font>";				
					}
				}	
				else if("reply".equals(module)){
					tree+=boardicon+"��飺<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				
					ReplyBean reply=(ReplyBean)request.getAttribute("replysingle");
					if("deleteview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>ɾ���ظ���"+reply.getReplyTitle()+"</font>";
					else if("deleterun".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>ɾ���ظ��ɹ���</font>";
				}
				else if("attachment".equals(module)){
					tree+=boardicon+"��飺<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					if("uploadview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>��Ӹ�����</font>";
				}
				else if("placard".equals(module)){
					tree+=boardicon+"��飺<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					if("view".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>�鿴������Ϣ��</font>";
					else if("postplacardview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>����������Ϣ��</font>";
					else if("postplacardrun".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>����������Ϣ�ɹ���</font>";
					else if("deleteplacardview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>ɾ��������Ϣ��</font>";
					else if("deleteplacardrun".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>ɾ��������Ϣ�ɹ���</font>";
				}
			}
		}
	}
	else{
		String operate=(String)request.getAttribute("operate");
		if("/regview".equals(operate)||"/checkmember".equals(operate))
			tree+=categoryicon+"�û�ע�ᣡ";
	}
%>
<table width="99%" border="0" cellpadding="8" cellspacing="0">
	<tr><td><%=tree%></td></tr>
</table>
