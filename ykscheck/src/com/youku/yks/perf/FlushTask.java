package com.youku.yks.perf;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import com.youku.yks.bean.FlushBean;
import com.youku.yks.bean.StrategyBean;
import com.youku.yks.json.JsonPaser;
import com.youku.yks.tools.GetXml;

public class FlushTask implements Callable<String>{
	private int taskNum;
	private JsonPaser jsonPaser;
	private StrategyBean strategy;
	private ConcurrentHashMap<Long,FlushBean> flushMap;	
	
	public StrategyBean getStrategy() {
		return strategy;
	}

	public void setStrategy(StrategyBean strategy) {
		this.strategy = strategy;
	}

	public ConcurrentHashMap<Long, FlushBean> getFlushMap() {
		return flushMap;
	}

	public void setFlushMap(ConcurrentHashMap<Long, FlushBean> flushMap) {
		this.flushMap = flushMap;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	@Override
	public String call() throws Exception {
		FlushBean flushBean = new FlushBean();
		jsonPaser = new JsonPaser();
		int maxTimes  = strategy.getIterationSet();
		int PassCount = 0;
		int FailCount = 0;
		int ErrorCount = 0;
		int i = 0;
		String content = "";
		String actual = "";
		long t1 = 0;
		boolean runFlag = PerformanceManag.flag;
		int timeOut = Integer.parseInt(strategy.getMaxTimeout());
		
		while(runFlag){	
			i++;			
			String url = strategy.getUrl();
			try{
			    t1 = new Date().getTime();		
			    content = GetXml.getResponseByGet(url,timeOut,-1);
			    actual = jsonPaser.getNodeValue(content, strategy.getCheckpoint());
			    PerformanceManag.getTPS();
			    if(!actual.equals(strategy.getExpectValue())) {
			    	FailCount++;
			    	flushBean.setErrorInfo("["+FailCount+"]不等于期望值");
			    } else {
			    	PassCount++;
			    	flushBean.setErrorInfo("["+PassCount+"]与期望值一致");
			    }
			}catch(Exception e){			
				ErrorCount++;
				flushBean.setErrorInfo("["+ErrorCount+"]"+e.getClass().getName());
			}
			long t2 = new Date().getTime();
			
			flushBean.setStatus("Running...");
			flushBean.setResponseTime(t2 - t1);
			flushBean.setCurrenCount(i);
			flushBean.setErrorCount(FailCount);
			flushBean.setExceptionCount(ErrorCount);
			flushBean.setSuccessCount(PassCount);
			flushBean.setThroughtBytes(content.length());
			flushBean.setTotalCount(maxTimes);			
			flushBean.setVuserId(Thread.currentThread().getId());			
			if(strategy.getRunType().equals("byIteration")){	
				flushBean.setRemainCount(maxTimes - i);
				if(i == maxTimes){
					flushMap.put(Thread.currentThread().getId(), flushBean);
					 break;
				}
			}			
			flushMap.put(Thread.currentThread().getId(), flushBean);
			if(strategy.getAlternation() > 0)Thread.sleep(strategy.getAlternation());
			runFlag = PerformanceManag.flag;
			if(PerformanceManag.threadId == Thread.currentThread().getId() || PerformanceManag.threadId == -2){
				flushBean.setStatus("Stop");
				break;
			}
		}
		if(!flushBean.getStatus().equals("Stop"))flushBean.setStatus("Complete");
		flushMap.put(Thread.currentThread().getId(), flushBean);
				
		return Thread.currentThread().getName();
	}

}
