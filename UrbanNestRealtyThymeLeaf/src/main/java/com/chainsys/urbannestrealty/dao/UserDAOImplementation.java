package com.chainsys.urbannestrealty.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.chainsys.urbannestrealty.mapper.ApproveSalesMapper;
import com.chainsys.urbannestrealty.mapper.ClosedPropertyMapper;
import com.chainsys.urbannestrealty.mapper.CompletedDealsMapper;
import com.chainsys.urbannestrealty.mapper.CustomerHistoryMapper;
import com.chainsys.urbannestrealty.mapper.ImageMapper;
import com.chainsys.urbannestrealty.mapper.PropertyMapper;
import com.chainsys.urbannestrealty.mapper.PropertyUserDisplayMapper;
import com.chainsys.urbannestrealty.mapper.PurchasedPropertiesMapper;
import com.chainsys.urbannestrealty.mapper.ReadyToBuyMapper;
import com.chainsys.urbannestrealty.mapper.SalesMapper;
import com.chainsys.urbannestrealty.mapper.SellerHistoryMapper;
import com.chainsys.urbannestrealty.mapper.Total;
import com.chainsys.urbannestrealty.mapper.UserMapper;
import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.Sales;
import com.chainsys.urbannestrealty.model.User;

@Repository
public class UserDAOImplementation implements UserDAO
{
	@Autowired
	JdbcTemplate jdbcTemplate;
	UserMapper mapper;
	PropertyUserDisplayMapper propertyUserDisplayMapper;
	ApproveSalesMapper approveSalesMapper;
	CompletedDealsMapper completedDealsMapper;
	PurchasedPropertiesMapper purchasedPropertiesMapper;
	ImageMapper image;
	CustomerHistoryMapper customerHistory;
	SellerHistoryMapper sellerHistoryMapper;
	Total total;
	
	@Override
	public void saveUserDetails(User user)
	{
		String insert = "insert into user (id, name, phone_number, designation, email_id, password, address, district, state)values(?,?,?,?,?,?,?,?,?)";
		Object[] params = {user.getGeneratedUserID() ,user.getName(),user.getPhoneNumber(), user.getDesignation(),  user.getEmailID(),user.getPassword(), user.getAddress(), user.getDistrict(), user.getState()};
		jdbcTemplate.update(insert, params);
	}

	@Override
	public String getAdminpassword(String generatedUserID) 
	{
		String adminPassword = "select password from user where id=? and designation = 'Admin' and deleted_User=0";
		String password = jdbcTemplate.queryForObject(adminPassword, String.class, generatedUserID);
		return password;
	}
	
	@Override
	public String getsellerId(String generatedUserID)
	{
		String sellerId = "select id from user where id=? and designation = 'Seller' and deleted_User=0";
		String seller = jdbcTemplate.queryForObject(sellerId, String.class, generatedUserID);
		return seller;
	}
	
	@Override
	public String getcustomerId(String generatedUserID)
	{
		String sellerId = "select id from user where id=? and designation = 'Customer' and deleted_User=0";
		String seller = jdbcTemplate.queryForObject(sellerId, String.class,generatedUserID);
		return seller;
	}
	
	@Override
	public String getsellerPassword(String generatedUserID) 
	{
		String password = "select password from user where id=? and designation = 'Seller' and deleted_User=0";
		String sellerPassword = jdbcTemplate.queryForObject(password, String.class, generatedUserID);
		return sellerPassword;
	}

	@Override
	public String getCustomerPassword(String generatedUserID) 
	{
		String password = "select password from user where id=? and designation = 'Customer' and deleted_User=0";
		String customerPassword = jdbcTemplate.queryForObject(password, String.class, generatedUserID);
		return customerPassword;
	}

	@Override
	public List<User> retriveAdminDetails() 
	{
		String retriveAdminDetails = "select id, name, phone_number, designation, email_id, address, district, state from user where id='UNR_Admin_1' and deleted_user=0";
		List<User> list = jdbcTemplate.query(retriveAdminDetails, new UserMapper());
		return list;
	}
	
	@Override
	public List<User> retriveCustomerDetails(String id) 
	{
		String retriveAdminDetails = "select id, name, phone_number, designation, email_id, address, district, state from user where id=? and designation='Customer' and deleted_user=0";
		List<User> list = jdbcTemplate.query(retriveAdminDetails, new UserMapper(), id);
		return list;
	}

	@Override
	public void updateAdmindetails(User user) 
	{
		String update = "update user set phone_number=?, password=?, address=? where name=?";
		Object[] params = {user.getPhoneNumber(), user.getPassword(), user.getAddress(), user.getName()};
		jdbcTemplate.update(update, params);
	}

	@Override
	public void updateSellerdetails(User user) {
		String update = "update user set phone_number=?, password=?, address=? where name=?";
		Object[] params = {user.getPhoneNumber(), user.getPassword(), user.getAddress(), user.getName()};
		jdbcTemplate.update(update, params);
		
	}	
	
	@Override
	public void updateCustomerdetails(User user) 
	{
		String update = "update user set phone_number=?, password=?, address=? where name=?";
		Object[] params = {user.getPhoneNumber(), user.getPassword(), user.getAddress(), user.getName()};
		jdbcTemplate.update(update, params);
		
	}
	
	@Override
	public List<User> sellerCustomerDetails() 
	{
		String retriveDetails = "select id, name, phone_number, designation, email_id, address, district, state from user where deleted_User=0 and (designation = 'Seller')";
		List<User> list = jdbcTemplate.query(retriveDetails, new UserMapper());
		return list;
	}

	@Override
	public List<User> customerDetails() 
	{
		String retriveDetails = "select id, name, phone_number, designation, email_id, address, district, state from user where deleted_User=0 and (designation = 'Customer')";
		List<User> list = jdbcTemplate.query(retriveDetails, new UserMapper());
		return list;
	}

	@Override
	public List<User> sellerSearch(String id) 
	{
		String retriveDetails = String.format("select id, name, phone_number, designation, email_id, address, district, state from user where (id like '%%%s%%' or name like '%%%s%%' or email_id like '%%%s%%') and deleted_User=0 and designation='seller'",id,id,id);
		List<User> list = jdbcTemplate.query(retriveDetails, new UserMapper());
		return list;	
	}
	
	@Override
	public List<User> customerSearch(String id) 
	{
		String retriveDetails = String.format("select id, name, phone_number, designation, email_id, address, district, state from user where (id like '%%%s%%' or name like '%%%s%%' or email_id like '%%%s%%') and deleted_User=0 and designation='customer'",id,id,id);
		List<User> list = jdbcTemplate.query(retriveDetails, new UserMapper());
		return list;
	}

	@Override
	public List<User> retriveSellerProfile(String id)
	{
		String retriveAdminDetails = "select id, name, phone_number, designation, email_id, address, district, state from user where id=? and designation='seller' and deleted_user=0";
		List<User> list = jdbcTemplate.query(retriveAdminDetails, new UserMapper(), id);
		return list;
	}

	@Override
	public void property(Property property) 
	{
		String insert = "insert into property_registration (seller_id,seller_name,property_name, property_id, approval, property_images, property_document, property_price, property_address, property_district, property_state, registered_date, customer_id, register_status, payment_status, account_number, payable_amount) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {property.getSellerId(), property.getPropertyName(), property.getPropertyId(), property.getApproval(), property.getPropertyImages(), property.getPropertyDocument(),property.getPropertyPrice(),
		property.getPropertyAddress(), property.getPropertyDistrict(), property.getPropertyState(), property.getRegisteredDate(),property.getCustomerId(), property.getRegisterStatus(), property.getPaymentStatus(),property.getAccountNumber(),property.getPayableAmount(), property.getSellerName()};
		jdbcTemplate.update(insert, params);	
	}

	@Override
	public void delete(User user)
	{
		String delete = "update user set deleted_User=1 where name=?";
		Object[] name = {user.getName()};
		jdbcTemplate.update(delete, name);		
	}

	@Override
	public List<Property> sellerRegisteredProperties(String sellerId) 
	{
		String select = "select seller_id, property_name, property_id, approval, property_images, property_document, property_price, property_address, property_district, property_state, registered_date, purchased_date, customer_id, register_status,payment_status from property_registration where seller_id=? and (approval='Approved' or approval='Rejected' or approval='Not Approved') and payment_status='Not Paid'";
		List<Property> list = jdbcTemplate.query(select, new PropertyMapper(), sellerId);
		return list;
	}
	
	@Override
	public List<Property> sellerProperties(String sellerId)
	{
		String select = "select seller_id, property_name, property_id, approval, property_images, property_document, property_price, property_address, property_district, property_state, registered_date, purchased_date, customer_id, register_status,payment_status from property_registration where seller_id=?";
		List<Property> list = jdbcTemplate.query(select, new PropertyMapper(), sellerId);
		return list;
	}

	@Override
	public void approval(Property property) 
	{
		String update = "update property_registration set approval=? where property_address=?";
		Object[] approval = {property.getApproval(),property.getPropertyAddress()};
		jdbcTemplate.update(update, approval);
		
	}

	@Override
	public List<Property> residential() 
	{
		String residential = "select seller_id,seller_name, property_name, property_id, approval, property_images, property_price, property_address, property_district, property_state, registered_date,account_number from property_registration where property_id=101 and deleted_User=0 and approval='Approved' and register_status='Not Registered'";
		List<Property> list = jdbcTemplate.query(residential, new PropertyUserDisplayMapper());
		return list;
	}

	@Override
	public List<Property> land() {
		String residential = "select seller_id, seller_name, property_name, property_id, approval, property_images, property_price, property_address, property_district, property_state, registered_date,account_number from property_registration where property_id=102 and deleted_User=0 and approval='Approved' and register_status='Not Registered'";
		List<Property> list = jdbcTemplate.query(residential, new PropertyUserDisplayMapper());
		return list;
	}

	@Override
	public List<Property> industrial() {
		String residential = "select seller_id, seller_name,property_name, property_id, approval, property_images, property_price, property_address, property_district, property_state, registered_date,account_number from property_registration where property_id=103 and deleted_User=0 and approval='Approved' and register_status='Not Registered'";
		List<Property> list = jdbcTemplate.query(residential, new PropertyUserDisplayMapper());
		return list;
	}

	@Override
	public List<Property> commercial() {
		String residential = "select seller_id,seller_name, property_name, property_id, approval, property_images, property_price, property_address, property_district, property_state, registered_date,account_number from property_registration where property_id=104 and deleted_User=0 and approval='Approved' and register_status='Not Registered'";
		List<Property> list = jdbcTemplate.query(residential, new PropertyUserDisplayMapper());
		return list;
	}

	@Override
	public List<Property> pendingProperty() 
	{
		String select = "select seller_id, property_name, property_id, approval, property_images, property_document, property_price, property_address, property_district, property_state, registered_date, purchased_date, customer_id, register_status,payment_status from property_registration where approval='Not Approved'";
		List<Property> list = jdbcTemplate.query(select, new PropertyMapper());
		return list;
	}
	
	@Override
	public List<Property> authorizedProperties()
	{
		String select = "select seller_id, property_name, property_id, approval, property_images, property_document, property_price, property_address, property_district, property_state, registered_date, purchased_date, customer_id, register_status,payment_status from property_registration where approval='Approved' and register_status='Not Registered'";
		List<Property> list = jdbcTemplate.query(select, new PropertyMapper());
		return list;
	}

	@Override
	public void sale(Sales sale) 
	{
		String insert = "insert into sales_record (customer_id,seller_id, government_id, approval, property_address, payment_method, total_amount, payabel_amount, customer_account, seller_account, payed_status) values (?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {sale.getCustomerId(), sale.getSellerId(), sale.getGovernmentId(), sale.getApproval(), sale.getPropertyAddress(), sale.getPaymentMethod(), sale.getTotalAmount(), sale.getPayableAmount(), sale.getCustomerAccount(), sale.getAccountNumber(), sale.getPaidStatus()};
		jdbcTemplate.update(insert, params);
	}

	@Override
	public List<Sales> propertiesUnderReview(String id)
	{
		String retrive = "select customer_id,seller_id, government_id, approval, property_address, payment_method, total_amount, payabel_amount, payed_status from sales_record where customer_id=? and payed_status='Not Paid' and approval='Not Approved'";
		List<Sales> list = jdbcTemplate.query(retrive, new SalesMapper(), id);
		return list;
	}

	@Override
	public void updateCustomerId(String customerId,String propertyAddress,String customerName) 
	{
		String update = "update property_registration set customer_id=?, customer_name=? where property_address=?";
		Object[] params = {customerId, customerName,propertyAddress};
		jdbcTemplate.update(update, params);
		
	}

	@Override
	public void registerUpdate(String address, String registerStatus)
	{
		String update = "update property_registration set register_status=? where property_address=?";
		Object[] params = {registerStatus,address};
		jdbcTemplate.update(update, params);
	}

	@Override
	public List<Property> properties(String propertyName, String propertyDistrict)
	{
		String residential = String.format( "select seller_id, property_name, property_id, approval, property_images, property_price, property_address, property_district, property_state, registered_date from property_registration where (property_name like '%%%s%%' OR property_district like '%%%s%%') and deleted_User=0 and approval='Approved' and register_status='Not Registered'",propertyName, propertyDistrict);
		List<Property> list = jdbcTemplate.query(residential, new PropertyUserDisplayMapper());
		return list;		
	}

	@Override
	public List<Sales> approveToBuy()
	{
		String retrive = "select customer_id, seller_id, property_address, payment_method, total_amount, payabel_amount, approval, government_id,payed_status from sales_record where payed_status='Not Paid' and deleted_User=0";
		List<Sales> list = jdbcTemplate.query(retrive, new ApproveSalesMapper());
		return list;
	}

	@Override
	public void updateApproval(String customerId, String approvalStatus) 
	{
		String update = "update sales_record set approval=? where customer_id=?";
		Object[] params = {approvalStatus, customerId};
		jdbcTemplate.update(update, params);		
	}

	@Override
	public List<Sales> readyToBuy(String id) 
	{
		String retrive = "select customer_id, seller_name, property_address ,property_price, payable_amount ,account_number from property_registration where customer_id=? and deleted_User=0 and payment_status='Not Paid'";
		List<Sales> list = jdbcTemplate.query(retrive, new ReadyToBuyMapper(), id);
		return list;		
	}

	@Override
	public void updatePayment(String address, long yourAccountNumber, long senderAccountNumber,
			String purchasedDate)
	{
		String update= "update sales_record set customer_account=?, seller_account=?, purchased_date=?, payed_status=? where property_address=?";
		Object[] params = {yourAccountNumber, senderAccountNumber, purchasedDate, "Paid",address};
		jdbcTemplate.update(update,params);
		
		String updatePropertyTable = "update property_registration set purchased_date=?, payment_status=? where property_address=?";
		Object[] params1 = {purchasedDate, "Paid", address};
		jdbcTemplate.update(updatePropertyTable, params1);
	}

	@Override
	public List<Property> registeredProperties()
	{
		String retrive = "select seller_id, property_name, approval, property_images, property_price, property_address, property_district, property_state, registered_date, purchased_date, customer_id, register_status, payment_status from property_registration where payment_status='Paid'";
		List<Property> list = jdbcTemplate.query(retrive, new ClosedPropertyMapper());
		
		return list;
	}

	@Override
	public List<Sales> completedDeals(String id) 
	{
		String retrive = "select customer_name, government_id, property_address, payment_method, total_amount, payabel_amount, customer_account, seller_account, purchased_date, payed_status from sales_record where seller_id=? and payed_status = 'Paid'";
		List<Sales> list = jdbcTemplate.query(retrive, new CompletedDealsMapper(), id);
		return list;
	}
	
	@Override
	public List<Sales> totalAmount(String id)
	{
		String retrive = "select total_amount from sales_record where seller_id=? and payed_status = 'Paid'";
		List<Sales> list = jdbcTemplate.query(retrive, new Total(), id);
		return list;
	}

	@Override
	public List<Property> purchasedProperties(String id)
	{
		String retrive = "select seller_name, property_name, property_document, property_price, property_address, property_district, registered_date, purchased_date, customer_id, register_status, payment_status from property_registration where customer_id=? and payment_status='Paid'";
		List<Property> list = jdbcTemplate.query(retrive, new PurchasedPropertiesMapper(), id);
		return list;
	}

	@Override
	public List<Property> propertiesSearch(String propertyName) 
	{
		String retrive = String.format( "select seller_id, property_name, property_images, property_price, property_address, property_district, property_state, registered_date,purchased_date,customer_id,payment_status from property_registration where (property_name like '%%%s%%') and deleted_User=0 and payment_status='Paid'",propertyName);
		List<Property> list = jdbcTemplate.query(retrive, new ClosedPropertyMapper());
		return list;
	}

	@Override
	public List<Property> dateFromTo(String fromDate, String toDate) 
	{
		String retrive = "select seller_id, property_name, approval, property_images, property_price, property_address, property_district, property_state, registered_date, purchased_date, customer_id, register_status, payment_status from property_registration where payment_status='Paid' and purchased_date>=? and purchased_date<=? and registered_date>=? and registered_date<=?";
		List<Property> list = jdbcTemplate.query(retrive, new ClosedPropertyMapper(),fromDate,toDate,fromDate, toDate);
		return list;
	}


	@Override
	public List<Property> viewImage(String image) 
	{
		String retrive = "select property_images from property_registration where property_images=?";
		List<Property> list = jdbcTemplate.query(retrive, new ImageMapper(), image);
		return list;
	}

	@Override
	public List<Sales> customerTransactionHistory(String id)
	{
		String history = "select seller_name, customer_account, seller_account, payabel_amount, purchased_date from sales_record where customer_id=?";
		List<Sales> list = jdbcTemplate.query(history, new CustomerHistoryMapper(), id);
		return list;
	}

	@Override
	public List<Sales> sellerHistory(String id) 
	{
		String history = "select customer_name, customer_account, seller_account, payabel_amount, purchased_date from sales_record where seller_id=?";
		List<Sales> list = jdbcTemplate.query(history, new SellerHistoryMapper(), id);
		return list;
	}

	@Override
	public List<Sales> sellerDate(String id,String fromDate, String toDate) 
	{
		String history = "select customer_id, customer_account, seller_account, payabel_amount, purchased_date from sales_record where seller_id=? and purchased_date>=? and purchased_date<=?";
		List<Sales> list = jdbcTemplate.query(history, new SellerHistoryMapper(), id, fromDate, toDate);
		return list;
	}

	@Override
	public List<Sales> customerDate(String id, String fromDate, String toDate) 
	{
		String history = "select seller_id, customer_account, seller_account, payabel_amount, purchased_date from sales_record where customer_id=? and purchased_date>=? and purchased_date<=?";
		List<Sales> list = jdbcTemplate.query(history, new CustomerHistoryMapper(), id, fromDate,toDate);
		return list;
	}

	@Override
	public void updateSellerAccount(long accountNumber,String customerName, String address, String name) 
	{
		String update = "update sales_record set seller_account=? , seller_name=?,customer_name where property_address=?";
		Object[] params1 = {update, accountNumber, name, customerName,address};
		jdbcTemplate.update(update, params1);
		
	}
	
	@Override
	public String getsellerName(String generatedUserID)
	{
		String name = "select name from user where id=? and designation = 'Seller' and deleted_User=0";
		String seller = jdbcTemplate.queryForObject(name, String.class, generatedUserID);
		return seller;
	}

	@Override
	public String getcustomerName(String generatedUserID) 
	{
		String name = "select name from user where id=? and designation = 'Customer' and deleted_User=0";
		String customer = jdbcTemplate.queryForObject(name, String.class, generatedUserID);
		return customer;
	}

	@Override
	public List<Property> districtSearch(String district) 
	{		
		String residential = String.format("select seller_id,seller_name, property_name, property_id, approval, property_images, property_price, property_address, property_district, property_state, registered_date,account_number from property_registration where (property_district like '%%%s%%') and deleted_User=0 and approval='Approved' and register_status='Not Registered'", district);
		List<Property> list = jdbcTemplate.query(residential, new PropertyUserDisplayMapper());
		return list;
	}

}