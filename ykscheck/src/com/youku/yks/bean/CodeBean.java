package com.youku.yks.bean;

public class CodeBean {
	private int code;
	private String value;
	public CodeBean(int code, String value) {
		super();
		this.code = code;
		this.value = value;
	}
	public CodeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
