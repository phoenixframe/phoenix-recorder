package com.youku.yks.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.youku.yks.bean.StatusBean;
import com.youku.yks.dao.CaseDao;
import com.youku.yks.dao.LogBatch;
import com.youku.yks.dao.LogUnitDao;
import com.youku.yks.dao.StatusDao;
import com.youku.yks.dao.impl.CaseImpl;
import com.youku.yks.dao.impl.LogBatchImpl;
import com.youku.yks.dao.impl.LogUnitImpl;
import com.youku.yks.dao.impl.StatusDaoImpl;

/**
 * 对测试结果进行分析，获取成功或失败总数。
 * 此类性能待优化，后续持久化之后再行改进,目前方案为：<br>
 * 1、首次查询时，会从l_unit表中取数据，这个过程较慢<br>
 * 2、再次查询时，则直接从l_status表中取数据，这个过程会很快。<br>
 * @author mengfeiyang
 * @since JDK 1.7
 */
public class ResultAnalyse {
	private LogUnitDao logDao = new LogUnitImpl();
	private CaseDao caseDao = new CaseImpl();
	private LogBatch batchDao = new LogBatchImpl();
	private StatusDao statusDao = new StatusDaoImpl();
	private List<String> resultList ;
	private String suList = "";
	private String failList = "";
	private String nodeNames = "";

	/**
	 * 1、首次查询时，会从l_unit表中取数据，这个过程较慢<br>
	 * 2、再次查询时，则直接从l_status表中取数据，这个过程会很快。<br>
	 * @author mengfeiyang
	 * @since JDK 1.7
	 */
	public List<String> getResultRate(int batchId,int caseTypeId){
		suList = "";
		failList = "";
		nodeNames = "";
		resultList = new ArrayList<String>();
		List<StatusBean> statusList = statusDao.getStatus(batchId, caseTypeId);
		if(statusList.size() == 0){
			List<String> caseNodeList = logDao.getCaseNode(batchId, caseTypeId);		   
       	    for(String caseNode : caseNodeList){
       	    	nodeNames += "'"+caseDao.getUnitCase(Integer.parseInt(caseNode)).getCaseNode()+"',";
       	    	int PassCount = logDao.getResultCount(batchId, caseTypeId, 3, Integer.parseInt(caseNode));
       	    	int FailCount = logDao.getResultCount(batchId, caseTypeId, 4, Integer.parseInt(caseNode));
       	    	suList += PassCount + ",";
       	    	failList += FailCount + ",";
       	    	statusDao.addStatus(new StatusBean(batchId,caseTypeId,Integer.parseInt(caseNode),PassCount,FailCount));
       	    }       	             	 
		} else {
			for(StatusBean status : statusList){
				nodeNames += "'"+caseDao.getUnitCase(status.getCaseId()).getCaseNode()+"',";
				suList += status.getS_rate()+",";
				failList += status.getF_rate()+",";
			}			
		}
      	 resultList.add(suList);
      	 resultList.add(failList);
      	 resultList.add(nodeNames);
      	 
      	 return resultList;
	}	
	
	public List<String> getLineChartData(int caseTypeId,int limit){
		suList = "";
		failList = "";
		nodeNames = "";
		resultList = new ArrayList<String>();
		List<String> batchList = logDao.getBatchList(caseTypeId, limit);
		HashMap<Integer,Integer> PassMap = logDao.getPassCount(caseTypeId, limit);
		HashMap<Integer,Integer> FailMap = logDao.getFailCount(caseTypeId, limit);
		for(String batch : batchList){
			nodeNames += "'"+batchDao.getBatchId(Integer.parseInt(batch)).getId()+"-"+batchDao.getBatchId(Integer.parseInt(batch)).getBatchString()+"',";
			suList += PassMap.get(Integer.parseInt(batch))+",";
			failList += FailMap.get(Integer.parseInt(batch))+",";
		}		 
     	 resultList.add(suList.replace("null", "0"));
     	 resultList.add(failList.replace("null", "0"));
     	 resultList.add(nodeNames);
     	 
     	 return resultList;
	}
}
