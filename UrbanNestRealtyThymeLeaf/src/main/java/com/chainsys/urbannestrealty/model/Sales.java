package com.chainsys.urbannestrealty.model;

import java.util.Arrays;

public class Sales 
{
	String customerId, sellerId, propertyId, propertyName;
	long totalAmount;
	String paymentMethod;
	String approval;
	String propertyAddress;
	byte[] governmentId;
	double payableAmount;
	String paidStatus;
	long customerAccount;
	long accountNumber;
	String purchasedDate;
	String sellerName;
	String customerName;
	String base64;
	
	String govId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public byte[] getGovernmentId() {
		return governmentId;
	}

	public void setGovernmentId(byte[] governmentId) {
		this.governmentId = governmentId;
	}

	public double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public long getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(long customerAccount) {
		this.customerAccount = customerAccount;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}

	public String getGovId() {
		return govId;
	}

	public void setGovId(String govId) {
		this.govId = govId;
	}

	@Override
	public String toString() {
		return "Sales [customerId=" + customerId + ", sellerId=" + sellerId + ", propertyId=" + propertyId
				+ ", propertyName=" + propertyName + ", totalAmount=" + totalAmount + ", paymentMethod=" + paymentMethod
				+ ", approval=" + approval + ", propertyAddress=" + propertyAddress + ", governmentId="
				+ Arrays.toString(governmentId) + ", payableAmount=" + payableAmount + ", paidStatus=" + paidStatus
				+ ", customerAccount=" + customerAccount + ", accountNumber=" + accountNumber + ", purchasedDate="
				+ purchasedDate + ", sellerName=" + sellerName + ", customerName=" + customerName + ", base64=" + base64
				+ ", govId=" + govId + "]";
	}
	
	
}