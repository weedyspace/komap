package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.GIS;



public class GISDao extends SqlSessionDaoSupport{
	
	public int insertGIS(GIS gis) {
		return this.getSqlSession().insert("gis.insertGIS", gis);	
	}
	
	public List<GIS> getAll() {
		return this.getSqlSession().selectList("gis.getAll");	
	}
	
	
}