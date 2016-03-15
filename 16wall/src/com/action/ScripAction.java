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
            return scripQuery(mapping,form,request,response); 		 //��ȡȫ������Ը����
        }else if("scripAdd".equals(action)){
        	return scripAdd(mapping,form,request,response);			//������Ը������Ϣ
        }else if("addHoldout".equals(action)){
        	return holdoutAdd(mapping,form,request,response);		//���֧��
        }else if("scripList".equals(action)){
        	return scripList(mapping,form,request,response);		//��ʾ�����б�
        }else if("scrollScrip".equals(action)){
        	return scrollScrip(mapping,form,request,response);		//��������������ʾ
        }else{
        	request.setAttribute("error","����ʧ�ܣ�");
        	return mapping.findForward("error");
        }
    }
    public ActionForward scripQuery(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	request.setAttribute("scripList",scripDAO.query(null));		//��ѯ������Ϣ
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
    		    	String rtn=scripDAO.insert(scripForm);					//����������Ϣ
    		    	request.setAttribute("returnValue",rtn);
    			}else{
    				request.setAttribute("returnValue","�������������ݣ�");	//������ʾ��Ϣ
    			}
    		}else{
    			String rtn=scripDAO.insert(scripForm);						//����������Ϣ
    			request.setAttribute("returnValue","������ף���ߣ�");
    		}
     	}else{
    		request.setAttribute("returnValue","������ף������");
    	}
    	System.out.println("ף������"+scripForm.getWishMan()+"ף���ߣ�"+scripForm.getWellWisher()+"������ɫ��"+scripForm.getColor()+"����ͼ����"+scripForm.getFace()+"�������ݣ�"+scripForm.getContent());
    	return mapping.findForward("scripAdd");
    }
    public ActionForward holdoutAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	int id=Integer.parseInt(request.getParameter("scripId"));		//�񴫵ݵ�����ID��
    	String hits=scripDAO.holdoutAdd(id);							//��ָ������������������1
    	request.setAttribute("hits", hits);								//�����µĵ�����
    	return mapping.findForward("holdoutAdd");
    }
    //���������б�
    public ActionForward scripList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	StringUtils su=new StringUtils();
    	String strPage=(String)request.getParameter("Page");
    	String f = request.getParameter("f"); // ��ȡ��ѯ�ֶ�
    	String key = su.toGBK(request.getParameter("key")); // ��ȡ��ѯ�ؼ���
    	String condition="";
    	if(("".equals(f) ||null==f) || ("all".equals(f) && "".equals(key))){
    		condition="ORDER BY sendTime DESC";
    	}else if("all".equals(f) && !"".equals(key)){
    		condition="WHERE wishMan like '%"+key+"%' OR wellWisher like '%"+key+"%' OR content like '%"+key+"%' ORDER BY sendTime DESC";
    	}else{
    		condition="WHERE "+f+" like '%"+key+"%' ORDER BY sendTime DESC";
    	}
    	System.out.println("������"+condition);
    	int Page=1;
    	List list=null;
    	if(strPage==null){
    		 pagination=new MyPagination();
    		list=scripDAO.query(condition);						//��ȡ������Ϣ
    		int pagesize=5;										//ָ��ÿҳ��ʾ�ļ�¼��
    		list=pagination.getInitPage(list,Page,pagesize);	//��ʼ����ҳ��Ϣ
    		request.getSession().setAttribute("pagination",pagination);
    	}else{
    		pagination=(MyPagination)request.getSession().getAttribute("pagination");
    		Page=pagination.getPage(strPage);
    		list=pagination.getAppointPage(Page);				//��ȡָ��ҳ����
    	}
    	request.setAttribute("scripList1",list);				//���浱ǰҳ��������Ϣ
    	request.setAttribute("Page",Page);						//����ĵ�ǰҳ��
    	request.setAttribute("f", f);
    	request.setAttribute("key", key);
    	return mapping.findForward("scripList"); 
    }
    public ActionForward scrollScrip(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response){
    	request.setAttribute("scrollScrip",scripDAO.queryTop());	//��ѯ���µ�10��������Ϣ
    	return mapping.findForward("scrollScrip");
    }
}
