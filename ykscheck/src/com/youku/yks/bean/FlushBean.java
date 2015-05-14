package com.youku.yks.bean;

public class FlushBean {
	private long VuserId;
	private long responseTime;
	private int TotalCount;
	private int CurrenCount;
	private int ThroughtBytes;
	private int ErrorCount;
	private int remainCount;
	private int SuccessCount;
	private String status;	
	private String ErrorInfo;
	private int ExceptionCount;
	
	
	public int getExceptionCount() {
		return ExceptionCount;
	}
	public void setExceptionCount(int exceptionCount) {
		ExceptionCount = exceptionCount;
	}
	public String getErrorInfo() {
		return ErrorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		ErrorInfo = errorInfo;
	}
	public int getSuccessCount() {
		return SuccessCount;
	}
	public void setSuccessCount(int successCount) {
		SuccessCount = successCount;
	}
	public int getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}
	public long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getVuserId() {
		return VuserId;
	}
	public void setVuserId(long vuserId) {
		VuserId = vuserId;
	}
	public int getTotalCount() {
		return TotalCount;
	}
	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}
	public int getCurrenCount() {
		return CurrenCount;
	}
	public void setCurrenCount(int currenCount) {
		CurrenCount = currenCount;
	}
	public int getThroughtBytes() {
		return ThroughtBytes;
	}
	public void setThroughtBytes(int throughtBytes) {
		ThroughtBytes = throughtBytes;
	}
	public int getErrorCount() {
		return ErrorCount;
	}
	public void setErrorCount(int errorCount) {
		ErrorCount = errorCount;
	}
	
}
