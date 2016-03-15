package com.wy.form;

public class Net {
	private Integer id=-1;
	private String net_title="";
	private String net_content="";
	private String address="";
	private String snapAddress= "";
	public String getSnapAddress() {
		return snapAddress;
	}
	public void setSnapAddress(String snapAddress) {
		this.snapAddress = snapAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNet_content() {
		return net_content;
	}
	public void setNet_content(String net_content) {
		this.net_content = net_content;
	}
	public String getNet_title() {
		return net_title;
	}
	public void setNet_title(String net_title) {
		this.net_title = net_title;
	}

}
