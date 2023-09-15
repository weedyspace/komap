package com.my.komap.vo;

public class Reply {
	private String user_image = null;
	private int reply_idx=0;
	private int reply_user_idx=0;
	private int reply_writing_idx = 0;
	private String id = null;
	private String reply_box_content=null;
	private int reply_recomm_count=0;
	private int reply_decomm_count=0;
	private String reply_reg_date=null;
	private int unix=0;
	
	
	
	
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public int getReply_idx() {
		return reply_idx;
	}
	public void setReply_idx(int reply_idx) {
		this.reply_idx = reply_idx;
	}
	public int getReply_user_idx() {
		return reply_user_idx;
	}
	public void setReply_user_idx(int reply_user_idx) {
		this.reply_user_idx = reply_user_idx;
	}
	public int getReply_writing_idx() {
		return reply_writing_idx;
	}
	public void setReply_writing_idx(int reply_writing_idx) {
		this.reply_writing_idx = reply_writing_idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReply_box_content() {
		return reply_box_content;
	}
	public void setReply_box_content(String reply_box_content) {
		this.reply_box_content = reply_box_content;
	}
	public int getReply_recomm_count() {
		return reply_recomm_count;
	}
	public void setReply_recomm_count(int reply_recomm_count) {
		this.reply_recomm_count = reply_recomm_count;
	}
	public int getReply_decomm_count() {
		return reply_decomm_count;
	}
	public void setReply_decomm_count(int reply_decomm_count) {
		this.reply_decomm_count = reply_decomm_count;
	}
	public String getReply_reg_date() {
		return reply_reg_date;
	}
	public void setReply_reg_date(String reply_reg_date) {
		this.reply_reg_date = reply_reg_date;
	}
	public int getUnix() {
		return unix;
	}
	public void setUnix(int unix) {
		this.unix = unix;
	}
	
	
		
}