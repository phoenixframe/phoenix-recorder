package com.youku.yks.bean;

/**
 * 对并发执行过程中数据操作的Bean
 * @author mengfeiyang
 *
 */
public class StrategyDataBean {
	private int id;
	private int stategyId;
	private String tps;
	private String maxTPS;
	private String durationTime;
	private String responseTime;
	private String maxResponseTime;
	private String throughputBytes;
	private String PassCount;
	private String FailCount;
	private String ExceptionCount;
	private String PassRate;
	private String FailRate;
	
	public StrategyDataBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StrategyDataBean(int stategyId, String tps,String maxTPS,
			String durationTime, String responseTime, String maxResponseTime, String throughputBytes,
			String passCount, String failCount, String exceptionCount, String passRate, String failRate) {
		super();
		this.stategyId = stategyId;
		this.tps = tps;
		this.maxTPS = maxTPS;
		this.durationTime = durationTime;
		this.responseTime = responseTime;
		this.maxResponseTime = maxResponseTime;
		this.throughputBytes = throughputBytes;
		PassCount = passCount;
		FailCount = failCount;
		ExceptionCount = exceptionCount;
		PassRate = passRate;
		FailRate = failRate;
	}
	public StrategyDataBean(int id, int stategyId, String tps,String maxTPS,
			String durationTime, String responseTime, String maxResponseTime, String throughputBytes,
			String passCount, String failCount, String exceptionCount, String passRate, String failRate) {
		super();
		this.id = id;
		this.stategyId = stategyId;
		this.tps = tps;
		this.maxTPS = maxTPS;
		this.durationTime = durationTime;
		this.responseTime = responseTime;
		this.maxResponseTime = maxResponseTime;
		this.throughputBytes = throughputBytes;
		PassCount = passCount;
		FailCount = failCount;
		ExceptionCount = exceptionCount;
		PassRate = passRate;
		FailRate = failRate;
	}
	
	
	public String getExceptionCount() {
		return ExceptionCount;
	}

	public void setExceptionCount(String exceptionCount) {
		ExceptionCount = exceptionCount;
	}

	public String getMaxTPS() {
		return maxTPS;
	}

	public void setMaxTPS(String maxTPS) {
		this.maxTPS = maxTPS;
	}

	public String getMaxResponseTime() {
		return maxResponseTime;
	}

	public void setMaxResponseTime(String maxResponseTime) {
		this.maxResponseTime = maxResponseTime;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStategyId() {
		return stategyId;
	}
	public void setStategyId(int stategyId) {
		this.stategyId = stategyId;
	}

	public String getTps() {
		return tps;
	}

	public void setTps(String tps) {
		this.tps = tps;
	}

	public String getDurationTime() {
		return durationTime;
	}
	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	public String getThroughputBytes() {
		return throughputBytes;
	}
	public void setThroughputBytes(String throughputBytes) {
		this.throughputBytes = throughputBytes;
	}
	public String getPassCount() {
		return PassCount;
	}
	public void setPassCount(String passCount) {
		PassCount = passCount;
	}
	public String getFailCount() {
		return FailCount;
	}
	public void setFailCount(String failCount) {
		FailCount = failCount;
	}
	public String getPassRate() {
		return PassRate;
	}
	public void setPassRate(String passRate) {
		PassRate = passRate;
	}
	public String getFailRate() {
		return FailRate;
	}
	public void setFailRate(String failRate) {
		FailRate = failRate;
	}
}
