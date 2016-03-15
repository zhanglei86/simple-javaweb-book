package com.wy.webiter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.wy.dao.ObjectDao;
import com.wy.model.ArticleInfo;
import com.wy.model.ReArticleInfo;
import com.wy.model.UserInfo;

public class ArticleAction extends ActionSupport implements
		ModelDriven<ArticleInfo>, ServletRequestAware {
	private String hql;

	private ArticleInfo articleInfo = new ArticleInfo();

	protected HttpServletRequest request;

	private ObjectDao<ArticleInfo> objectDao = null;

	String dateTimeFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
			.format(Calendar.getInstance().getTime());

	public ArticleInfo getModel() {
		return articleInfo;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	// 管理员登录后，对文章的详细查询
	public String admin_articleQueryOne() {
		objectDao = new ObjectDao<ArticleInfo>();
		hql = "select author from ArticleInfo group by author";
		List authorList = objectDao.queryListObject(hql);
		request.setAttribute("authorList", authorList);

		Integer id = Integer.valueOf(request.getParameter("id"));
		hql = "from ArticleInfo where id = " + id + "";
		articleInfo = objectDao.queryFrom(hql);
		if (null != request.getParameter("commend")) {
			if (articleInfo.getCommend().equals("否")) {
				articleInfo.setCommend("是");
			} else {
				articleInfo.setCommend("否");
			}
			objectDao.updateT(articleInfo);
		}
		articleInfo = objectDao.queryFrom(hql);
		request.setAttribute("articleInfo", articleInfo);
		return "admin_articleQueryOne";
	}

	// 管理员登录后，文章查询操作
	public String admin_articleQuery() {
		// 以下是对文章的全部查询
		hql = "from ArticleInfo";
		String account = request.getParameter("account");
		if (null != account) {
			hql = "from ArticleInfo where author = '" + account + "'";
			request.setAttribute("account", account);
		}
		objectDao = new ObjectDao<ArticleInfo>();
		List<ArticleInfo> list = objectDao.queryList(hql);
		int showNumber = 10;
		Integer count = 0;
		if (null != request.getParameter("count")) {
			count = Integer.valueOf(request.getParameter("count"));
		}
		list = objectDao.queryList(hql);
		int maxPage = list.size();
		if (maxPage % showNumber == 0) {
			maxPage = maxPage / showNumber;
		} else {
			maxPage = maxPage / showNumber + 1;
		}
		if (0 == count) {
			list = objectDao.queryList(hql, showNumber, count);
		} else {
			count--;
			list = objectDao.queryList(hql, showNumber, count * showNumber);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("maxPage", maxPage);
		// 文章所对应的发布人
		hql = "select author from ArticleInfo group by author";
		List authorList = objectDao.queryListObject(hql);
		request.setAttribute("authorList", authorList);
		return "admin_articleQuery";
	}

	// 前台文章的详细查询
	public String f_article_query() {
		// 文章的详细查询
		Integer id = Integer.valueOf(request.getParameter("id"));
		hql = "from ArticleInfo where id =" + id + "";
		objectDao = new ObjectDao<ArticleInfo>();
		
		
		articleInfo = objectDao.queryFrom(hql);
		
		String account = (String) request.getSession().getAttribute("account");

		if(null==account){	
			account=articleInfo.getAuthor();
			hql = "from UserInfo where account = '" + account + "'";
			ObjectDao<UserInfo>	objectDao1 = new ObjectDao<UserInfo>();
			UserInfo userInfo = objectDao1.queryFrom(hql);
			request.getSession().setAttribute("userInfo", userInfo);
		
		}
		if (null == request.getParameter("count")) {
			if (!articleInfo.getAuthor().equals(account)) {
				articleInfo.setVisit(articleInfo.getVisit() + 1);
				objectDao.updateT(articleInfo);
			}
		}
		request.setAttribute("articleInfo", articleInfo);
		// 文章回复内容的详细查询
		hql = "from ReArticleInfo where re_id=" + id + " order by id desc";	
		ObjectDao<ReArticleInfo> re_objectDao = new ObjectDao<ReArticleInfo>();
		List<ReArticleInfo> list = null;
		int showNumber = 3;
		Integer count = 0;
		if (null != request.getParameter("count")) {
			count = Integer.valueOf(request.getParameter("count"));
		}
		list = re_objectDao.queryList(hql);
		if(list.size()!=0){
		int maxPage = list.size();
		if (maxPage % showNumber == 0) {
			maxPage = maxPage / showNumber;
		} else {
			maxPage = maxPage / showNumber + 1;
		}
		if (0 == count) {
			list = re_objectDao.queryList(hql, showNumber, count);
		} else {
			count--;
			list = re_objectDao.queryList(hql, showNumber, count * showNumber);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("maxPage", maxPage);
		}	
		return "f_article_query";
	}

	public String article_forwardUpdate() {
		objectDao = new ObjectDao<ArticleInfo>();
		Integer id = Integer.valueOf(request.getParameter("id"));
		hql = "from ArticleInfo where id =" + id + "";
		articleInfo = objectDao.queryFrom(hql);
		request.setAttribute("articleInfo", articleInfo);
		return "article_forwardUpdate";
	}

	public void validateArticle_update() {
		if (articleInfo.getTitle().equals("")) {
			this.addFieldError("title", "请输入文章标题！<br><br>");
		}
		if (articleInfo.getContent().equals("")) {
			this.addFieldError("content", "请输入文章内容！");
		}

	}

	// 文章修改操作
	public String article_update() {
		objectDao = new ObjectDao<ArticleInfo>();
		this.articleInfo.setSendTime(this.dateTimeFormat);
		String result = "修改文章成功！";
		if (!objectDao.updateT(articleInfo)) {
			result = "修改文章失败！";
		}
		request.setAttribute("result", result);
		request.setAttribute("sign", "10");
		return "operationArticle";

	}

	// 文章删除操作
	public String article_delete() {
		Integer id = Integer.valueOf(request.getParameter("id"));
		hql = "from ArticleInfo where id =" + id + "";
		objectDao = new ObjectDao<ArticleInfo>();
		articleInfo = objectDao.queryFrom(hql);
		String hql1 = "from ReArticleInfo where re_id =" + id + "";
		ObjectDao<ReArticleInfo> objectDao1 = new ObjectDao<ReArticleInfo>();
		List<ReArticleInfo> list = objectDao1.queryList(hql1);
		for (ReArticleInfo reArticleInfo : list) {
			objectDao1.deleteT(reArticleInfo);
		}
		boolean flag = objectDao.deleteT(articleInfo);
		String result = "删除文章成功！";
		if (!flag) {
			result = "删除文章失败！";
		}
		request.setAttribute("result", result);
		request.setAttribute("sign", "10");
		return "operationArticle";
	}

	// 文章详细查询
	public String article_queryContent() {
		Integer id = Integer.valueOf(request.getParameter("id"));
		hql = "from ArticleInfo where id =" + id + "";
		objectDao = new ObjectDao<ArticleInfo>();
		articleInfo = objectDao.queryFrom(hql);
		request.setAttribute("form", articleInfo);
		return SUCCESS;
	}

	// 文章查询操作
	public String article_query() {
		String account = (String) request.getSession().getAttribute("account");
		hql = "from ArticleInfo where author='" + account
				+ "' order by id desc";
		objectDao = new ObjectDao<ArticleInfo>();
		List<ArticleInfo> list = null;
		int showNumber = 15;
		Integer count = 0;
		if (null != request.getParameter("count")) {
			count = Integer.valueOf(request.getParameter("count"));
		}
		list = objectDao.queryList(hql);
		int maxPage = list.size();
		if (maxPage % showNumber == 0) {
			maxPage = maxPage / showNumber;
		} else {
			maxPage = maxPage / showNumber + 1;
		}
		if (0 == count) {
			list = objectDao.queryList(hql, showNumber, count);
		} else {
			count--;
			list = objectDao.queryList(hql, showNumber, count * showNumber);
		}
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("maxPage", maxPage);
		return SUCCESS;
	}

	// 文章表单的校验
	public void validateArticle_add() {
		if (articleInfo.getTitle().equals("")) {
			this.addFieldError("title", "请输入文章标题！<br><br>");
		}
		if (articleInfo.getContent().equals("")) {
			this.addFieldError("content", "请输入文章内容！");
		}

	}

	// 文章添加
	public String article_add() {
		objectDao = new ObjectDao<ArticleInfo>();
		this.articleInfo.setSendTime(this.dateTimeFormat);
		if (objectDao.saveT(articleInfo)) {
			request.setAttribute("result", "添加文章成功！");
		} else {
			request.setAttribute("result", "添加文章失败！");
		}
		return SUCCESS;
	}

}
