package com.my.diseaseControl.vo;

public class Project {
    private int ProjectID = 0;
	private String ProjectName = null;
	private String ProjectDeadLine = null;
	private int GISID = 0;
	private String LicenseNumber = null;
	private String ProjectMakedDate = null;
	private String ProjectStartTime = null;
	private String ProjectEndTime = null;
	private String Address = null;
	private int AdminAccountIndex = 0;
	private String DroneFlyRouteJsonUrl = null;
	private int GeojsonIndex = 0;
	private String Status = null;
	
	//프로젝트를 할당한 관리자의 정보
	private String Position = null;
	private String Name = null;
	
	
	
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getProjectDeadLine() {
		return ProjectDeadLine;
	}
	public void setProjectDeadLine(String projectDeadLine) {
		ProjectDeadLine = projectDeadLine;
	}
	public int getGISID() {
		return GISID;
	}
	public void setGISID(int gISID) {
		GISID = gISID;
	}
	public String getLicenseNumber() {
		return LicenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		LicenseNumber = licenseNumber;
	}
	public String getProjectMakedDate() {
		return ProjectMakedDate;
	}
	public void setProjectMakedDate(String projectMakedDate) {
		ProjectMakedDate = projectMakedDate;
	}
	public String getProjectStartTime() {
		return ProjectStartTime;
	}
	public void setProjectStartTime(String projectStartTime) {
		ProjectStartTime = projectStartTime;
	}
	public String getProjectEndTime() {
		return ProjectEndTime;
	}
	public void setProjectEndTime(String projectEndTime) {
		ProjectEndTime = projectEndTime;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getAdminAccountIndex() {
		return AdminAccountIndex;
	}
	public void setAdminAccountIndex(int adminAccountIndex) {
		AdminAccountIndex = adminAccountIndex;
	}
	public String getDroneFlyRouteJsonUrl() {
		return DroneFlyRouteJsonUrl;
	}
	public void setDroneFlyRouteJsonUrl(String droneFlyRouteJsonUrl) {
		DroneFlyRouteJsonUrl = droneFlyRouteJsonUrl;
	}
	public int getGeojsonIndex() {
		return GeojsonIndex;
	}
	public void setGeojsonIndex(int geojsonIndex) {
		GeojsonIndex = geojsonIndex;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}