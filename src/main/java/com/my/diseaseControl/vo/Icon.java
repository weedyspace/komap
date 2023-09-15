package com.my.diseaseControl.vo;

public class Icon {
	
	private int IconIndex = 0;
	private String IconImageUrl = null;
	private String Explanation = null;
	private int AdminAccountIndex = 0;
	
	
	
	public int getIconIndex() {
		return IconIndex;
	}
	public void setIconIndex(int iconIndex) {
		IconIndex = iconIndex;
	}
	public String getIconImageUrl() {
		return IconImageUrl;
	}
	public void setIconImageUrl(String iconImageUrl) {
		IconImageUrl = iconImageUrl;
	}
	public String getExplanation() {
		return Explanation;
	}
	public void setExplanation(String explanation) {
		Explanation = explanation;
	}
	public int getAdminAccountIndex() {
		return AdminAccountIndex;
	}
	public void setAdminAccountIndex(int adminAccountIndex) {
		AdminAccountIndex = adminAccountIndex;
	}
	
}