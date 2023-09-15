package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.AdminAccount;
import com.my.diseaseControl.vo.Project;



public class AdminAccountDao extends SqlSessionDaoSupport{
	
	public List<AdminAccount> getAll() {
		return this.getSqlSession().selectList("adminAccount.getAll");
	}
	
	public AdminAccount selectAdminAccount(AdminAccount adminAccount) {
		return this.getSqlSession().selectOne("adminAccount.selectAdminAccount", adminAccount);
	}	
	
	public int insertAdminAccount(AdminAccount newAdmin) {
		return this.getSqlSession().insert("adminAccount.insertAdminAccount", newAdmin);	
	}	
	
	public int updateAdminAccount(AdminAccount updatedAdmin) {
		return this.getSqlSession().insert("adminAccount.updateAdminAccount", updatedAdmin);	
	}

	public int deleteAdminAccount(AdminAccount adminAccount) {
		return this.getSqlSession().insert("adminAccount.deletePilotAccount", adminAccount);
	}	
	
	public AdminAccount selectAdminAccountByAdminIDAndPassword(AdminAccount adminAccount) {
		return this.getSqlSession().selectOne("adminAccount.selectAdminAccountByAdminIDAndPassword", adminAccount);
	}

	//프로젝트로부터 일치하는 해당 라이센스번호와 ongoing인 컬럼들을 모두 가져오기 
	public List<Project> getAllprojectInfo_adminInfo_byLicenseNumber(String LicenseNumber) {
		return this.getSqlSession().selectList("adminAccount.getAllprojectInfo_adminInfo_byLicenseNumber", LicenseNumber);
	}

	public AdminAccount getAdminInfoByIndex(int adminAccountIndex) {
		return this.getSqlSession().selectOne("adminAccount.getAdminInfoByIndex", adminAccountIndex);
	}

	public AdminAccount getAdminAccountByProjectID(String AdminID) {
		return this.getSqlSession().selectOne("adminAccount.getAdminAccountByProjectID", AdminID);
	}
	
	public AdminAccount getAdminAccountByPhoneNumber(String PhoneNumber) {
		return this.getSqlSession().selectOne("adminAccount.getAdminAccountByPhoneNumber", PhoneNumber);
	}
	
	public List<AdminAccount> getAdminAccountByName(String Name) {
		return this.getSqlSession().selectList("adminAccount.getAdminAccountByName", Name);
	}
	
	public List<AdminAccount> getAdminAccountByPosition(String Position) {
		return this.getSqlSession().selectList("adminAccount.getAdminAccountByPosition", Position);
	}
	
	public List<AdminAccount> getAdminAccountByType(String Type) {
		return this.getSqlSession().selectList("adminAccount.getAdminAccountByType", Type);
	}
	
	public List<AdminAccount> getAdminAccountByAccountCreationDeletionAbility(Boolean AccountCreationDeletionAbility) {
		return this.getSqlSession().selectList("adminAccount.getAdminAccountByAccountCreationDeletionAbility", AccountCreationDeletionAbility);
	}
	
}