package com.my.diseaseControl.vo;

public class Confirmer {
	private int ConfirmerIndex = 0;
	private int AdminAccountIndex = 0;
	private Boolean IsApproved = false;
	private int ProjectID = 0;
	
	
	
	public int getConfirmerIndex() {
		return ConfirmerIndex;
	}
	public void setConfirmerIndex(int confirmerIndex) {
		ConfirmerIndex = confirmerIndex;
	}
	public int getAdminAccountIndex() {
		return AdminAccountIndex;
	}
	public void setAdminAccountIndex(int adminAccountIndex) {
		AdminAccountIndex = adminAccountIndex;
	}
	public Boolean getIsApproved() {
		return IsApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		IsApproved = isApproved;
	}
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	} 
	
	
}