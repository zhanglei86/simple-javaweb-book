package com.wy.servlet;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.search.Hits;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wy.dao.Dao;
import com.wy.form.Word;

public class OperationServlet extends HttpServlet {

	private String info = "";
	private Dao dao = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		info = request.getParameter("info");
		if (info.equals("createSearch")) {
			this.createSearch(request, response);
		}
		if (info.equals("searchResult")) {
			this.searchResult(request, response);
		}
		if (info.equals("searchWordResult")) {
			this.searchWordResult(request, response);
		}

		if (info.equals("searchWordAll")) {
			this.searchWordAll(request, response);
		}

	}

	private void createSearch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRealPath("\\WEB-INF\\net");
		dao = new Dao();
		String result = "创建搜索引擎失败！";
		if (dao.writerinNet(path)) {
			result = "创建搜索引擎成功！";
			List list = dao.wordList(null);
			for (int i = 0; i < list.size(); i++) {
				Word word = (Word) list.get(i);
				Hits hits = dao.readinNet(word.getWord_name(), path);
				if (hits != null) {
					word.setWord_result(hits.length());
					dao.updateWordFomr(word);
				}
			}
		}
		request.setAttribute("result", result);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private void searchResult(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Long start = System.currentTimeMillis();
		String path = request.getRealPath("\\WEB-INF\\net");
		dao = new Dao();
		String queryString = request.getParameter("queryString");
		if (queryString == null || queryString.equals("")) {
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		} else {
			queryString = new String(queryString.getBytes("ISO8859_1"), "GBK");
			Hits hits = dao.readinNet(queryString, path);

			if (hits.length() != 0) {
				dao.addWordForm(queryString, hits.length());
			}
			Long end = System.currentTimeMillis();
			Long overTime = end - start;
			request.setAttribute("hits", hits);
			request.getSession().setAttribute("seach", queryString);
			request.getSession().setAttribute("overTime", overTime);
			request.getRequestDispatcher("showInformation.jsp").forward(
					request, response);
		}
	}

	private void searchWordResult(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dao = new Dao();
		request.setAttribute("list1", dao.wordList("desc"));
		request.setAttribute("list2", dao.wordList(null));
		request.getRequestDispatcher("showInWord.jsp").forward(request,
				response);
	}

	private void searchWordAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		dao = new Dao();
		request.setAttribute("list", dao.wordList(null));
		request.getRequestDispatcher("showInWordAll.jsp").forward(request,
				response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		doGet(request, response);
	}

}
