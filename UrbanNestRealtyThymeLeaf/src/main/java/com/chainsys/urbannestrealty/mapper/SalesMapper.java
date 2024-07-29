package com.chainsys.urbannestrealty.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class SalesMapper implements RowMapper<Sales> {

	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Sales sales = new Sales();
		
		String customerId = rs.getString("customer_id");
		sales.setCustomerId(customerId);
		
		String customername = rs.getString("customer_name");
		sales.setCustomerName(customername);
	
        String sellerId = rs.getString("seller_name");
        sales.setSellerName(sellerId);
                
        String propertyAddress = rs.getString("property_address");
        sales.setPropertyAddress(propertyAddress);
        
        long propertyPrice = rs.getLong("total_amount");
        sales.setTotalAmount(propertyPrice);
        
        double payableAmount = rs.getDouble("payabel_amount");
        sales.setPayableAmount(payableAmount);
        
        String paymentMethod = rs.getString("payment_method");
        sales.setPaymentMethod(paymentMethod);
        
        String paidStatus = rs.getString("payed_status");
        sales.setPaidStatus(paidStatus);
        
        String approval = rs.getString("approval");
        sales.setApproval(approval);
        
		return sales;
	}

}
