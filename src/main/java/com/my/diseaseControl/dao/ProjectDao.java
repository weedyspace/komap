package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.PilotAccount;
import com.my.diseaseControl.vo.Project;



public class ProjectDao extends SqlSessionDaoSupport{

	public List<Project> getAll() {
		return this.getSqlSession().selectList("projectTable.getAll");
	}
	
	public int insertNewProject(Project newProject) {
		return this.getSqlSession().insert("projectTable.insertNewProject", newProject);
	}	
	
//	public List<Project> getPreOngoingProjectsByPilot(Project licenseAndStatus) {
//		return this.getSqlSession().selectList("projectTable.getPreOngoingProjectsByPilot", licenseAndStatus);
//	}	
//	
//	public List<Project> getOngoingProjectsByPilot(Project licenseAndStatus) {
//		return this.getSqlSession().selectList("projectTable.getOngoingProjectsByPilot", licenseAndStatus);
//	}
//	
//	public List<Project> getCompletedProjectsByPilot(Project licenseAndStatus) {
//		return this.getSqlSession().selectList("projectTable.getCompletedProjectsByPilot", licenseAndStatus);
//	}	
//	
//	public List<Project> getOnholdProjectsByPilot(Project licenseAndStatus) {
//		return this.getSqlSession().selectList("projectTable.getOnholdProjectsByPilot", licenseAndStatus);
//	}	
//	
//	public List<Project> getCanceledProjectsByPilot(Project licenseAndStatus) {
//		return this.getSqlSession().selectList("projectTable.getCanceledProjectsByPilot", licenseAndStatus);
//	}
	
	public List<Project> getProjectsByPilotAndStatus(Project licenseAndStatus) {
	return this.getSqlSession().selectList("projectTable.getProjectsByPilotAndStatus", licenseAndStatus);
}	
	
	public List<Project> getAllOngoingProject() {
		return this.getSqlSession().selectList("projectTable.getAllOngoingProject");
	}

	public List<Project> getAllOngoingPlusCompletedProject() {
		return this.getSqlSession().selectList("projectTable.getAllOngoingPlusCompletedProject");
	}
	
	public List<Project> getAllOngoingPlusCompletedProjectByLicense(String License) {
		return this.getSqlSession().selectList("projectTable.getAllOngoingPlusCompletedProjectByLicense", License);
	}	
	
	public List<Project> getAllPreOngoingProject() {
		return this.getSqlSession().selectList("projectTable.getAllPreOngoingProject");
	}
	
	public List<Project> getAllCompletedProject() {
		return this.getSqlSession().selectList("projectTable.getAllCompletedProject");
	}
	
	public List<Project> getAllOnHoldProject() {
		return this.getSqlSession().selectList("projectTable.getAllOnHoldProject");
	}
	
	public List<Project> getAllCanceledProject() {
		return this.getSqlSession().selectList("projectTable.getAllCanceledProject");
	}
	
	public Project getProjectByProjectName(String ProjectName) {
		return this.getSqlSession().selectOne("projectTable.getProjectByProjectName", ProjectName);
	}
	
	public List<Project> getProjectByProjectDeadLine(String ProjectDeadLine) {
		return this.getSqlSession().selectList("projectTable.getProjectByProjectDeadLine", ProjectDeadLine);
	}
	
	public Project getProjectByGISID(int GISID) {
		return this.getSqlSession().selectOne("projectTable.getProjectByGISID", GISID);
	}
	
	public List<Project> getProjectByLicenseNumber(String LicenseNumber) {
		return this.getSqlSession().selectList("projectTable.getProjectByLicenseNumber", LicenseNumber);
	}
	
	public List<Project> getProjectByProjectStartTime(String ProjectStartTime) {
		return this.getSqlSession().selectList("projectTable.getProjectByProjectStartTime", ProjectStartTime);
	}	
	
	public List<Project> getProjectByAdminAccountIndex(int AdminAccountIndex) {
		return this.getSqlSession().selectList("projectTable.getProjectByAdminAccountIndex", AdminAccountIndex);
	}
	
	public List<Project> getProjectByGeojsonIndex(int GeojsonIndex) {
		return this.getSqlSession().selectList("projectTable.getProjectByGeojsonIndex", GeojsonIndex);
	}

	
}