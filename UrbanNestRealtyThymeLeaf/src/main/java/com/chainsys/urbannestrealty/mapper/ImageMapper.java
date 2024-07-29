package com.chainsys.urbannestrealty.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Property;

public class ImageMapper implements RowMapper<Property>
{

	@Override
	public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Property property = new Property();
		
		Blob image = rs.getBlob("property_document");
        if (image != null) 
        {
            int blobLength = (int) image.length();
            byte[] blobAsBytes = image.getBytes(1, blobLength);
            property.setPropertyDocument(blobAsBytes);
        }
        
        Blob imageProperty = rs.getBlob("property_images");
        if (imageProperty != null) 
        {
            int blobLength = (int) imageProperty.length();
            byte[] blobAsBytes = imageProperty.getBytes(1, blobLength);
            property.setPropertyImages(blobAsBytes);
        }
        
        return property;
	}
	
}
