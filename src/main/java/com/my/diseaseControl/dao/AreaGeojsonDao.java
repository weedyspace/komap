package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.AreaGeojson;



public class AreaGeojsonDao extends SqlSessionDaoSupport{
	
	public int insertAreaGeojson(AreaGeojson areaGeojson) {
		return this.getSqlSession().insert("areaGeojson.insertAreaGeojson", areaGeojson);	
	}
	
	public List<AreaGeojson> getAll() {
		return this.getSqlSession().selectList("areaGeojson.getAll");
	}	
	
}