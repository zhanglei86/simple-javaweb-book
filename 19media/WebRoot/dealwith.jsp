k<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<jsp:directive.page import="com.wy.model.*" />
<jsp:directive.page import="com.wy.dao.*" />
<jsp:directive.page import="java.io.File" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>">

<%
	Integer sign = Integer.valueOf(request.getParameter("sign"));
	if (sign == 0) {
		String result = "";	
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			AdminDao adminDao = new AdminDao();
			AdminInfo info = adminDao.admin_queryPassword(account);
			if (null != info) {
		String password1 = info.getPassword();
		if (password.equals(password1)) {
			adminDao.admin_addVisit();
			info = adminDao.admin_queryPassword(account);
			session.setAttribute("info", info);
		} else {
			result = "���������������";
		}
			} else {
		result = "���˺Ų����ڣ�";
			}
		 

		if (result.equals("")) {
			response.sendRedirect("admin/mainPage.jsp");
		} else {
			out.print("<script language=javascript>alert('" + result
			+ "');history.go(-1);</script>");
		}
		

	}

	if (sign == 1) {
		session.invalidate();
		response.sendRedirect("index.jsp");
	}
	if (sign == 2) {
%>
<jsp:useBean id="user" class="com.wy.model.UserInfo" scope="request" />
<jsp:setProperty property="*" name="user" />
<%
		UserDao userDao = new UserDao();
		boolean flag = userDao.user_add(user);
		if (flag) {
			session.setAttribute("username", user.getUser_name());
			response.sendRedirect("index.jsp");
		} else {
			out
			.print("<script language=javascript>alert('��������û����ظ������������룡');history.go(-1);</script>");
		}
	}
	if (sign == 3) {
		String code = request.getParameter("code");
		String rand = (String) session.getAttribute("rand");
		String result = "";
		if (code.equals(rand)) {
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			UserDao userDao = new UserDao();
			UserInfo userInfo = userDao.user_queryOne(account);
			if (null != userInfo) {
		if (password.equals(userInfo.getUser_pswd())) {
			userDao.user_addHit(account);
			session.setAttribute("username", userInfo
			.getUser_name());
		} else {
			result = "����������벻��ȷ�����������룡";
		}
			} else {
		result = "��������û��������ڣ������������룡";
			}
		} else {
			result = "���������֤�����󣡣����������룡";
		}

		if (result.equals(""))
			response.sendRedirect("index.jsp");
		else
			out.print("<script language=javascript>alert('" + result
			+ "');history.go(-1);</script>");

	}

	if (sign == 4) {
%>
<jsp:useBean id="mediaRInfo" class="com.wy.model.MediaRInfo"
	scope="request" />
<jsp:setProperty property="*" name="mediaRInfo" />
<%
		MediaRDao mediaRDao = new MediaRDao();
		mediaRDao.media_add(mediaRInfo);
		out
		.print("<script language=javascript>window.location.href='media_show.jsp?id="
				+ mediaRInfo.getMediaR_rootId() + "';</script>");
	}

	if (sign == 5) {
		UserDao userDao = new UserDao();
		MediaRDao mediaRDao = new MediaRDao();
		Integer id = Integer.valueOf(request.getParameter("id"));
		String username = request.getParameter("username");
		username = new String(username.getBytes("ISO8859_1"), "GBK");
		mediaRDao.media_deleteAllUser(username);
		userDao.user_delete(id);
		out
		.print("<script language=javascript>alert('����������벻��ȷ�����������룡');window.location.href='admin/user_query.jsp';</script>");
	}

	if (sign == 6) {
		Integer id = Integer.valueOf(request.getParameter("id"));
		MediaRDao mediaRDao = new MediaRDao();
		mediaRDao.media_deleteAllId(id);
		MediaDao mediaDao = new MediaDao();
		MediaInfo mediaInfo = mediaDao.media_query(id);
		String media_pic = mediaInfo.getMediaPic();
		media_pic=request.getRealPath("/"+media_pic);
		File file1 = new File(media_pic);
		if (file1.exists()) {
			file1.delete();
		}
		String media_src = mediaInfo.getMediaSrc();
		media_src=request.getRealPath("/"+media_src);
		File file2 = new File(media_src);
		if (file2.exists()) {
			file2.delete();
		}
		mediaDao.media_delete(id);
		out
		.print("<script language=javascript>alert('�����ɹ���');window.location.href='admin/mainPage.jsp';</script>");

	}
	if (sign == 7) {
		Integer id = Integer.valueOf(request.getParameter("id"));
		MediaRDao mediaRDao = new MediaRDao();
		mediaRDao.media_delete(id);
		out
		.print("<script language=javascript>alert('�����ɹ���');window.location.href='admin/media_queryOne.jsp?id="
				+ request.getParameter("media_id")
				+ "';</script>");
	}
%>




