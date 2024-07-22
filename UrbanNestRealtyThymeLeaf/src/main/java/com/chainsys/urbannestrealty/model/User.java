package com.chainsys.urbannestrealty.model;

public class User 
{
	String generatedUserID;
    String name;
    long   phoneNumber;
    String emailID;
    String password;
    String address;
    String district;
    String state;    		    
    String designation;
    
    public User() 
    {
		
	}
	public String getGeneratedUserID() {
		return generatedUserID;
	}
	
	public void setGeneratedUserID(String generatedUserID) {
		this.generatedUserID = generatedUserID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	@Override
	public String toString() {
		return "User [generatedUserID=" + generatedUserID + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", emailID=" + emailID + ", password=" + password + ", address=" + address + ", district=" + district
				+ ", state=" + state + ", designation=" + designation + "]";
	}
	
	
	
}