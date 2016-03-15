package com.valuebean;

public class PlacardBean {
	private int 		id;
	private int 		categoryId;
	private int 		boardId;
	private String 		placardTitle;
	private String 		placardContent;
	private String 		placardPostTime;
	private String 		placardType;
	private String 		placardAuthorName;
	private UserBean 	placardAuthor;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getPlacardTitle() {
		return placardTitle;
	}
	public void setPlacardTitle(String placardTitle) {
		this.placardTitle = placardTitle;
	}
	public String getPlacardContent() {
		return placardContent;
	}
	public void setPlacardContent(String placardContent) {
		this.placardContent = placardContent;
	}
	public String getPlacardPostTime() {
		return placardPostTime;
	}
	public void setPlacardPostTime(String placardPostTime) {
		this.placardPostTime = placardPostTime;
	}
	public String getPlacardType() {
		return placardType;
	}
	public void setPlacardType(String placardType) {
		this.placardType = placardType;
	}
	public String getPlacardAuthorName() {
		return placardAuthorName;
	}
	public void setPlacardAuthorName(String placardAuthorName) {
		this.placardAuthorName = placardAuthorName;
	}
	public UserBean getPlacardAuthor() {
		return placardAuthor;
	}
	public void setPlacardAuthor(UserBean placardAuthor) {
		this.placardAuthor = placardAuthor;
	}	
}
