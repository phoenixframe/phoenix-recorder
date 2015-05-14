package com.youku.yks.lab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import com.youku.yks.bean.LogUnitBean;
import com.youku.yks.dao.VidDao;
import com.youku.yks.dao.impl.DBDaoImpl;
import com.youku.yks.dao.impl.LogUnitImpl;
import com.youku.yks.dao.impl.VidImpl;
import com.youku.yks.json.JsonPaser;
import com.youku.yks.tools.GetXml;

/**
 * 处理任务实体类
 * @author mengfeiyang
 *
 */
public class TaskHandle extends DBDaoImpl implements Callable<String> {
	private List<String[]> taskList;
	private int currentThreadNum;
	private int batchId;
	private VidDao vidDao;
	private LogUnitBean logUnitBean;
	private LogUnitImpl logUnitImpl;
	private JsonPaser jsonPa ;
	private HashMap<String,List<String>> headMap;
	
	public HashMap<String, List<String>> getHeadMap() {
		return headMap;
	}

	public void setHeadMap(HashMap<String, List<String>> headMap) {
		this.headMap = headMap;
	}

	public int getCurrentThreadNum() {
		return currentThreadNum;
	}

	public void setCurrentThreadNum(int currentThreadNum) {
		this.currentThreadNum = currentThreadNum;
	}
	
	public List<String[]> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<String[]> taskList) {
		this.taskList = taskList;
	}

	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	@Override
	public String call() throws Exception {
		for(String[] task : taskList){
			jsonPa = new JsonPaser();
			vidDao = new VidImpl();
			logUnitBean = new LogUnitBean();
			logUnitImpl = new LogUnitImpl();
			String typeName = task[0];
			String CaseNode = task[1];
			String paramNode = task[2];
			String expect = task[3];
			String url = task[4];	
			String sql = "SELECT t.id as typeId,c.id as caseId,n.id as nodeId FROM t_type t,t_case c,t_node n where t.typeName='"+typeName
					+"' AND c.caseNode='"+CaseNode
					+"' AND n.paramNode='"+paramNode+"';";
			LinkedList<String> ilist = getValue(sql,3);
			int vid = Integer.parseInt(task[5]);
			String requestType = task[6];
			String jsonPath = task[7];
			String dataId = task[8];
			
			logUnitBean.setBatchId(batchId);
			logUnitBean.setVid(vid);
			logUnitBean.setResult(2);
			logUnitBean.setTypeId(Integer.parseInt(ilist.get(0)));
			logUnitBean.setCaseNodeId(Integer.parseInt(ilist.get(1)));
			logUnitBean.setParamNodeId(Integer.parseInt(ilist.get(2)));
			logUnitBean.setUrl(url);
			logUnitBean.setRemark("Running...");
			
			int incId = logUnitImpl.addUnitLog(logUnitBean);
			String vidValue = vidDao.getVidById(vid).getVid();
			String vidType = vidDao.getVidById(vid).getRemark();
			String JSONContent = "";
			List<String> headList  = headMap.get(url);
			try {
				if(requestType.equals("get")){
					if(headList !=null && headList.size()>0){
						JSONContent = GetXml.getResponseByGet(url,headList,"setHeaderField");
					} else {
						JSONContent = GetXml.getResponseByGet(url);
					}
				} else if(requestType.equals("post")){
					if(headList !=null && headList.size()>0){
						JSONContent = GetXml.getResponseByPost(url,headList,"setHeaderField");
					} else{
						JSONContent = GetXml.getResponseByPost(url);
					}
				}
				
				String actual = jsonPa.getNodeValue(JSONContent, jsonPath);
				if (expect.equals(actual)) {
					logUnitBean.setResult(3);
				} else {
					logUnitBean.setResult(4);
				}
				logUnitBean.setRemark("Complete，["+typeName+"-><a target=navTab rel=caseNodeDetail href=node.jsp?org1.id="+ilist.get(1)+"&org1.orgName="+CaseNode+">"+CaseNode+"</a>-><a target=navTab rel=dataDetail title=查看详细  href=data.jsp?org1.id="+ilist.get(2)+"&org1.orgName="+paramNode+">"+paramNode+"</a>-><a target=dialog title=查看详细 href=detail/datadetail.jsp?id="+dataId+">DataId:"+dataId+"</a>],["+vidValue+"("+vidType+")],[实际值是："+actual+",期望值是："+expect+"],[jsonPath："+jsonPath+"]");
			} catch (Exception e) {
				logUnitBean.setResult(4);
				logUnitBean.setRemark("Error，["+typeName+"-><a target=navTab rel=caseNodeDetail href=node.jsp?org1.id="+ilist.get(1)+"&org1.orgName="+CaseNode+">"+CaseNode+"</a>-><a target=navTab rel=dataDetail title=查看详细  href=data.jsp?org1.id="+ilist.get(2)+"&org1.orgName="+paramNode+">"+paramNode+"</a>-><a target=dialog title=查看详细 href=detail/datadetail.jsp?id="+dataId+">DataId:"+dataId+"</a>],["+vidValue+"("+vidType+")],[jsonPath："+jsonPath+"],异常信息："+e.getMessage()+",异常类型："+e.getClass().getName());
			}
			logUnitBean.setId(incId);
			logUnitImpl.updateUnitLog(logUnitBean);						
		}
		
		return "已接收到任务指令 "+Thread.currentThread().getName();
	}

}
