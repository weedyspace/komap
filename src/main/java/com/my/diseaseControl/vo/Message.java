package com.my.diseaseControl.vo;


public class Message {
	private int MessageIndex = 0;
	private String MessageContent = null;
	private String Activator = null;
	private String Responder = null;
	
	
	
	public int getMessageIndex() {
		return MessageIndex;
	}
	public void setMessageIndex(int messageIndex) {
		MessageIndex = messageIndex;
	}
	public String getMessageContent() {
		return MessageContent;
	}
	public void setMessageContent(String messageContent) {
		MessageContent = messageContent;
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