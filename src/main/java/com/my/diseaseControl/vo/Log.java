package com.my.diseaseControl.vo;


public class Log {
	
	private int LogIndex = 0;
	private String LogContent = null;
	private String ActionType = null;
	private String Activator = null;
	private String Responder = null;
	
	
	
	public int getLogIndex() {
		return LogIndex;
	}
	public void setLogIndex(int logIndex) {
		LogIndex = logIndex;
	}
	public String getLogContent() {
		return LogContent;
	}
	public void setLogContent(String logContent) {
		LogContent = logContent;
	}
	public String getActionType() {
		return ActionType;
	}
	public void setActionType(String actionType) {
		ActionType = actionType;
	}
	public String getActivator() {
		return Activator;
	}
	public void setActivator(String activator) {
		Activator = activator;
	}
	public String getResponder() {
		return Responder;
	}
	public void setResponder(String responder) {
		Responder = responder;
	}
	
	
}