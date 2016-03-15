package com.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeVerifyCode extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("image/jpeg");
		//禁止图片(验证码)缓存，这段缓存代码一定要加在这里，如果加在登录页面(visitlogin.jsp或adminlogin.jsp)中将起不到禁止缓存的作用
		response.addHeader("Pragma","no-cache");
	    response.addHeader("Cache-Control","no-cache");
	    response.addDateHeader("Expires",0);	
	    
		String source="A0B1C2D3E4F56G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z";
		int bwidth=100;															//图片背景宽度
		int bheight=25;															//图片背景高度
		int fsize=20;															//字体大小
		String mFont[] = {"Tahoma","Arial","Helvetica","sans-serif"};			//生成验证码的数据源
		BufferedImage image = new BufferedImage(bwidth, bheight,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();		
		g.setColor(getRandColor("shallow"));									//随机设置填充背景颜色
		g.fillRect(0,0,bwidth-1,bheight-1);										//填充背景		
		
		//画出背景中的线条
		for (int i = 0; i <20; i++) {
			int x1 = getRanNum(bwidth/2 - 1);
			int y1 = getRanNum(bheight - 1);
			int x2 = getRanNum(bwidth - 1);
			int y2 = getRanNum(bheight - 1);
			g.setFont(new Font(mFont[getRanNum(mFont.length-1)],Font.ITALIC,fsize));	//设置字体
			g.setColor(getRandColor("shallow"));										//设置字体颜色
			g.drawLine(x1, y1, x2 ,y2);
		}
		
		//生成随机5位的验证文字
		String sRand="";
		for(int i=0;i<5;i++){
			char select=source.charAt(getRanNum(source.length()-1));					//获取数据源中随机位置上的字符
			String str=String.valueOf(select);
			sRand+=str;
		}
		
		request.getSession().setAttribute("getverifycode",sRand);						//将生成的验证码保存到session中
		
		//将验证文字输出为图片
		g.setFont(new Font(mFont[getRanNum(mFont.length-1)],Font.ITALIC,fsize));		//设置字体
		g.setColor(getRandColor("deep"));												//设置字体颜色
		g.drawString(sRand,10,fsize);													//画出字符		
		g.dispose();
		ImageIO.write(image,"JPEG",response.getOutputStream());							//输出图片		
	}
	private Color getRandColor(String depth) {
		int r =0;
		int g =0;
		int b =0;
		
		if("deep".equals(depth)){
			r =getRanNum(100);
			g =getRanNum(100);
			b =getRanNum(100);
		}
		else{
			r =200+getRanNum(55);
			g =200+getRanNum(55);
			b =200+getRanNum(55);
		}	
		return new Color(r, g, b);
	}
	private int getRanNum(int limit){
		int len=String.valueOf(limit*10).length();
		double scope=Math.pow(10,len);
		int num=((int)(Math.random()*scope))%limit;
		return num;		
	}
}
