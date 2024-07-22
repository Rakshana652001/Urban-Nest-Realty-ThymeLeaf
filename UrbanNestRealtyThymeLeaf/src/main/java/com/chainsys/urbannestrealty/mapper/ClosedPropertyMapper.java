package com.chainsys.urbannestrealty.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Property;

public class ClosedPropertyMapper implements RowMapper<Property> 
{

	@Override
	public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
Property property = new Property();
		
		String sellerId = rs.getString("seller_id");
		property.setSellerId(sellerId);
		
		String propertyName = rs.getString("property_name");
		property.setPropertyName(propertyName);
		
		Blob image = rs.getBlob("property_images");
        if (image != null) 
        {
            int blobLength = (int) image.length();
            byte[] blobAsBytes = image.getBytes(1, blobLength);
            property.setPropertyImages(blobAsBytes);
        }
        
        long propertyPrice = rs.getLong("property_price");
        property.setPropertyPrice(propertyPrice);
        
        String propertyAddress = rs.getString("property_address");
        property.setPropertyAddress(propertyAddress);
        
        String propertyDistrict = rs.getString("property_district");
        property.setPropertyDistrict(propertyDistrict);
        
        String propertyState = rs.getString("property_state");
        property.setPropertyState(propertyState);
        
        String registeredDate = rs.getString("registered_date");
        property.setRegisteredDate(registeredDate);
        
        String purchasedDate = rs.getString("purchased_date");
        property.setPurchasedDate(purchasedDate);
        
        String customerId = rs.getString("customer_id");
        property.setCustomerId(customerId);
                
        String paymentStatus = rs.getString("payment_status");
        property.setPaymentStatus(paymentStatus);
        
		return property;
	}

}
