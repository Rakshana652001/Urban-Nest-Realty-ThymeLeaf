package com.chainsys.urbannestrealty.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class IDMapper implements RowMapper<Sales>
{

	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Sales property = new Sales();
		
		Blob image = rs.getBlob("government_id");
        if (image != null) 
        {
            int blobLength = (int) image.length();
            byte[] blobAsBytes = image.getBytes(1, blobLength);
            property.setGovernmentId(blobAsBytes);
        }
        
        return property;
	}

}