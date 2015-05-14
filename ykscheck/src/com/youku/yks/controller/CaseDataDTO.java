package com.youku.yks.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.youku.yks.bean.CaseBean;
import com.youku.yks.bean.CaseTypeBean;
import com.youku.yks.bean.DataBean;
import com.youku.yks.bean.NodeBean;
import com.youku.yks.bean.RunTimeBean;
import com.youku.yks.dao.impl.CaseImpl;
import com.youku.yks.dao.impl.CaseTypeImpl;
import com.youku.yks.dao.impl.DataImpl;
import com.youku.yks.dao.impl.NodeImpl;

/**
 * 组装数据表中的数据，使其产生可用的测试URL地址和接口数据。
 * @author mengfeiyang
 *
 */
public class CaseDataDTO {
	private DataDTO dataDTO = new DataDTO();
	private CaseTypeImpl caseTypeImpl = new CaseTypeImpl();
	private CaseImpl caseImpl = new CaseImpl();
	private NodeImpl nodeImpl = new NodeImpl();
	private DataImpl dataImpl = new DataImpl();	
	private HashMap<String,List<String>> headMap;
	public HashMap<String, List<String>> getHeadMap() {
		return headMap;
	}
	public void setHeadMap(HashMap<String, List<String>> headMap) {
		this.headMap = headMap;
	}
	/**
	 * 默认方法，获取库中所有可执行的用例数据。<br>
	 * <em>开发日期：2014-7-29 19:53</em>
	 * @author mengfeiyang
	 * @return
	 */
	public LinkedList<String[]> getCaseData(RunTimeBean runBean){
		LinkedList<String[]> dataBlocks = new LinkedList<String[]>();
		CaseTypeBean caseTypeBean = caseTypeImpl.getUnitCaseType(runBean.getCaseTypeId());
			String typeName = caseTypeBean.getTypeName();
			List<CaseBean> allEnabledCase = caseImpl.getAllEnabledCaseByTypeNameId(runBean.getCaseTypeId(),runBean.getUserId());
			for(CaseBean eCase : allEnabledCase){
				String caseNode = eCase.getCaseNode();
				List<NodeBean> allEnabledNode = nodeImpl.getAllEnabledNodeByCaseNodeId(eCase.getId(),runBean.getUserId());
				for(NodeBean eNode : allEnabledNode){
					String paramNode = eNode.getParamNode();
					List<DataBean> allEnabledData = dataImpl.getAllDataByParamNodeId(eNode.getId(),runBean.getUserId());
					for(DataBean eData : allEnabledData){
						String expect = eData.getExpect();
						String url = dataDTO.getUrl(eData);
						List<String> headList = dataDTO.getHeadList();
						headMap = new HashMap<String,List<String>>();
						String[] dataBlock = new String[9];
						dataBlock[0] = typeName;
						dataBlock[1] = caseNode;
						dataBlock[2] = paramNode;
						dataBlock[3] = expect;
						dataBlock[4] = url;
						dataBlock[5] = dataDTO.getVid()+"";
						dataBlock[6] = eData.getRequestType();
						dataBlock[7] = eData.getJsonPath();
						dataBlock[8] = eData.getId()+"";
						dataBlocks.add(dataBlock);
						headMap.put(url, headList);
						//System.out.println(typeName+" --- "+caseNode +"  --- " +paramNode+" --- "+expect+" --- "+url);
					}
				}
		    }
		return dataBlocks;
	}
}
