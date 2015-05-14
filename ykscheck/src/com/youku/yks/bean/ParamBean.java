package com.youku.yks.bean;

public class ParamBean {
	private int id;
	private int userId;
	private int interfaceId;
	private String paramName;
	private String paramType;
	private String paramValue;
	
	public ParamBean(String paramName,String paramValue){
		this.paramName = paramName;
		this.paramValue = paramValue;
	}
	
	public ParamBean(int id, int userId, int interfaceId, String paramName,
			String paramType, String paramValue) {
		super();
		this.id = id;
		this.userId = userId;
		this.interfaceId = interfaceId;
		this.paramName = paramName;
		this.paramType = paramType;
		this.paramValue = paramValue;
	}

	public ParamBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	

}
