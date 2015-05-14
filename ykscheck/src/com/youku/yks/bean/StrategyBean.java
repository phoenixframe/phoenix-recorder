package com.youku.yks.bean;

/**
 * 对并发测试执行策略数据设置的Bean
 * @author mengfeiyang
 *
 */
public class StrategyBean {
	private int id;
	private int userId;
	private String clientIP;
	private String startTime;
	private String url;
	private int Vusers;
	private String runType;
	private String runTimeSet;
	private int IterationSet;
	private int alternation;
	private String checkpoint;
	private String expectValue;	
	private String maxTimeout;
	private String remark;
	
	public StrategyBean(int id, int userId, String clientIP, String startTime, String url,
			int vusers, String runType, String runTimeSet, int iterationSet,
			int alternation, String checkpoint, String expectValue, String maxTimeout, String remark) {
		super();
		this.id = id;
		this.userId = userId;
		this.clientIP = clientIP;
		this.startTime = startTime;
		this.url = url;
		Vusers = vusers;
		this.runType = runType;
		this.runTimeSet = runTimeSet;
		IterationSet = iterationSet;
		this.alternation = alternation;
		this.checkpoint = checkpoint;
		this.expectValue = expectValue;
		this.maxTimeout = maxTimeout;
		this.remark = remark;
	}	

	@Override
	public String toString() {
		return "StrategyBean [id=" + id + ", userId=" + userId + ", clientIP="
				+ clientIP + ", startTime=" + startTime + ", url=" + url
				+ ", Vusers=" + Vusers + ", runType=" + runType
				+ ", runTimeSet=" + runTimeSet + ", IterationSet="
				+ IterationSet + ", alternation=" + alternation
				+ ", checkpoint=" + checkpoint + ", expectValue=" + expectValue
				+ ", maxTimeout=" + maxTimeout + ", remark=" + remark + "]";
	}

	public StrategyBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getVusers() {
		return Vusers;
	}
	public void setVusers(int vusers) {
		Vusers = vusers;
	}
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	public String getRunTimeSet() {
		return runTimeSet;
	}
	public void setRunTimeSet(String runTimeSet) {
		this.runTimeSet = runTimeSet;
	}
	public int getIterationSet() {
		return IterationSet;
	}
	public void setIterationSet(int iterationSet) {
		IterationSet = iterationSet;
	}
	public int getAlternation() {
		return alternation;
	}
	public void setAlternation(int alternation) {
		this.alternation = alternation;
	}
	public String getCheckpoint() {
		return checkpoint;
	}
	public void setCheckpoint(String checkpoint) {
		this.checkpoint = checkpoint;
	}
	public String getExpectValue() {
		return expectValue;
	}
	public void setExpectValue(String expectValue) {
		this.expectValue = expectValue;
	}

	public String getMaxTimeout() {
		return maxTimeout;
	}

	public void setMaxTimeout(String maxTimeout) {
		this.maxTimeout = maxTimeout;
	}
	
}
