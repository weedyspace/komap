package com.my.diseaseControl.vo;


public class AdminAccount {
	private int AdminAccountIndex = 0;
	private String AdminID = null;
	private String Password = null;
	private String Salt_key = null;
	private String Sha_256_pwd_salt = null;
	private String Name = null;
	private String Position = null;
	private String PhoneNumber = null;
	private String Email = null;
	private String Type = null;
	private Boolean AccountCreationDeletionAbility = true;
	
	
	
	public int getAdminAccountIndex() {
		return AdminAccountIndex;
	}
	public void setAdminAccountIndex(int adminAccountIndex) {
		AdminAccountIndex = adminAccountIndex;
	}
	public String getAdminID() {
		return AdminID;
	}
	public void setAdminID(String adminID) {
		AdminID = adminID;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getSalt_key() {
		return Salt_key;
	}
	public void setSalt_key(String salt_key) {
		Salt_key = salt_key;
	}
	public String getSha_256_pwd_salt() {
		return Sha_256_pwd_salt;
	}
	public void setSha_256_pwd_salt(String sha_256_pwd_salt) {
		Sha_256_pwd_salt = sha_256_pwd_salt;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		Position = position;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Boolean getAccountCreationDeletionAbility() {
		return AccountCreationDeletionAbility;
	}
	public void setAccountCreationDeletionAbility(Boolean accountCreationDeletionAbility) {
		AccountCreationDeletionAbility = accountCreationDeletionAbility;
	}
	

	
}
