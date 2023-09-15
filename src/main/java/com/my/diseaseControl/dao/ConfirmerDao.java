package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.AreaGeojson;
import com.my.diseaseControl.vo.Confirmer;



public class ConfirmerDao extends SqlSessionDaoSupport{
	
	public int insertConfirmer(Confirmer confirmer) {
		return this.getSqlSession().insert("confirmer.insertConfirmer", confirmer);	
	}
	
	public List<Confirmer> getAll() {
		return this.getSqlSession().selectList("confirmer.getAll");
	}	
	
	public List<Confirmer> getApprovedConfirmer() {
		return this.getSqlSession().selectList("confirmer.getApprovedConfirmer");	
	}
	
	public List<Confirmer> getUnapprovedConfirmer() {
		return this.getSqlSession().selectList("confirmer.getUnapprovedConfirmer");	
	}
	
	public List<Confirmer> getConfirmerByAdminAccountIndex(int AdminAccountIndex) {
		return this.getSqlSession().selectList("confirmer.getConfirmerByAdminAccountIndex", AdminAccountIndex);	
	}
	
	public List<Confirmer> getConfirmerByProjectID(int ProjectID) {
		return this.getSqlSession().selectList("confirmer.getConfirmerByProjectID", ProjectID);	
	}
	
}