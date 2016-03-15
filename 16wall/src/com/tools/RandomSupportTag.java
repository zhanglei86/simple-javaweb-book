package com.tools;

import java.util.Random;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class RandomSupportTag extends TagSupport {
	private static final long serialVersionUID=1L;
	private int n=0;		//生成的随机数的范围
	private int base=0;		//生成的随机数再加上的固定值，目的是生成一个大于某个值的随机数
	public int getN(){
		return n;
	}
	public void setN(int n){
		this.n=n;
	}
	public int getBase(){
		return base;
	}
	public void setBase(int base){
		this.base=base;
	}	
	public int doStartTag() throws JspException{
		JspWriter out=pageContext.getOut();
		try{
			Random random = new Random();
			int num=random.nextInt(n);
			//注意：此处不能应用out.pritnln()实现
			out.print(num+base);	//输出一个随机数
		}catch(Exception e){
			System.out.println("生成随机数时出现的异常："+e.getMessage());
		}
		return(SKIP_BODY);	//返回SKIP_BODY常量，表示将不对标签体进行处理
	}
}
