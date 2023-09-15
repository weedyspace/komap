package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Log;
import com.my.diseaseControl.vo.WorkImageData;



public class LogDao extends SqlSessionDaoSupport{
	
	public int insertLog(Log log) {
		return this.getSqlSession().insert("log.insertLog", log);
	}
	
	public List<Log> getAll() {
		return this.getSqlSession().selectList("log.getAll");
	}	
	
	public List<Log> getLogByActionType(String ActionType) {
		return this.getSqlSession().selectList("log.getLogByActionType", ActionType);
	}
	
	public List<Log> getLogByActivator(String Activator) {
		return this.getSqlSession().selectList("log.getLogByActivtor", Activator);
	}
	
	public List<Log> getLogByResponder(String Responder) {
		return this.getSqlSession().selectList("log.getLogByResponder", Responder);
	}
	
	public int messageFromActivatorToResponder(Log log) {
		return this.getSqlSession().insert("log.messageFromActivatorToResponder", log);
	}
	
}