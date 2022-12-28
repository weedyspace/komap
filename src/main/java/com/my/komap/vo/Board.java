package com.my.komap.vo;

public class Board {
	private int board_idx=0;
	private int user_idx=0;
	private String title=null;
	private String content=null;
	private int click_count=0;
	private int recomm_count=0;
	private int decomm_count=0;
	private String reg_date=null;
	private int unix=0;
	
	//user
	private String id = null;
	private String email = null;
	private String user_image = null;
//	private String password = null;
	
	
	
	
	
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getClick_count() {
		return click_count;
	}
	public void setClick_count(int click_count) {
		this.click_count = click_count;
	}
	public int getRecomm_count() {
		return recomm_count;
	}
	public void setRecomm_count(int recomm_count) {
		this.recomm_count = recomm_count;
	}
	public int getDecomm_count() {
		return decomm_count;
	}
	public void setDecomm_count(int decomm_count) {
		this.decomm_count = decomm_count;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getUnix() {
		return unix;
	}
	public void setUnix(int unix) {
		this.unix = unix;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	
}
