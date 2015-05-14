package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.VidBean;
import com.youku.yks.dao.VidDao;

/**
 * 对Vid进行操作的实现类
 * @author mengfeiyang
 *
 */
public class VidImpl extends BatchDataOper implements VidDao {

	@Override
	public int addVid(VidBean vidBean) {
		String sql = "INSERT INTO t_vid values(0,'"+vidBean.getUserId()+"','"+vidBean.getVid()+"','"+vidBean.getVidName()+"','"+vidBean.getRemark()+"');";
		return getUpdateResult(sql);
	}

	@Override
	public int deleVidById(int id) {
		String sql = "DELETE FROM t_vid WHERE id='"+id+"';";
		return getUpdateResult(sql);
	}

	@Override
	public int updateVid(VidBean vidBean) {
		String sql = "UPDATE t_vid SET vid='"+vidBean.getVid()
				+"',vidName = '"+vidBean.getVidName()
				+"',remark = '"+vidBean.getRemark()
				+"' WHERE id='"+vidBean.getId()+"';";
		return getUpdateResult(sql);
	}

	@Override
	public List<VidBean> getAllVid() {
		String sql = "SELECT * FROM t_vid;";
		return getBatchVid(sql);
	}

	@Override
	public VidBean getVidById(int id) {
		String sql = "SELECT * FROM t_vid WHERE id='"+id+"';";
		return getBatchVid(sql).get(0);
	}

	@Override
	public VidBean getVidByName(String vid,int userId) throws Exception{
		String sql = "SELECT * FROM t_vid WHERE vid='"+vid+"' AND userId='"+userId+"';";
		return getBatchVid(sql).get(0);
	}

	@Override
	public List<VidBean> getAllVidByUser(int userId) {
		String sql = "SELECT * FROM t_vid WHERE userId='"+userId+"';";
		return getBatchVid(sql);
	}
}
