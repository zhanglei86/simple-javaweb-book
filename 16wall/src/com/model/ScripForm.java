package com.model;

import java.util.Date;
import org.apache.struts.action.ActionForm;

public class ScripForm extends ActionForm{
	public int id = 0;				//字条编号
	public String wishMan = "";		//祝福对象
	public String wellWisher = "";	//祝福者
	public String content = "";		//字条内容
	public int color = 0;			//字条颜色
	public int face = 0;			//心情图案
	public Date sendTime = null;	//发送时间
	public int hits = 0;			//点击数
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public String getWishMan(){
		return wishMan;
	}
	public void setWishMan(String wishMan){
		this.wishMan = wishMan;
	}
	
	public String getWellWisher(){
		return wellWisher;
	}
	public void setWellWisher(String wellWisher){
		this.wellWisher = wellWisher;
	}	
	
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	
	public int getColor(){
		return color;
	}
	public void setColor(int color){
		this.color = color;
	}
	
	public int getFace(){
		return face;
	}
	public void setFace(int face){
		this.face = face;
	}

	public Date getSendTime(){
		return sendTime;
	}
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	
	public int getHits(){
		return hits;
	}
	public void setHits(int hits){
		this.hits = hits;
	}	
}
