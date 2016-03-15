package com.action;

import java.util.List;
import org.apache.struts.action.*;
import com.dao.ScripDAO;
import com.model.ScripForm;
import javax.servlet.http.*;
import com.tools.MyPagination;
import com.tools.StringUtils;

public class ScripAction extends Action {
    private ScripDAO scripDAO = null;
    private StringUtils su=new StringUtils();
    MyPagination pagination=null;
    public ScripAction() {
        this.scripDAO = new ScripDAO();
       
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        String action =request.getParameter("action");
     //   System.out.println("\nscrip*********************action="+action);
        if("scripQuery".equals(action)){
            return scripQuery(mapping,form,request,response); 		 //获取全部的许愿字条
        }else if("scripAdd".equals(action)){
        	return scripAdd(mapping,form,request,response);			//保存许愿字条信息
        }else if("addHoldout".equals(action)){
        	return holdoutAdd(mapping,form,request,response);		//添加支持
        }else if("scripList".equals(action)){
        	return scripList(mapping,form,request,response);		//显示字条列表
        }else if("scrollScrip".equals(action)){
        	return scrollScrip(mapping,form,request,response);		//最新字条滚动显示
        }else{
        	request.setAttribute("error","操作失败！");
        	return mapping.findForward("error");
        }
    }
    public ActionForward scripQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	request.setAttribute("scripList",scripDAO.query(null));		//查询字条信息
    	return mapping.findForward("scripQuery");
    }
    public ActionForward scripAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	ScripForm scripForm=(ScripForm)form;
    	if(!"".equals(scripForm.getWishMan())){
    		scripForm.setWishMan(su.StringtoSql(su.toUTF8(scripForm.getWishMan())));
    		if(!"".equals(scripForm.getWellWisher())){
    			scripForm.setWellWisher(su.StringtoSql(su.toUTF8(scripForm.getWellWisher()))); 
    			if(!"".equals(scripForm.getContent())){
    				scripForm.setContent(su.StringtoSql(su.toUTF8(scripForm.getContent())));
    		    	String rtn=scripDAO.insert(scripForm);					//保存字条信息
    		    	request.setAttribute("returnValue",rtn);
    			}else{
    				request.setAttribute("returnValue","请输入字条内容！");	//保存提示信息
    			}
    		}else{
    			String rtn=scripDAO.insert(scripForm);						//保存字条信息
    			request.setAttribute("returnValue","请输入祝福者！");
    		}
     	}else{
    		request.setAttribute("returnValue","请输入祝福对象！");
    	}
    	System.out.println("祝福对象："+scripForm.getWishMan()+"祝福者："+scripForm.getWellWisher()+"字条颜色："+scripForm.getColor()+"心情图案："+scripForm.getFace()+"字条内容："+scripForm.getContent());
    	return mapping.findForward("scripAdd");
    }
    public ActionForward holdoutAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	int id=Integer.parseInt(request.getParameter("scripId"));		//获传递的字条ID号
    	String hits=scripDAO.holdoutAdd(id);							//将指定的字条的人气数加1
    	request.setAttribute("hits", hits);								//保存新的单击数
    	return mapping.findForward("holdoutAdd");
    }
    //查找字条列表
    public ActionForward scripList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	StringUtils su=new StringUtils();
    	String strPage=(String)request.getParameter("Page");
    	String f = request.getParameter("f"); // 获取查询字段
    	String key = su.toGBK(request.getParameter("key")); // 获取查询关键字
    	String condition="";
    	if(("".equals(f) ||null==f) || ("all".equals(f) && "".equals(key))){
    		condition="ORDER BY sendTime DESC";
    	}else if("all".equals(f) && !"".equals(key)){
    		condition="WHERE wishMan like '%"+key+"%' OR wellWisher like '%"+key+"%' OR content like '%"+key+"%' ORDER BY sendTime DESC";
    	}else{
    		condition="WHERE "+f+" like '%"+key+"%' ORDER BY sendTime DESC";
    	}
    	System.out.println("条件："+condition);
    	int Page=1;
    	List list=null;
    	if(strPage==null){
    		 pagination=new MyPagination();
    		list=scripDAO.query(condition);						//获取字条信息
    		int pagesize=5;										//指定每页显示的记录数
    		list=pagination.getInitPage(list,Page,pagesize);	//初始化分页信息
    		request.getSession().setAttribute("pagination",pagination);
    	}else{
    		pagination=(MyPagination)request.getSession().getAttribute("pagination");
    		Page=pagination.getPage(strPage);
    		list=pagination.getAppointPage(Page);				//获取指定页数据
    	}
    	request.setAttribute("scripList1",list);				//保存当前页的字条信息
    	request.setAttribute("Page",Page);						//保存的当前页码
    	request.setAttribute("f", f);
    	request.setAttribute("key", key);
    	return mapping.findForward("scripList"); 
    }
    public ActionForward scrollScrip(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	request.setAttribute("scrollScrip",scripDAO.queryTop());	//查询最新的10条字条信息
    	return mapping.findForward("scrollScrip");
    }
}
