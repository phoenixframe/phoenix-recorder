package com.youku.yks.batchoper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.youku.yks.bean.CaseBean;
import com.youku.yks.bean.CaseTypeBean;
import com.youku.yks.bean.CodeBean;
import com.youku.yks.bean.DataBean;
import com.youku.yks.bean.DiaryBean;
import com.youku.yks.bean.HostBean;
import com.youku.yks.bean.LogBatchBean;
import com.youku.yks.bean.LogUnitBean;
import com.youku.yks.bean.MonitorBean;
import com.youku.yks.bean.NodeBean;
import com.youku.yks.bean.ParamBean;
import com.youku.yks.bean.InterfaceBean;
import com.youku.yks.bean.StatusBean;
import com.youku.yks.bean.StrategyBean;
import com.youku.yks.bean.StrategyDataBean;
import com.youku.yks.bean.UserBean;
import com.youku.yks.bean.VidBean;
import com.youku.yks.dao.impl.DBDaoImpl;

/**
 * 对各个接口实例需要进行数据批量操作的方法汇总<br>
 * <em>开发时间：2014年8月4日 9:55</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7
 *
 */
public class BatchDataOper extends DBDaoImpl {
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	private Connection conn = null;
	
	
	/**
	 * 对日志进行批量获取
	 * @param sql
	 * @return
	 */
	public List<DiaryBean> getBatchDiary(String sql){
		List<DiaryBean> diaryList = new ArrayList<DiaryBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				diaryList.add(new DiaryBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getTimestamp(4)
						));
			}
			return diaryList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}	
		return null;
	}
	
	/**
	 * 对执行策略操作
	 * @param sql
	 * @return
	 */
	public List<StrategyDataBean> getBatchStrategyData(String sql){
		List<StrategyDataBean> strategyDataList = new ArrayList<StrategyDataBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				strategyDataList.add(new StrategyDataBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getString(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13)
						));
			}
			return strategyDataList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}	
		return null;
	}
	
	/**
	 * 对执行策略操作
	 * @param sql
	 * @return
	 */
	public List<StrategyBean> getBatchStrategy(String sql){
		List<StrategyBean> strategyList = new ArrayList<StrategyBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				strategyList.add(new StrategyBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getInt(6),
						rs.getString(7),
						rs.getString(8),
						rs.getInt(9),
						rs.getInt(10),
						rs.getString(11),
						rs.getString(12),
						rs.getString(13),
						rs.getString(14)
						));
			}
			return strategyList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}	
		return null;
	}
	
	
	/**
	 * 获取统计结果数据
	 * @param sql
	 * @return
	 */
	public HashMap<Integer,Integer> getBatchRate(String sql){
		HashMap<Integer,Integer> beanMap = new HashMap<Integer,Integer>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanMap.put(rs.getInt(1), rs.getInt(2));
			}
			return beanMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}	
		return null;
	}
	
	
	/**
	 * Status批次批量获取
	 * @param sql
	 * @return
	 */
	public List<StatusBean> getBatchStatus(String sql){
		List<StatusBean> beanList = new ArrayList<StatusBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new StatusBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getTimestamp(7)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}		
		return null;
	}
	
	/**
	 * LOG批次批量获取
	 * @param sql
	 * @return
	 */
	public List<LogBatchBean> getBatchLog(String sql){
		List<LogBatchBean> beanList = new ArrayList<LogBatchBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new LogBatchBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getTimestamp(4)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}		
		return null;
	}
	
	/**
	 * LOG单元批量获取
	 * @param sql
	 * @return
	 */
	public List<LogUnitBean> getBatchLogUnit(String sql){
		List<LogUnitBean> beanList = new ArrayList<LogUnitBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new LogUnitBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getInt(7),
						rs.getString(8),
						rs.getString(9)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}	
		return null;
	}
	
	/**
	 * 批量获取监控数据
	 * @param sql
	 * @return
	 */
	public List<MonitorBean> getBatchMonitor(String sql){
		List<MonitorBean> beanList = new ArrayList<MonitorBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new MonitorBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}		
		return null;
	}
	
	/**
	 * 批量获取用例
	 * @param sql
	 * @return
	 */
	public List<CaseBean> getBatchCase(String sql){
		List<CaseBean> beanList = new ArrayList<CaseBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new CaseBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getInt(5),
						rs.getInt(6),
						rs.getInt(7),
						rs.getString(8),
						rs.getString(9),
						rs.getTimestamp(10)
						));
			}

			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}		
		return null;
	}
	
	/**
	 * 批量获取用例类型
	 * @param sql
	 * @return
	 */
	public List<CaseTypeBean> getBatchCaseType(String sql){
		conn = getConn();
		List<CaseTypeBean> beanList = new ArrayList<CaseTypeBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new CaseTypeBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getTimestamp(5)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rs,stmt,conn);
		}
		return null;
	}
	
	/**
	 * 批量获取编码值
	 * @param sql
	 * @return
	 */
	public List<CodeBean> getBatchCodeList(String sql){
		conn = getConn();
		List<CodeBean> codeList = new ArrayList<CodeBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				codeList.add(new CodeBean(
						rs.getInt(1),
						rs.getString(2)
						)
				);
			}
			return codeList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		
		return null;
	}
	
	/**
	 * 批量获取编码值
	 * @param sql
	 * @return
	 */
	public HashMap<Integer, String> getBatchCode(String sql){
		conn = getConn();
		HashMap<Integer, String> codeMap = new HashMap<Integer, String>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				codeMap.put(
						rs.getInt(1),
						rs.getString(2)
						);
			}
			return codeMap;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		
		return null;
	}
	
	/**
	 * 批量获取用例数据
	 * @param sql
	 * @return
	 */
	public List<DataBean> getBatchCaseData(String sql){
		conn = getConn();
		List<DataBean> beanList = new ArrayList<DataBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new DataBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		
		return null;
	}
	
	/**
	 * 批量获取用例节点
	 * @param sql
	 * @return
	 */
	public List<NodeBean> getBatchNode(String sql){
		conn = getConn();
		List<NodeBean> beanList = new ArrayList<NodeBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){				
				beanList.add(new NodeBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getString(5),
						rs.getString(6)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		
		return null;
	}
	
	/**
	 * 批量获取参数对象
	 * @param sql
	 * @return
	 */
	public List<ParamBean> getBatchData(String sql){
		List<ParamBean> beanList = new ArrayList<ParamBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new ParamBean(
						rs.getString(1),
						rs.getString(2)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}		
		return null;
	}
	
	/**
	 * 批量获取参数
	 * @param sql
	 * @return
	 */
	public List<ParamBean> getBatchParam(String sql){
		conn = getConn();
		List<ParamBean> beanList = new ArrayList<ParamBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new ParamBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		
		return null;
	}
	
	/**
	 * 批量获取用于拼接URL的请求数据
	 * @param sql
	 * @return
	 */
	public List<InterfaceBean> getBatchRequestData(String sql){
		List<InterfaceBean> beanList = new ArrayList<InterfaceBean>();
		conn = getConn();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new InterfaceBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}	
		return null;
	}
	
	/**
	 * 批量获取Vid
	 * @param sql
	 * @return
	 */
	public List<VidBean> getBatchVid(String sql){
		conn = getConn();
		List<VidBean> beanList = new ArrayList<VidBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new VidBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		
		return null;
	}
	
	/**
	 * 批量获取Host
	 * @param sql
	 * @return
	 */
	public List<HostBean> getBatchHost(String sql){		
		conn = getConn();
		List<HostBean> beanList = new ArrayList<HostBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new HostBean(
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		return null;
	}
	
	/**
	 * 批量获取用户信息
	 * @param sql
	 * @return
	 */
	public List<UserBean> getBatchUser(String sql){		
		conn = getConn();
		List<UserBean> beanList = new ArrayList<UserBean>();
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				beanList.add(new UserBean(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getInt(4),
						rs.getString(5)
						));
			}
			return beanList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			close(rs,stmt,conn);
		}
		return null;
	}
}
