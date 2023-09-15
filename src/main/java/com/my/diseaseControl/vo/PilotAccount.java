package com.my.diseaseControl.vo;

public class PilotAccount {
	private int idx = 0;
	private String LicenseNumber = null;
	private String PhoneNumber = null;
	private String Name = null;
	private String CarNumber = null;
	private String Drone = null;
	private String Date = null;
	private Boolean isLocked = false;
	
	
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getLicenseNumber() {
		return LicenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		LicenseNumber = licenseNumber;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCarNumber() {
		return CarNumber;
	}
	public void setCarNumber(String carNumber) {
		CarNumber = carNumber;
	}
	public String getDrone() {
		return Drone;
	}
	public void setDrone(String drone) {
		Drone = drone;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	
}