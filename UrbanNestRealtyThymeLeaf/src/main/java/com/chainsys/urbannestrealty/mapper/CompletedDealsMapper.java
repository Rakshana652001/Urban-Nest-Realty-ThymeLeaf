package com.chainsys.urbannestrealty.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class CompletedDealsMapper implements RowMapper<Sales>
{
	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Sales sales = new Sales();
		
		String customerId = rs.getString("customer_id");
		sales.setCustomerId(customerId);
		
		Blob image = rs.getBlob("government_id");
        if (image != null) 
        {
            int blobLength = (int) image.length();
            byte[] blobAsBytes = image.getBytes(1, blobLength);
            sales.setGovernmentId(blobAsBytes);
        }
                
        String propertyAddress = rs.getString("property_address");
        sales.setPropertyAddress(propertyAddress);
        
        String paymentMethod = rs.getString("payment_method");
        sales.setPaymentMethod(paymentMethod);
        
        long propertyPrice = rs.getLong("total_amount");
        sales.setTotalAmount(propertyPrice);
        
        double payableAmount = rs.getDouble("payabel_amount");
        sales.setPayableAmount(payableAmount);
        
        long customerAccount = rs.getLong("customer_account");
        sales.setCustomerAccount(customerAccount);
        
        long sellerAccount = rs.getLong("seller_account");
        sales.setAccountNumber(sellerAccount);
        
        String purchasedDate = rs.getString("purchased_date");
        sales.setPurchasedDate(purchasedDate);
        
        String paidStatus = rs.getString("payed_status");
        sales.setPaidStatus(paidStatus);
        
		return sales;
	}

}
