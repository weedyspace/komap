package com.my.diseaseControl.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.my.diseaseControl.vo.Issue;





public class IssueDao extends SqlSessionDaoSupport{

	public List<Issue> getAll() {
		return this.getSqlSession().selectList("issueTable.getAll");
	}
	
	public int insertNewIssue(Issue newIssue) {
		return this.getSqlSession().insert("issueTable.insertNewIssue", newIssue);
	}	
	
	public int updateIssue(Issue updatedIssue) {
		return this.getSqlSession().update("issueTable.updateIssue", updatedIssue);
	}	
	
	public List<Issue> getIssueByLicenseNumber(String LicenseNumber) {
		return this.getSqlSession().selectList("issueTable.getIssueByLicenseNumber", LicenseNumber);
	}	
	
	public List<Issue> getIssueByLatAndLng(Issue issue) {
		return this.getSqlSession().selectList("issueTable.getIssueByLatAndLng", issue);
	}	
	
	public List<Issue> getIssueByIssueOccurredTime(String IssueOccurredTime) {
		return this.getSqlSession().selectList("issueTable.getIssueByIssueOccurredTime", IssueOccurredTime);
	}
	
	public List<Issue> getIssueByProjectID(int ProjectID) {
		return this.getSqlSession().selectList("issueTable.getIssueByProjectID", ProjectID);
	}
	
}