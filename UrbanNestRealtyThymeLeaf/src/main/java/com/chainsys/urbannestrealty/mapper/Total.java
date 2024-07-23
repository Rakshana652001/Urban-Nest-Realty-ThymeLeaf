package com.chainsys.urbannestrealty.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.Sales;

public class Total implements RowMapper<Sales>
{
	@Override
	public Sales mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		Sales sales = new Sales();
		
        long propertyPrice = rs.getLong("total_amount");
        sales.setTotalAmount(propertyPrice);
      
		return sales;
	}

}
