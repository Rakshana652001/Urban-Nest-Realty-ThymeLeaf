package com.chainsys.urbannestrealty.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class ReadyToBuyMapper implements RowMapper<Sales>
{

	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Sales sales = new Sales();
		
		String customerId = rs.getString("customer_id");
		sales.setCustomerId(customerId);
		
		String sellerName = rs.getString("seller_name");
		sales.setSellerName(sellerName);
                
        String propertyAddress = rs.getString("property_address");
        sales.setPropertyAddress(propertyAddress);
        
        long propertyPrice = rs.getLong("property_price");
        sales.setTotalAmount(propertyPrice);
        
        double payableAmount = rs.getDouble("payable_amount");
        sales.setPayableAmount(payableAmount);
         
        
        long acc = rs.getLong("account_number");
        sales.setAccountNumber(acc);
        
		return sales;
	}
	

}
