package com.youku.yks.dao;

import java.util.Date;
import java.util.List;

import com.youku.yks.bean.StatusBean;

/**
 * 对结果统计操作的接口
 * @author mengfeiyang
 *
 */
public interface StatusDao {
	/**
	 * 增加一条记录
	 * @param statusBean
	 * @return
	 */
   int addStatus(StatusBean statusBean);
   
   /**
    * 删除一条记录,根据Id
    * @param id
    * @return
    */
   int deleStatus(int id);
   
   /**
    * 更新一条记录
    * @param statusBean
    * @return
    */
   int updateStatus(StatusBean statusBean);
   
   /**
    * 获取一条记录
    * @param batchId
    * @param caseTypeid
    * @param caseid
    * @return
 * @throws Exception 
    */
   List<StatusBean> getStatus(int batchId,int caseTypeid);
   
   /**
    * 获取一条记录
    * @param id
    * @return
 * @throws Exception 
    */
   StatusBean getStatus(int id) throws Exception;
   
   /**
    * 批量获取记录
    * @param batchId
    * @return
    */
   List<StatusBean> getStatusByBatchId(int batchId);
   
   /**
    * 批量获取记录，根据时间戳
    * @param start
    * @param end
    * @return
    */
   List<StatusBean> getStatusByDate(Date start,Date end);
   
}
