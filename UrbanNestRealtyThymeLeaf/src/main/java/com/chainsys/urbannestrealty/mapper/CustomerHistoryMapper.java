package com.chainsys.urbannestrealty.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class CustomerHistoryMapper implements RowMapper<Sales>
{

	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Sales sales = new Sales();
		
		String seller = rs.getString("seller_id");
		sales.setSellerId(seller);
		
		long customerAccount = rs.getLong("customer_account");
		sales.setCustomerAccount(customerAccount);
		
		long sellerAccount = rs.getLong("seller_account");
		sales.setAccountNumber(sellerAccount);
		
		Double amount = rs.getDouble("payabel_amount");
		sales.setPayableAmount(amount);
		
		String date = rs.getString("purchased_date");
		sales.setPurchasedDate(date);
		
		return sales;
	}

}
