package com.youku.yks.controller;

import java.util.ArrayList;
import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.CaseBean;
import com.youku.yks.bean.DataBean;
import com.youku.yks.bean.HostBean;
import com.youku.yks.bean.InterfaceBean;
import com.youku.yks.bean.ParamBean;
import com.youku.yks.bean.VidBean;
import com.youku.yks.dao.CaseDao;
import com.youku.yks.dao.HostDao;
import com.youku.yks.dao.InterfaceDao;
import com.youku.yks.dao.NodeDao;
import com.youku.yks.dao.VidDao;
import com.youku.yks.dao.impl.CaseImpl;
import com.youku.yks.dao.impl.HostImpl;
import com.youku.yks.dao.impl.InterfaceDaoImpl;
import com.youku.yks.dao.impl.NodeImpl;
import com.youku.yks.dao.impl.VidImpl;

/**
 * 集中对数据进行解析转换操作的类
 * @author mengfeiyang
 *
 */
public class DataDTO extends BatchDataOper {
	private List<String> headList = new ArrayList<String>();
	private CaseDao caseDao = new CaseImpl();
	private NodeDao nodeDao = new NodeImpl();
	private HostDao hostDao = new HostImpl();
	private InterfaceDao interDao = new InterfaceDaoImpl();
	private VidDao vidDao = new VidImpl();
	private int vid;
		
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	public List<String> getHeadList() {
		return headList;
	}
	public void setHeadList(List<String> headList) {
		this.headList = headList;
	}
	
	/**
	 * 对数据进行转换，返回一条完整可用的URL地址
	 * @param dataBean
	 * @return
	 */
	public String getUrl(DataBean dataBean){
		StringBuilder stringBui = new StringBuilder();
		CaseBean caseBean = caseDao.getUnitCase(nodeDao.getUnitNode(dataBean.getParamNodeId()).getCaseNodeId());
		int vid = caseBean.getVid();
		int hostId = caseBean.getHostId();
		setVid(vid);
		VidBean vidBean = vidDao.getVidById(vid);
		HostBean hostBean = hostDao.getHost(hostId);
		InterfaceBean interBean = interDao.getInterface(caseBean.getInterfaceId());
		
		String getParamSQL = "SELECT paramName,paramValue FROM t_interface r,t_parameter p WHERE p.interfaceid = r.id AND p.paramType='setParameter' AND r.id = '"+interBean.getId()+"';";
		String getHeadSQL = "SELECT paramName,paramValue FROM t_interface r,t_parameter p WHERE p.interfaceid = r.id AND p.paramType='setHeader' AND r.id = '"+interBean.getId()+"';";
					
		String host = hostBean.getHostName();
		String interfaceValue = interBean.get_interface();
		String vidValue = vidBean.getVid();
		String vidName = vidBean.getVidName();
		
		List<ParamBean> pList = getBatchData(getParamSQL);
		List<ParamBean> hList = getBatchData(getHeadSQL);
		
		stringBui.append(vidName+"="+vidValue);
		for(ParamBean pBean: pList){
			stringBui.append("&"+pBean.getParamName()+"="+pBean.getParamValue());
		}
		for(ParamBean hBean : hList){
			headList.add(hBean.getParamName()+"=>"+hBean.getParamValue());		
		}		
		
		
		String url = "http://"+host+"/"+interfaceValue+stringBui.toString();
		return url;
	}
	
	public static void main(String[] args) {
		DataDTO dataDTO = new DataDTO();
		DataBean dataBean = new DataBean();
		dataBean.setParamNodeId(15);
		dataBean.setRequestType("get");
		dataBean.setUserId(6);
		
		System.out.println(dataDTO.getUrl(dataBean));
	}
}
