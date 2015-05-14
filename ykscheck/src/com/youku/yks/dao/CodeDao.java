package com.youku.yks.dao;

import java.util.HashMap;
import java.util.List;

import com.youku.yks.bean.CodeBean;

/**
 * 
 * @author 对状态码进行操作的接口
 *
 */
public interface CodeDao {
	/**
	 * 增加一个状态码
	 * @param code
	 * @return
	 */
	int addCode(CodeBean code);
	
	/**
	 * 删除一个状态码
	 * @param codeId
	 * @return
	 */
	int deleCode(int codeId);
	
	/**
	 * 更新一个状态码
	 * @param Code
	 * @return
	 */
	int updateCode(CodeBean Code);
	
	/**
	 * 指定范围内的状态码
	 * @param start
	 * @param end
	 * @return
	 */
	List<CodeBean> getCodeList(int start,int end);
	
	/**
	 * 获取所有状态码
	 * @return
	 */
	HashMap<Integer,String> getAllCode();
	
	/**
	 * 获取一个状态码
	 * @param codeId
	 * @return
	 */
	String getCode(int codeId);

}
