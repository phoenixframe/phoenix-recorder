package com.youku.yks.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对用例类型进行操作的bean对象<br>
 * 
 * <em>开发时间：2014年7月28日 16:24
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 
 *
 */
public class CaseTypeBean {
	private int id;
	private int isDisabled;
	private int userid;
	private String typeName;
	private Date createTime;
	
	public CaseTypeBean(){
		
	}
	
	public CaseTypeBean(int id, int isDisabled, int userid ,String typeName, Date createTime) {
		super();
		this.id = id;
		this.isDisabled = isDisabled;
		this.userid = userid;
		this.typeName = typeName;
		this.createTime = createTime;
	}
	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getCreateTime() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(int isDisabled) {
		this.isDisabled = isDisabled;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
