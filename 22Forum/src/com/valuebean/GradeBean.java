package com.valuebean;

public class GradeBean {
	private int gradeOrder;
	private String gradeName;
	private int gradeMinpoint;
	
	public GradeBean(){
		gradeOrder=0;
		gradeName="Ä°ÉúÈË";
		gradeMinpoint=0;
	}	
	public int getGradeOrder() {
		return gradeOrder;
	}
	public void setGradeOrder(int gradeOrder) {
		this.gradeOrder = gradeOrder;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public int getGradeMinpoint() {
		return gradeMinpoint;
	}
	public void setGradeMinpoint(int gradeMinpoint) {
		this.gradeMinpoint = gradeMinpoint;
	}
}
