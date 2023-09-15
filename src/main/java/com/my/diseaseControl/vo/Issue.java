package com.my.diseaseControl.vo;

public class Issue {
	private int IssueIndex = 0;
	private String LicenseNumber = null;
	private String ImageUrl = null;
	private String Content = null;
	private int lat = 0;
	private int lng = 0;
	private String IssueOccurredTime = null;
	private int ProjectID = 0;
	private Boolean IsCompleted = false;
	
	
	
	public int getIssueIndex() {
		return IssueIndex;
	}
	public void setIssueIndex(int issueIndex) {
		IssueIndex = issueIndex;
	}
	public String getLicenseNumber() {
		return LicenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		LicenseNumber = licenseNumber;
	}
	public String getImageUrl() {
		return ImageUrl;
	}
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLng() {
		return lng;
	}
	public void setLng(int lng) {
		this.lng = lng;
	}
	public String getIssueOccurredTime() {
		return IssueOccurredTime;
	}
	public void setIssueOccurredTime(String issueOccurredTime) {
		IssueOccurredTime = issueOccurredTime;
	}
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}
	public Boolean getIsCompleted() {
		return IsCompleted;
	}
	public void setIsCompleted(Boolean isCompleted) {
		IsCompleted = isCompleted;
	}
	
	

}