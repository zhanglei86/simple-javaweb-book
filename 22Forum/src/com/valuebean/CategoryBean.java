package com.valuebean;

import java.util.List;

import com.toolsbean.StringHandler;

public class CategoryBean {
	private int id;							//类别ID
	private String categoryName;			//类别名称
	private String categoryInfo;			//类别描述
	private int categoryOrder;				//类别排列次序
	private String categoryFoundTime;		//类别创建时间
	private List masters;					//类别的所有类主名称
	
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
