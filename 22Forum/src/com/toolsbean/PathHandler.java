package com.toolsbean;

public class PathHandler {	
	public String[] dividePath(String servletPath,String pathInfo){
		String[] subPaths=null;
		if(servletPath!=null&&pathInfo!=null){
			String temp1=StringHandler.delBlank(servletPath.substring(1));										//去掉路径中的首字符“/”
			String temp2=StringHandler.delBlank(pathInfo.substring(1));
	
			String[] subtemp1=divideSub(temp1);
			String[] subtemp2=divideSub(temp2);
			
			if(subtemp1!=null&&subtemp2!=null){
				subPaths=new String[4];
				subPaths[0]=subtemp1[0];			//存储前、后操作的标识：前台-visit；后台-admin
				subPaths[1]=subtemp1[1];			//存储模块：如board表示版块模块、topic表示主题模块等
				subPaths[2]=subtemp2[0];			//存储权限标识：a-所有、b-自己以上、c-版主以上、
				subPaths[3]=subtemp2[1];			//存储操作：list－列表显示、add-添加				
			}							
		}
		return subPaths;
	}
	/**
	 * @功能：验证路径是否为"A/B"形式，并以"/"符号分割该路径
	 * @参数：temp为待操作的路径
	 * @返回：字符串数组，存储分割后的单元路径
	 */
	private String[] divideSub(String temp){
		int start=temp.indexOf("/");						//获取路径中第一个"/"符号位置
		int end=temp.lastIndexOf("/");						//获取路径中最后一个"/"符号位置
		if(start==end){										//若相等，则表示路径中只有一个"/"符号
			String[] subtemp=temp.split("/");				//以"/"符号分割路径
			if(subtemp.length==2&&!subtemp[0].equals("")&&!subtemp[1].equals(""))		//如果路径为"A/B"形式
				return subtemp;
		}
		return null;
	}
}
