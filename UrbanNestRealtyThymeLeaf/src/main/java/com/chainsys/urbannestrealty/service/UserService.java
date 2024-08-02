package com.chainsys.urbannestrealty.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.User;


@Service
public class UserService {
	
	@Autowired
    private UserDAO userDAO;


    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public void saveUserDetails(String generatedUserID, String name, long phoneNumber, String designation, String emailID, String password, String district, String state, String address) 
    {
    	 userDAO.saveUserDetails(generatedUserID, name, phoneNumber, designation, emailID, password, district, state, address);
    }

    public List<User> getAdminDetails()
    {
        return userDAO.retriveAdminDetails();
    }
    
    public List<User> getSellerProfile(String sellerId) 
    {
        return userDAO.retriveSellerProfile(sellerId);
    }
    
    public List<User> getCustomerProfile(String customerId) 
    {
    	return userDAO.retriveCustomerDetails(customerId);
    }
    
    public void updateAdminDetails(User user) 
    {
        userDAO.updateAdmindetails(user);
    }
    
    
    public void updateCustomerDetails(User user) {
        userDAO.updateCustomerdetails(user);
    }

    public List<User> getCustomerDetails(String customerId) 
    {
         return userDAO.retriveCustomerDetails(customerId);
    }

    public String encodePassword(String password) 
    {
         return bcrypt.encode(password);
    }
    
    public void updateSellerDetails(User user) 
    {
        userDAO.updateSellerdetails(user);
    }
    
    public void getSellerCustomerDetails(Model model) 
    {
        List<User> list = userDAO.sellerCustomerDetails();
        model.addAttribute("list", list);
    }
    
    public List<User> deleteSellerAndRetrieveList(String name) 
    {
        User user = new User();
        user.setName(name);
        userDAO.delete(user);
        return userDAO.sellerCustomerDetails();
    }
    
    public void getCustomerDetails(Model model) 
    {
        List<User> list = userDAO.customerDetails();
        model.addAttribute("list", list);
    }
    
    public void deleteCustomer(String name) 
    {
        User user = new User();
        user.setName(name);
        userDAO.delete(user);
    }

}
