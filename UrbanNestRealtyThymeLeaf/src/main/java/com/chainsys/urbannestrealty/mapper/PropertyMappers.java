package com.chainsys.urbannestrealty.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Property;

public class PropertyMappers implements RowMapper<Property>
{
	@Override
	public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
		Property property = new Property();
		
		
		String propertyName = rs.getString("property_name");
		property.setPropertyName(propertyName);
		
		String propertyId = rs.getString("property_id");
		property.setPropertyId(propertyId);
		
		String approval = rs.getString("approval");
		property.setApproval(approval);
		
        Blob document = rs.getBlob("property_document");
        if (document != null) 
        {
            int blobLength = (int) document.length();
            byte[] blobAsBytes = document.getBytes(1, blobLength);
            property.setPropertyDocument(blobAsBytes);
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
        
	
		String customerId = rs.getString("customer_name");
		property.setCustomerName(customerId);
		 
        
        String registeredStatus = rs.getString("register_status");
        property.setRegisterStatus(registeredStatus);
        
        String paymentStatus = rs.getString("payment_status");
        property.setPaymentStatus(paymentStatus);
        
		return property;
	}	
}