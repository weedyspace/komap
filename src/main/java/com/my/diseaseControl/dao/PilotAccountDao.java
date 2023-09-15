package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.Project;



public class PilotAccountDao extends SqlSessionDaoSupport{

	public List<PilotAccount> getAll() {
		return this.getSqlSession().selectList("pilotAccount.getAll");
	}
	
	public PilotAccount selectPilotAccountByLicenseNumberORPhoneNumber(PilotAccount pilotAccount) {
		return this.getSqlSession().selectOne("pilotAccount.selectPilotAccountByLicenseNumberORPhoneNumber", pilotAccount);
	}	

	public int insertNewPilotAccount(PilotAccount newPilotAccount) {
		return this.getSqlSession().insert("pilotAccount.insertNewPilotAccount", newPilotAccount);	
	}

	public int updatePilotAccount(PilotAccount pilotAccount) {
		return this.getSqlSession().insert("pilotAccount.updatePilotAccount", pilotAccount);
	}
	
	public int deletePilotAccount(PilotAccount pilotAccount) {
		return this.getSqlSession().insert("pilotAccount.deletePilotAccount", pilotAccount);
	}
	
	public int lockPilotAccount(PilotAccount pilotAccount) {
		return this.getSqlSession().insert("pilotAccount.lockPilotAccount", pilotAccount);	
	}
	
	public int unlockPilotAccount(PilotAccount pilotAccount) {
		return this.getSqlSession().insert("pilotAccount.unlockPilotAccount", pilotAccount);	
	}
	
	public PilotAccount selectPilotAccountByLicenseNumberAndPhoneNumber(PilotAccount pilotAccount) {
		return this.getSqlSession().selectOne("pilotAccount.selectPilotAccountByLicenseNumberAndPhoneNumber", pilotAccount);
	}	
	
	public PilotAccount selectPilotAccountByLicenseNumberAndName(PilotAccount pilotAccount) {
		return this.getSqlSession().selectOne("pilotAccount.selectPilotAccountByLicenseNumberAndName", pilotAccount);
	}
	
	public PilotAccount getPilotAccountByLicenseNumber(String LicenseNumber) {
		return this.getSqlSession().selectOne("pilotAccount.selectPilotAccountByLicenseNumber", LicenseNumber);
	}
	
	public PilotAccount getPilotAccountByPhoneNumber(String PhoneNumber) {
		return this.getSqlSession().selectOne("pilotAccount.selectPilotAccountByPhoneNumber", PhoneNumber);
	}
	
	public List<PilotAccount> getPilotAccountByName(String Name) {
		return this.getSqlSession().selectList("pilotAccount.selectPilotAccountByName", Name);
	}
	
	public List<PilotAccount> getPilotAccountByCarNumber(String CarNumber) {
		return this.getSqlSession().selectList("pilotAccount.selectPilotAccountByCarNumber", CarNumber);
	}
	
	public List<PilotAccount> getPilotAccountByDrone(String Drone) {
		return this.getSqlSession().selectList("pilotAccount.selectPilotAccountByDrone", Drone);
	}
	
	
}

