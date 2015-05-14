package com.youku.yks.bean;

/**
 * 对接口数据操作的Bean，该数据用于设置请求地址的主体
 * @author mengfeiyang
 *
 */
public class InterfaceBean {
	private int id;
	private int userId;
	private String _interface;
	
	public InterfaceBean(int id, int userId, String _interface) {
		super();
		this.id = id;
		this.userId = userId;
		this._interface = _interface;
	}
	public InterfaceBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String get_interface() {
		return _interface;
	}
	public void set_interface(String _interface) {
		this._interface = _interface;
	}
	
}
