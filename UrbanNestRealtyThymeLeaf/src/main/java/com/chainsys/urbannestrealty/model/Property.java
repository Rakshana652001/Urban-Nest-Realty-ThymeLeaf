package com.chainsys.urbannestrealty.model;


import java.util.Arrays;


public class Property
{
	String sellerId;
	String sellerName;
    String propertyName;
    String propertyId;
    long   propertyPrice;
    String propertyAddress;                                      
    String propertyDistrict;
    String propertyState;
    byte[] propertyImages;
    byte[] propertyDocument;
    String approval;
    String registerStatus;
    String paymentStatus;
    String customerId;
    String registeredDate;
    String purchasedDate;
    String fromDate;
    String toDate;
	private String base64Image;
	private String base64Document;
	long accountNumber;
	double payableAmount;
    
    
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public long getPropertyPrice() {
		return propertyPrice;
	}
	public void setPropertyPrice(long propertyPrice) {
		this.propertyPrice = propertyPrice;
	}
	public String getPropertyAddress() {
		return propertyAddress;
	}
	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}
	public String getPropertyDistrict() {
		return propertyDistrict;
	}
	public void setPropertyDistrict(String propertyDistrict) {
		this.propertyDistrict = propertyDistrict;
	}
	public String getPropertyState() {
		return propertyState;
	}
	public void setPropertyState(String propertyState) {
		this.propertyState = propertyState;
	}
	public byte[] getPropertyImages() {
		return propertyImages;
	}
	public void setPropertyImages(byte[] propertyImages) {
		this.propertyImages = propertyImages;
	}
	public byte[] getPropertyDocument() {
		return propertyDocument;
	}
	public void setPropertyDocument(byte[] propertyDocument) {
		this.propertyDocument = propertyDocument;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getRegisterStatus() {
		return registerStatus;
	}
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public String getPurchasedDate() {
		return purchasedDate;
	}
	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
    
    public String getBase64Document()
    {
    	return base64Document;
    }
	public void setBase64Document(String getDocument) 
	{
		this.base64Document = getDocument;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	@Override
	public String toString() {
		return "Property [sellerId=" + sellerId + ", sellerName=" + sellerName + ", propertyName=" + propertyName
				+ ", propertyId=" + propertyId + ", propertyPrice=" + propertyPrice + ", propertyAddress="
				+ propertyAddress + ", propertyDistrict=" + propertyDistrict + ", propertyState=" + propertyState
				+ ", propertyImages=" + Arrays.toString(propertyImages) + ", propertyDocument="
				+ Arrays.toString(propertyDocument) + ", approval=" + approval + ", registerStatus=" + registerStatus
				+ ", paymentStatus=" + paymentStatus + ", customerId=" + customerId + ", registeredDate="
				+ registeredDate + ", purchasedDate=" + purchasedDate + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", base64Image=" + base64Image + ", base64Document=" + base64Document + ", accountNumber="
				+ accountNumber + ", payableAmount=" + payableAmount + "]";
	}
	
}