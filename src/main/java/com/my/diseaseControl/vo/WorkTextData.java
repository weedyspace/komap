package com.my.diseaseControl.vo;


public class WorkTextData {
	
	private int TextDataIndex = 0;
	private int ProjectID  = 0;
	private String DisinfectantName = null;
	private String MixingRatio = null;
	
	
	
	public int getTextDataIndex() {
		return TextDataIndex;
	}
	public void setTextDataIndex(int textDataIndex) {
		TextDataIndex = textDataIndex;
	}
	public int getProjectID() {
		return ProjectID;
	}
	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}
	public String getDisinfectantName() {
		return DisinfectantName;
	}
	public void setDisinfectantName(String disinfectantName) {
		DisinfectantName = disinfectantName;
	}
	public String getMixingRatio() {
		return MixingRatio;
	}
	public void setMixingRatio(String mixingRatio) {
		MixingRatio = mixingRatio;
	}
	
}
