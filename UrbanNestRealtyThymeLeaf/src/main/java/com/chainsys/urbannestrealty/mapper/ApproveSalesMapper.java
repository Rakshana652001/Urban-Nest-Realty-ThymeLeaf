package com.chainsys.urbannestrealty.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class ApproveSalesMapper implements RowMapper<Sales>
{
	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Sales sales = new Sales();
		
		String customerId = rs.getString("customer_id");
		sales.setCustomerId(customerId);
		
        
        String sellerId = rs.getString("seller_id");
        sales.setSellerId(sellerId);
                
        Blob image = rs.getBlob("government_id");
        if (image != null) 
        {
            int blobLength = (int) image.length();
            byte[] blobAsBytes = image.getBytes(1, blobLength);
            sales.setGovernmentId(blobAsBytes);
        }
        
        String propertyAddress = rs.getString("property_address");
        sales.setPropertyAddress(propertyAddress);
        
        long propertyPrice = rs.getLong("total_amount");
        sales.setTotalAmount(propertyPrice);
        
        double payableAmount = rs.getDouble("payabel_amount");
        sales.setPayableAmount(payableAmount);
        
        String paymentMethod = rs.getString("payment_method");
        sales.setPaymentMethod(paymentMethod);
        
        
        String approval = rs.getString("approval");
        sales.setApproval(approval);
        
        String pay = rs.getString("payed_status");
        sales.setPaidStatus(pay);
        
		return sales;
	}


}
