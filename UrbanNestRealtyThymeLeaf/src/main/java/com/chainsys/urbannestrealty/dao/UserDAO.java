package com.chainsys.urbannestrealty.dao;

import java.util.List;

import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.Sales;
import com.chainsys.urbannestrealty.model.User;

public interface UserDAO 
{
	void saveUserDetails(User user);
	String getAdminpassword(String generatedUserID);
	String getsellerPassword(String generatedUserID);
	String getCustomerPassword(String generatedUserID);
	List<User> retriveAdminDetails();
	void updateAdmindetails(User user);
	List<User> sellerCustomerDetails();
	List<User> customerDetails();
	List<User> sellerSearch(String id);
	List<User> customerSearch(String id);
	List<User> retriveSellerProfile(String id);
	void updateSellerdetails(User user);
	
	void property(Property property);
	void delete(User user);
	String getsellerId(String generatedUserID);
	String getcustomerId(String generatedUserID);
	List<User> retriveCustomerDetails(String id);
	void updateCustomerdetails(User user);
	List<Property> sellerRegisteredProperties(String sellerId);
	List<Property> pendingProperty();
	void approval(Property property);
	List<Property> residential();
	List<Property> land();
	List<Property> industrial();
	List<Property> commercial();
	List<Property> authorizedProperties();
	void sale(Sales sale);
	List<Sales> propertiesUnderReview(String id);
	void updateCustomerId(Sales sale);
	void registerUpdate(String address, String registerStatus);
	List<Property> properties(String propertyName);
	List<Sales> approveToBuy();
	void updateApproval(String customerId, String approvalStatus);
	List<Sales> readyToBuy(String id);
	void updatePayment(String id, long yourAccountNumber, long senderAccountNumber, 
			String purchasedDate);
	List<Property> registeredProperties();
	List<Sales> completedDeals(String id);
	List<Property> purchasedProperties(String id);
	List<Property> propertiesSearch(String propertyName);
	List<Property> dateFromTo(String fromDate, String toDate);
	List<Property> viewImage(String id);
	List<Sales> customerTransactionHistory(String id);
	List<Sales> sellerHistory(String id);
	List<Sales> sellerDate(String id, String fromDate, String toDate);
	List<Sales> customerDate(String id, String fromDate, String toDate);
	void updateSellerAccount(String propertyAddress, String name, String custName);
	String getsellerName(String generatedUserID);
	String getcustomerName(String generatedUserID);
	List<Property> districtSearch(String district);
	List<Property> sellerProperties(String generatedUserID);
	List<Sales> totalAmount(String generatedUserID);
	List<Sales> viewID(String address);
	List<Property> forDownload(String address);
	List<Sales> download(String address);
	List<Property> viewProperty(String address);
	int landCount();
	int residentialCount();
	int commercialCount();
	int pgCount();
	int showroomCount();
	int plotCount();
	int factoryCount();
	int floorCount();
	String getDistrict();
}