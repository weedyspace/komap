package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.WorkImageData;



public class WorkImageDataDao extends SqlSessionDaoSupport{
	
	public int insertWorkImageData(WorkImageData workImageData) {
		return this.getSqlSession().insert("workImageData.insertWorkImageData", workImageData);
	}
	
	public List<WorkImageData> getAll() {
		return this.getSqlSession().selectList("workImageData.getAll");
	}	
	
	public List<WorkImageData> getWorkImageDataByProjectID(int ProjectID) {
		return this.getSqlSession().selectList("workImageData.getWorkImageDataByProjectID", ProjectID);
	}
	
	public List<WorkImageData> getWorkImageDataByImageType(String ImageType) {
		return this.getSqlSession().selectList("workImageData.getWorkImageDataByImageType", ImageType);
	}
	
	
	
}