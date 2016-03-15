package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDao;
import com.dao.CategoryDao;

public class IndexServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.getSession().setAttribute("activation",true);
		try {			
			//获取所有类型的ID和名称
			Map categorys=new CategoryDao().getCategoryMap();
			request.getSession().setAttribute("categorys",categorys);
			
			//依次获取某类别下所有版块的ID和名称
			if(categorys!=null&&categorys.size()!=0){
				Map boards=new HashMap();
				Iterator itc=categorys.keySet().iterator();
				while(itc.hasNext()){
					int categoryId=(Integer)itc.next();
					Map board=new BoardDao().getBoardMap(categoryId);
					boards.put(categoryId,board);
				}
				request.getSession().setAttribute("boards",boards);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath()+"/visit/category/a/alllist");
	}	
}
