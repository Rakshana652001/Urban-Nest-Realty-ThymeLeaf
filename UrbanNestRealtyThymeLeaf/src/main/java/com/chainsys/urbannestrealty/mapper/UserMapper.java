package com.chainsys.urbannestrealty.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.urbannestrealty.model.User;

public class UserMapper  implements RowMapper<User>
{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException
	{
		User user=new User();
		
		String id = rs.getString("id");
		user.setGeneratedUserID(id);
		
		String name = rs.getString("name");
		user.setName(name);
		
		String designation = rs.getString("designation");
		user.setDesignation(designation);
		
		long phoneNumber = rs.getLong("phone_number");
		user.setPhoneNumber(phoneNumber);
		
		String emailID = rs.getString("email_id");
		user.setEmailID(emailID);
		
		String address = rs.getString("address");
		user.setAddress(address);
		
		String district = rs.getString("district");
		user.setDistrict(district);
		
		String state = rs.getString("state");
		user.setState(state);
				
		return user;
	}
	
	
}