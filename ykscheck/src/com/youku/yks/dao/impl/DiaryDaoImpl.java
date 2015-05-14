package com.youku.yks.dao.impl;

import java.util.List;

import com.youku.yks.batchoper.BatchDataOper;
import com.youku.yks.bean.DiaryBean;
import com.youku.yks.dao.DiaryDao;

public class DiaryDaoImpl extends BatchDataOper implements DiaryDao {

	@Override
	public int addDiary(DiaryBean diaryBean) {
		String sql = "INSERT INTO k_diary(userId,content) VALUES('"+diaryBean.getUserId()+"','"+diaryBean.getContent()+"');";
		return getUpdateResult(sql);
	}

	@Override
	public int deleDiary(int id) {
		String sql = "DELETE FROM k_diary WHERE id='"+id+"'";
		return getUpdateResult(sql);
	}

	@Override
	public int updateDiary(DiaryBean diaryBean) {
		String sql = "UPDATE k_diary SET userId='"+diaryBean.getUserId()+"',content='"+diaryBean.getContent()+"' WHERE id='"+diaryBean.getId()+"'";
		return getUpdateResult(sql);
	}

	@Override
	public DiaryBean getDiary(int id) throws Exception{
		String sql = "SELECT * FROM k_diary WHERE id='"+id+"'";
        return getBatchDiary(sql).get(0);
	}

	@Override
	public List<DiaryBean> getAllDiaryByTime(String start, String end) {
		String sql = "SELECT * FROM k_diary WHERE createtime BETWEEN '"+start+"' AND '"+end+"'";
		return getBatchDiary(sql);
	}

	@Override
	public List<DiaryBean> getAllDiaryByKeyword(String keyword) {
		String sql  ="SELECT * FROM k_diary WHERE content like '%"+keyword+"%'";
		return getBatchDiary(sql);
	}

	@Override
	public List<DiaryBean> getAllDiaryByUserId(int userId) {
		String sql = "SELECT * FROM k_diary WHERE userId='"+userId+"'";
		return getBatchDiary(sql);
	}

	@Override
	public List<DiaryBean> getAllDiary() {
		String sql = "SELECT * FROM k_diary";
		return getBatchDiary(sql);
	}

}
