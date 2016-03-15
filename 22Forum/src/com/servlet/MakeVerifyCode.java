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
		//��ֹͼƬ(��֤��)���棬��λ������һ��Ҫ�������������ڵ�¼ҳ��(visitlogin.jsp��adminlogin.jsp)�н��𲻵���ֹ���������
		response.addHeader("Pragma","no-cache");
	    response.addHeader("Cache-Control","no-cache");
	    response.addDateHeader("Expires",0);	
	    
		String source="A0B1C2D3E4F56G7H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z";
		int bwidth=100;															//ͼƬ�������
		int bheight=25;															//ͼƬ�����߶�
		int fsize=20;															//�����С
		String mFont[] = {"Tahoma","Arial","Helvetica","sans-serif"};			//������֤�������Դ
		BufferedImage image = new BufferedImage(bwidth, bheight,BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();		
		g.setColor(getRandColor("shallow"));									//���������䱳����ɫ
		g.fillRect(0,0,bwidth-1,bheight-1);										//��䱳��		
		
		//���������е�����
		for (int i = 0; i <20; i++) {
			int x1 = getRanNum(bwidth/2 - 1);
			int y1 = getRanNum(bheight - 1);
			int x2 = getRanNum(bwidth - 1);
			int y2 = getRanNum(bheight - 1);
			g.setFont(new Font(mFont[getRanNum(mFont.length-1)],Font.ITALIC,fsize));	//��������
			g.setColor(getRandColor("shallow"));										//����������ɫ
			g.drawLine(x1, y1, x2 ,y2);
		}
		
		//�������5λ����֤����
		String sRand="";
		for(int i=0;i<5;i++){
			char select=source.charAt(getRanNum(source.length()-1));					//��ȡ����Դ�����λ���ϵ��ַ�
			String str=String.valueOf(select);
			sRand+=str;
		}
		
		request.getSession().setAttribute("getverifycode",sRand);						//�����ɵ���֤�뱣�浽session��
		
		//����֤�������ΪͼƬ
		g.setFont(new Font(mFont[getRanNum(mFont.length-1)],Font.ITALIC,fsize));		//��������
		g.setColor(getRandColor("deep"));												//����������ɫ
		g.drawString(sRand,10,fsize);													//�����ַ�		
		g.dispose();
		ImageIO.write(image,"JPEG",response.getOutputStream());							//���ͼƬ		
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
