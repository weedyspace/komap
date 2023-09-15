package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.Icon;



public class IconDao extends SqlSessionDaoSupport{
	
	public int insertIcon(Icon icon) {
		return this.getSqlSession().insert("icon.insertIcon", icon);
	}
	
	public List<Icon> getAll() {
		return this.getSqlSession().selectList("icon.getAll");
	}
	
}