package com.tools;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PictureCheckCode extends HttpServlet {
	private Font mFont = new Font("华文宋体", Font.BOLD, 24);

	public PictureCheckCode() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void init() throws ServletException {
		// Put your code here
		super.init();
	}

	Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应是图片
		response.setContentType("image/jpeg");
		int width =160;
		int height = 45;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Graphics2D g2d=(Graphics2D)g;
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width , height);

		//画一条折线		
		BasicStroke bs=new BasicStroke(2f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
		g2d.setStroke(bs);
		g.setColor(Color.DARK_GRAY);
		int[] xPoints=new int[3];
		int[] yPoints=new int[3];
		for(int j=0;j<3;j++){
			xPoints[j]=random.nextInt(width - 1);
			yPoints[j]=random.nextInt(height - 1);
		}
		g.drawPolyline(xPoints, yPoints,3);
		g.setFont(mFont);		
		String sRand="";
		int itmp=0;
		//输出随机的验证文字
		for(int i=0;i<4;i++){
			if(random.nextInt(2)==1){
				itmp=random.nextInt(26)+65;	//生成A~Z的字母
			}else{
				itmp=random.nextInt(10)+48;	//生成0~9的数字
			}
			char ctmp=(char)itmp;
			sRand+=String.valueOf(ctmp);
			Color color=new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110));
			g.setColor(color);
			/****随机缩放文字并将文字旋转指定角度**/
			//将文字旋转指定角度
			Graphics2D g2d_word=(Graphics2D)g;
			AffineTransform trans=new AffineTransform();
			trans.rotate(random.nextInt(45)*3.14/180,15*i+10,7);
			//缩放文字
			float scaleSize=random.nextFloat()+0.5f;
			if(scaleSize<0.8 || scaleSize>1.1f) scaleSize=1f;
			trans.scale(scaleSize, scaleSize);
			g2d_word.setTransform(trans);
			/************************/
			g.drawString(String.valueOf(ctmp),25*i+20,15);

		}
		//将生成的验证码保存到Session中
		HttpSession session=request.getSession(true);
		session.setAttribute("randCheckCode",sRand);
		g.dispose();
		ImageIO.write(image,"JPEG",response.getOutputStream());
	}

}
