<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="com.valuebean.*" %>
<%@ page import="java.util.*"%>
<%
	String webname=request.getContextPath();

	//生成当前位置树状菜单
	String forumicon="<img src='"+webname+"/images/icon/forum.gif'>&nbsp;&nbsp;";
	String categoryicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/category.gif'>&nbsp;&nbsp;";
	String boardicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/board.gif'>&nbsp;&nbsp;";
	String topicicon="<img src='"+webname+"/images/icon/link.gif'><img src='"+webname+"/images/icon/topic.gif'>&nbsp;&nbsp;";
	String color="red";	
	String tree=forumicon+"<a href='"+webname+"/index'>随意论坛</a><br>";				//位置树状菜单的默认值(根节点)
	
	String[] subPaths=(String[])request.getAttribute("subPaths");
	if(subPaths!=null&&subPaths.length!=0){
		String way=subPaths[0];						//区分前后台的标识
		String module=subPaths[1];					//区分模块的标识
		String operate=subPaths[3];					//区分操作的标识　
	
		if("visit".equals(way)){
			if("myself".equals(module)){
				tree+=categoryicon+"<a href='"+webname+"/visit/myself/a/index'>个人资料</a><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("mycollect".equals(operate))
					tree+=boardicon+"<font color='red'>我的收藏夹！</font>";
				else if("editmyinfoview".equals(operate))
					tree+=boardicon+"<font color='red'>编辑个人资料</font>";
				else if("editmyinforun".equals(operate))
					tree+=boardicon+"<font color='red'>编辑个人资料成功！</font>";
				else if("deletemycollect".equals(operate))
					tree+=boardicon+"<font color='red'>删除收藏帖成功！</font>";
				else if("mymessagebox".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>我的信箱！</font>";
				else if("sendmessageview".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>我的信箱！</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>发送消息！</font>";
				}
				else if("sendmessagerun".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>我的信箱！</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>发送消息成功！</font>";
				}
				else if("viewmymessage".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>我的信箱！</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>查看消息！</font>";
				}
				else if("deleteonemessage".equals(operate)||"deleteselectmessages".equals(operate)){
					tree+=boardicon+"<a href='"+webname+"/visit/myself/a/mymessagebox'>我的信箱！</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					tree+=topicicon+"<font color='"+color+"'>删除消息成功！</font>";
				}
				else if("editmyiconview".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>更换头像！</font>";
				else if("editmysignview".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>更新个性签名！</font>";
				else if("editmypswdview".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>更改密码！</font>";
				else if("cancelautologin".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>取消自动登录成功！</font>";
				else if("listmyposttopic".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>我发表的帖子！</font>";
				else if("listmyreplytopic".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>我回复的帖子！</font>";
			}
			else if("user".equals(module)){
				tree+=categoryicon+"<font color='"+color+"'>会员操作</font><br>";
				tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				if("viewmember".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>查看会员资料！</font>";
				else if("listmemberposttopic".equals(operate))
					tree+=boardicon+"<font color='"+color+"'>查看会员发表的文章！</font>";				
			}
			else if("category".equals(module)){
				if("onelist".equals(operate)){								//查看某一个类别的操作
					CategoryBean category=(CategoryBean)request.getAttribute("category");
					tree+=categoryicon+"<font color='"+color+"'>分类："+category.getCategoryName()+"</font>";
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
							tree+=categoryicon+"分类：<a href='"+webname+"/visit/category/a/onelist?categoryId="+visitcategoryId+"'>"+visitcategoryName+"</a><br>";
							tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}						
					}
				}			
				if("board".equals(module)){
					if("listspecialtopic".equals(operate))
						tree+=boardicon+"<font color='"+color+"'>版块："+visitboardName+"</font>";
					else if("postview".equals(operate)){
						tree+=boardicon+"版块：<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
						tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tree+=topicicon+"<font color='"+color+"'>发表帖子！</font>";
					}
					else if("postrun".equals(operate)){
						tree+=boardicon+"版块：<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
						tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						tree+=topicicon+"<font color='"+color+"'>发表帖子成功！</font>";
					}
				}
				else if("topic".equals(module)){
					if("listuncheck".equals(operate)){
						tree=forumicon+"<a href='"+webname+"/index'>随意论坛</a><br>";
						tree+=categoryicon+"查看待审核帖子！";
					}
					else{
						TopicBean topic=(TopicBean)request.getAttribute("topicsingle");
						tree+=boardicon+"版块：<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
						tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						if("view".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>查看帖子："+topic.getTopicTitle()+"</font>";
						else if("replyview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>回复该帖子："+topic.getTopicTitle()+"</font>";
						else if("replyrun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>回复帖子成功！</font>";
						else if("modifyview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>编辑帖子："+topic.getTopicTitle()+"</font>";
						else if("modifyrun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>编辑帖子成功！</font>";
						else if("changetypeview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>修改帖子类型："+topic.getTopicTitle()+"</font>";
						else if("changetyperun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>修改帖子类型成功！</font>";
						else if("changestatusview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>修改帖子状态："+topic.getTopicTitle()+"</font>";
						else if("changestatusrun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>修改帖子状态成功！</font>";
						else if("moveview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>移动帖子："+topic.getTopicTitle()+"</font>";
						else if("moverun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>移动帖子成功！</font>";
						else if("collecttopic".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>收藏帖子成功！</font>";
						else if("movefirst".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>提前帖子成功！</font>";
						else if("deleteview".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>删除帖子："+topic.getTopicTitle()+"</font>";
						else if("deleterun".equals(operate))
							tree+=topicicon+"<font color='"+color+"'>删除帖子成功！</font>";				
					}
				}	
				else if("reply".equals(module)){
					tree+=boardicon+"版块：<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				
					ReplyBean reply=(ReplyBean)request.getAttribute("replysingle");
					if("deleteview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>删除回复："+reply.getReplyTitle()+"</font>";
					else if("deleterun".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>删除回复成功！</font>";
				}
				else if("attachment".equals(module)){
					tree+=boardicon+"版块：<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					if("uploadview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>添加附件！</font>";
				}
				else if("placard".equals(module)){
					tree+=boardicon+"版块：<a href='"+webname+"/visit/board/a/listspecialtopic?boardId="+visitboardId+"'>"+visitboardName+"</a><br>";
					tree+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					if("view".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>查看公告信息！</font>";
					else if("postplacardview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>发布公告信息！</font>";
					else if("postplacardrun".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>发布公告信息成功！</font>";
					else if("deleteplacardview".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>删除公告信息！</font>";
					else if("deleteplacardrun".equals(operate))
						tree+=topicicon+"<font color='"+color+"'>删除公告信息成功！</font>";
				}
			}
		}
	}
	else{
		String operate=(String)request.getAttribute("operate");
		if("/regview".equals(operate)||"/checkmember".equals(operate))
			tree+=categoryicon+"用户注册！";
	}
%>
<table width="99%" border="0" cellpadding="8" cellspacing="0">
	<tr><td><%=tree%></td></tr>
</table>
