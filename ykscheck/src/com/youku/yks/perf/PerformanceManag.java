package com.youku.yks.perf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import com.youku.yks.bean.FlushBean;
import com.youku.yks.bean.StrategyBean;
import com.youku.yks.bean.StrategyDataBean;
import com.youku.yks.dao.impl.StrategyDataImpl;
import com.youku.yks.dao.impl.StrategyImpl;
import com.youku.yks.tools.GetNow;
import com.youku.yks.tools.LocalInfo;

public class PerformanceManag {
	private List<Future<String>> resultList = new ArrayList<Future<String>>();
	private ConcurrentHashMap<Long,FlushBean> flushMap = new ConcurrentHashMap<Long,FlushBean>();
	private StrategyImpl strategyImpl = new StrategyImpl();
	private StrategyDataImpl strategyDataImpl = new StrategyDataImpl();
	private ExecutorService pool;
	private CompletionService<String> ThreadPool;
	private String startTime;	
	private StrategyBean strategyBean;
	private StrategyDataBean dataBean;
	private Timer timer = new Timer();
	private Timer addLogTimer = new Timer();
	private Object[] rateData = new Object[15];
	private long seconds = 0;
	private int strategyId;
	private double MaxAvgTime = 0.0;
	private long maxTps = 0;
	private static AtomicInteger tps = new AtomicInteger(0); 
	private boolean tFlag;
	public volatile static boolean flag;
	public volatile static int threadId = -1;

	public static void getTPS(){
		tps.getAndIncrement();
	}

	public StrategyBean getStrategyBean() {
		return strategyBean;
	}

	public void setStrategyBean(StrategyBean strategyBean) {
		this.strategyBean = strategyBean;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public void startTimer() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				seconds++;
				if(strategyBean.getRunType().equals("byRunTime") && seconds == Integer.parseInt(strategyBean.getRunTimeSet())){
					flag = false;
					stopTimer();
				}
			}
		}, 0, 1000);
	}
	
	public void addLogTimer() {
		addLogTimer.schedule(new TimerTask() {
			@Override
			public void run() {	
				dataBean = new StrategyDataBean(
						strategyId,
						tps.get()+"",
						rateData[14]+"",
						seconds+"",
						rateData[3]+"",
						rateData[13]+"",
						rateData[0]+"",						
						rateData[1]+"",
						rateData[2]+"",
						rateData[8]+"",
						rateData[6]+"",
						rateData[7]+""
						);
				strategyDataImpl.addStrategyData(dataBean);	
			}
		}, 30000, 60000);
	}
	
	public long getDurationTime(){
		return seconds;
	}
	
	/**
	 * 停止时间统计计时器，插入Log的计时器，并且手动关闭时，强制插入最后保留的数据。
	 */
	public void stopTimer(){
		if(!tFlag){
		timer.cancel();
		addLogTimer.cancel();
		dataBean = new StrategyDataBean(
				strategyId,
				tps.get()+"",
				rateData[14]+"",
				seconds+"",
				rateData[3]+"",
				rateData[13]+"",
				rateData[0]+"",						
				rateData[1]+"",
				rateData[2]+"",
				rateData[8]+"",
				rateData[6]+"",
				rateData[7]+""
				);
		strategyDataImpl.addStrategyData(dataBean);	
		}
	}
	
	private void init(){
		flag = true;
		tFlag = false;
		threadId = -1;
		MaxAvgTime = 0.0;
		maxTps = 0;
		if(pool != null)pool.shutdownNow();
	}

	/**
	 * 任务管理器，任务部署，状态结果收集
	 * @return
	 */
	public int taskManager(){	
		init();
		int size = strategyBean.getVusers();		
		startTimer();		
		setStartTime(GetNow.getCurrentTime());
		strategyBean.setStartTime(getStartTime());
		strategyBean.setClientIP(LocalInfo.getRealIp());
		strategyId = strategyImpl.addStrategy(strategyBean);		
		pool = Executors.newFixedThreadPool(size);		
		ThreadPool = new ExecutorCompletionService<String>(pool);
		for (int i = 0; i < size; i++) {
			FlushTask deployTask = new FlushTask();
			deployTask.setStrategy(strategyBean);
			deployTask.setTaskNum(i);
			deployTask.setFlushMap(flushMap);
			Future<String> future = ThreadPool.submit(deployTask);	
			resultList.add(future);
		}
		pool.shutdown();
		addLogTimer();
		
		return 1;
	}
	
	
	
	
	/**
	 * 关闭线程池
	 * @return
	 */
	public int shutdownPool(){
		threadId = -2;
		rateData[9] = "Stop";
		//pool.shutdownNow();
		
		return 1;
	}
	
	/**
	 * 停止指定线程
	 * @param id
	 * @return
	 */
	public int shutdownThread(int id){
		threadId = id;
		return 1;
	}
	
	/**
	 * 获取各个线程执行结果
	 * @return
	 */
	public List<Future<String>> getFutureList(){
		return resultList;
	}
	
/*	public void Test(){
		for(Future<String> future : resultList){
			try{
			future.get();//如果未取到值，则处于阻塞状态
			future.get(100, TimeUnit.MILLISECONDS);//取值的超时时间
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}*/
	
	
	/**
	 * 对各个线程的执行过程进行监控
	 * @return
	 */
	   
	public ConcurrentHashMap<Long,FlushBean> getFlushMap(){
		   long responseTime = 0;
		   double throughBytes = 0;
		   int TotalPass = 0;
		   int TotalError = 0;
		   int TotalException = 0;
		   int TotalStop = 0;
		   int TotalRunning = 0;
		   int TotalComplete = 0;
		   
		   Iterator<Entry<Long, FlushBean>> itera = flushMap.entrySet().iterator();
		   while(itera.hasNext()){
			   Entry<Long, FlushBean> entry = (Entry<Long, FlushBean>)itera.next();
			   FlushBean flush = entry.getValue();			   
			   throughBytes += flush.getThroughtBytes();
			   TotalPass += flush.getSuccessCount();
			   TotalError += flush.getErrorCount(); 
			   TotalException += flush.getExceptionCount();
			   
			   if(flush.getStatus().equals("Complete")) TotalComplete++;
			   else if(flush.getStatus().equals("Running...")) { TotalRunning++; responseTime += flush.getResponseTime(); }
			   else if(flush.getStatus().equals("Stop")) TotalStop++;
			   if((TotalComplete+TotalStop)==strategyBean.getVusers()) {
				   stopTimer();
				   tFlag = true;
			   }
		   }	
		   double SuccRate = 0.0;
		   try{ 
			   SuccRate = Double.parseDouble(new DecimalFormat("#.00000").format((double)TotalPass/(double)(TotalPass+TotalError+TotalException))); 
			  }catch (Exception e) {
				  //e.printStackTrace();
			  }
		   double FailRate = ((double)1.0-SuccRate);
		   double Restimeavg = 0.0;
		   try{ Restimeavg = Double.parseDouble(new DecimalFormat("#.00").format((double)responseTime/(double)TotalRunning));} catch(Exception e){}
		   if(Restimeavg > MaxAvgTime)MaxAvgTime = Restimeavg;
		   if(tps.get() > maxTps)maxTps = tps.get();
		   rateData[0] = throughBytes;
		   rateData[1] = TotalPass;
		   rateData[2] = TotalError;
		   rateData[3] = Restimeavg;
		   rateData[4] = strategyBean.getVusers()-TotalComplete-TotalStop;
		   rateData[5] = tps.get();
		   rateData[6] = SuccRate*100+"%";
		   rateData[7] = FailRate*100+"%";
		   rateData[8] = TotalException;
		   rateData[10] = TotalStop;
		   rateData[11] = TotalRunning;
		   rateData[12] = TotalComplete;
		   rateData[13] = MaxAvgTime;
		   rateData[14] = maxTps;
		   tps.getAndSet(0);
		   
		return flushMap;
	}
	
	/**
	 * 返回获取到的监控结果的值
	 * @return
	 */
	public Object[] getRateData(){
		return rateData;
	}
	
	/**
	 * 获取任务启动时间
	 * @return
	 */
	public String getStartTime(){
		return startTime;
	}
	

}
