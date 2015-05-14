/**
 * 
 */
package com.youku.yks.dao.impl;

import java.util.Date;
import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.StatusBean;
import com.youku.yks.dao.StatusDao;

/**
 * StatusDao接口的实现类
 * @author mengfeiyang
 *
 */
public class StatusDaoImpl extends BatchDataOper implements StatusDao {

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#addStatus(com.youku.yks.bean.StatusBean)
	 */
	@Override
	public int addStatus(StatusBean statusBean) {
		String sql = "INSERT INTO l_status(batchId,caseTypeId,caseId,s_rate,f_rate) VALUES('"+statusBean.getBatchId()
				+"','"+statusBean.getCaseTypeId()
				+"','"+statusBean.getCaseId()
				+"','"+statusBean.getS_rate()
				+"','"+statusBean.getF_rate()
				+"');";
		return getUpdateResult(sql);
		
	}

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#deleStatus(int)
	 */
	@Override
	public int deleStatus(int id) {
		String sql = "DELETE FROM l_status WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#updateStatus(com.youku.yks.bean.StatusBean)
	 */
	@Override
	public int updateStatus(StatusBean statusBean) {
		String sql = "UPDATE l_status SET batchId='"+statusBean.getBatchId()
				+"',caseTypeId='"+statusBean.getCaseTypeId()
				+"',caseId='"+statusBean.getCaseId()
				+"',s_rate='"+statusBean.getS_rate()
				+"',f_rate='"+statusBean.getF_rate()
				+"' WHERE id='"+statusBean.getId()+"';";
		return getUpdateResult(sql);
	}
	

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#getStatus(int, int, int)
	 */
	@Override
	public List<StatusBean> getStatus(int batchId, int caseTypeid) {
		String sql = "SELECT * FROM l_status WHERE batchId='"+batchId+"' AND caseTypeId='"+caseTypeid+"'";
		return getBatchStatus(sql);
		
	}

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#getStatus(int)
	 */
	@Override
	public StatusBean getStatus(int id) throws Exception {
		String sql="SELECT * FROM l_status WHERE id='"+id+"'";
		return getBatchStatus(sql).get(0);
	}

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#getStatusByBatchId(int)
	 */
	@Override
	public List<StatusBean> getStatusByBatchId(int batchId) {
		String sql = "SELECT * FROM l_status WHERE batchId='"+batchId+"'";
		return getBatchStatus(sql);
	}

	/* (non-Javadoc)
	 * @see com.youku.yks.dao.StatusDao#getStatusByDate(java.util.Date, java.util.Date)
	 */
	@Override
	public List<StatusBean> getStatusByDate(Date start, Date end) {
		String sql = "SELECT * FROM l_status WHERE updatetime BETWEEN '"+start+"' AND '"+end+"'";
		return getBatchStatus(sql);
	}

}
