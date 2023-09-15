package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.WorkImageData;
import com.my.diseaseControl.vo.WorkTextData;



public class WorkTextDataDao extends SqlSessionDaoSupport{
	
	public int insertWorkTextData(WorkTextData workTextData) {
		return this.getSqlSession().insert("workTextData.insertWorkTextData", workTextData);
	}
	
	public List<WorkTextData> getAll() {
		return this.getSqlSession().selectList("workTextData.getAll");
	}	
	
	public List<WorkTextData> getWorkTextDataByProjectID(int ProjectID) {
		return this.getSqlSession().selectList("workTextData.getWorkTextDataByProjectID", ProjectID);
	}
	
	public List<WorkTextData> getWorkTextDataByDisinfectantName(String DisinfectantName) {
		return this.getSqlSession().selectList("workTextData.getWorkTextDataByDisinfectantName", DisinfectantName);
	}
	
	public List<WorkTextData> getWorkTextDataByMixingRatio(String MixingRatio) {
		return this.getSqlSession().selectList("workTextData.getWorkTextDataByMixingRatio", MixingRatio);
	}
	
}