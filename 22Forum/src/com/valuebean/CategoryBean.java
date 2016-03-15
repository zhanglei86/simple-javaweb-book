package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class CategoryBean {
	private int id;							//���ID
	private String categoryName;			//�������
	private String categoryInfo;			//�������
	private int categoryOrder;				//������д���
	private String categoryFoundTime;		//��𴴽�ʱ��
	private List masters;					//����������������
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategoryName() {
		return StringHandler.changehtml(categoryName);
	}
	public String getCategoryNameForEdit() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryInfo() {
		return StringHandler.changehtml(categoryInfo);
	}
	public String getCategoryInfoForEdit() {
		return categoryInfo;
	}
	public void setCategoryInfo(String categoryInfo) {
		this.categoryInfo = categoryInfo;
	}
	public int getCategoryOrder() {
		return categoryOrder;
	}
	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}
	public String getCategoryFoundTime() {
		return categoryFoundTime;
	}
	public void setCategoryFoundTime(String categoryFoundTime) {
		this.categoryFoundTime = categoryFoundTime;
	}
	public List getMasters() {
		return masters;
	}
	public void setMasters(List masters) {
		this.masters = masters;
	}	
}
