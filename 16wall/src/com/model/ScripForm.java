package com.model;

import java.util.Date;
import org.apache.struts.action.ActionForm;

public class ScripForm extends ActionForm{
	public int id = 0;				//�������
	public String wishMan = "";		//ף������
	public String wellWisher = "";	//ף����
	public String content = "";		//��������
	public int color = 0;			//������ɫ
	public int face = 0;			//����ͼ��
	public Date sendTime = null;	//����ʱ��
	public int hits = 0;			//�����
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
