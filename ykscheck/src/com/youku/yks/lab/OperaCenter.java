package com.youku.yks.lab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.youku.yks.bean.RunTimeBean;
import com.youku.yks.controller.CaseDataDTO;
import com.youku.yks.tools.ListSplit;

/**
 * 执行任务的控制中心，主要有以下职责：<br>
 * 1、分割任务<br>
 * 2、将分割的任务分发给每个并发线程<br>
 * 3、根据总任务大小分配线程池。线程池默认大小为100，<br>
 * 如果分配后的任务份数总数小于100，则按任务总数分配线程池，否则使用100.<br>
 * @author mengfeiyang
 * @since JDK 1.7
 */
public class OperaCenter {
	private CaseDataDTO caseDataDTO;
	private ListSplit listSplit;
	private RunTimeBean runBean;
	private int threadNum = 100;
	private int logBatchId;
	
	public OperaCenter(int logBatchId,RunTimeBean runBean){
		this.logBatchId = logBatchId;
		this.runBean = runBean;
	}

	/**
	 * 执行任务的控制中心，主要有以下职责：<br>
	 * 1、分割任务<br>
	 * 2、将分割的任务分发给每个并发线程<br>
	 * 3、根据总任务大小分配线程池。线程池默认大小为100，<br>
	 * 如果分配后的任务份数总数小于100，则按任务总数分配线程池，否则使用100.<br>
	 * @author mengfeiyang
	 * @since JDK 1.7
	 */
	public void runCase() {
		caseDataDTO = new CaseDataDTO();
		listSplit = new ListSplit();
		List<List<String[]>> subList;
		LinkedList<String[]> caseData = caseDataDTO.getCaseData(runBean);
		HashMap<String,List<String>> headMap = caseDataDTO.getHeadMap();
		if (caseData.size() > threadNum) {
			subList = listSplit.SplitList(caseData, threadNum);
		} else {
			threadNum = caseData.size();
			subList = listSplit.SplitList(caseData, threadNum);
		}
		ExecutorService pool = Executors.newFixedThreadPool(threadNum);
		CompletionService<String> ThreadPool = new ExecutorCompletionService<String>(pool);

		int totalTaskSize = subList.size();
		List<Future<String>> resultList = new ArrayList<Future<String>>();
		for (int i = 0; i < totalTaskSize; i++) {
			TaskHandle deployTask = new TaskHandle();
			deployTask.setCurrentThreadNum(i);
			deployTask.setTaskList(subList.get(i));
			deployTask.setHeadMap(headMap);
			deployTask.setBatchId(logBatchId);
			Future<String> future = ThreadPool.submit(deployTask);
			resultList.add(future);
		}
		pool.shutdown();
		/*调试信息,用于打印每个线程的执行结果*/
/*		for (Future<String> fs : resultList) {
			try {
				System.out.println(fs.get()); // 打印各个线程（任务）执行的结果
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} finally {
				// 启动一次顺序关闭，执行以前提交的任务，已提交的任务执行完毕后，将自动关闭。但不接受新任务。如果已经关闭，则调用没有其他作用。
				pool.shutdown();
			}
        } */		
	}
}
