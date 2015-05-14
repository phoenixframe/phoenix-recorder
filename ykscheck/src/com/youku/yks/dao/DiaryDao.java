package com.youku.yks.dao;

import java.util.List;

import com.youku.yks.bean.DiaryBean;

/**
 * 对组员日志操作的接口
 * @author mengfeiyang
 *
 */
public interface DiaryDao {
	
	/**
	 * 增加日志
	 * @param diaryBean
	 * @return
	 */
	int addDiary(DiaryBean diaryBean);
	
	/**
	 * 删除日志，根据日志id
	 * @param id
	 * @return
	 */
	int deleDiary(int id);
	
	/**
	 * 更新日志
	 * @param diaryBean
	 * @return
	 */
	int updateDiary(DiaryBean diaryBean);
	
	/**
	 * 获取一篇日志，根据id
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	DiaryBean getDiary(int id) throws Exception;
	
	/**
	 * 根据时间查询日志
	 * @param start
	 * @param end
	 * @return
	 */
	List<DiaryBean> getAllDiaryByTime(String start,String end);
	
	/**
	 * 根据关键字查询日志
	 * @param keyword
	 * @return
	 */
	List<DiaryBean> getAllDiaryByKeyword(String keyword);
	
	/**
	 * 根据用户id查询日志
	 * @param userId
	 * @return
	 */
	List<DiaryBean> getAllDiaryByUserId(int userId);
	
	/**
	 * 获取所有的日志列表
	 * @return
	 */
	List<DiaryBean> getAllDiary();

}
