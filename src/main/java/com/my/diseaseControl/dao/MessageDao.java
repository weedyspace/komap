package com.my.diseaseControl.dao;


import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.Message;


public class MessageDao extends SqlSessionDaoSupport{

	public int insertMessage(Message message) {
		return this.getSqlSession().insert("message.insertMessage", message);
	}	
	
}