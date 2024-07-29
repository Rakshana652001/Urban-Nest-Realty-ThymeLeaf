package com.chainsys.urbannestrealty.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.Sales;
import com.chainsys.urbannestrealty.model.User;
import com.chainsys.urbannestrealty.validation.Validation;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	
	@Autowired
    private UserDAO userDAO;

    @Autowired
    private Validation validation;

    public String home() {
        return "home";
    }
    
    public String user()
	{
		return "UserRegistration";
	}
    
    public String adminWelcome()
	{
		return "AdminWelcomePage";
	}
    
    public String sellerHome()
	{
		return "SellerWelcomePage";
	}
    
    public String customerWelcome()
	{
		return "CustomerWelcomePage";
	}
    
    public String sellerDetails()
	{
		return "SellerList";
	}

    private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    public String saveUserDetails(String generatedUserID, String name, long phoneNumber, String designation, String emailID, String password, String district, String state, String address, Model model) {
        User user = new User();

        String encryptedPassword = bcrypt.encode(password);

        user.setGeneratedUserID(generatedUserID);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setDesignation(designation);
        user.setEmailID(emailID);
        user.setPassword(encryptedPassword);
        user.setDistrict(district);
        user.setState(state);
        user.setAddress(address);

        if (Boolean.FALSE.equals(validation.nameValidation(name, model)) ||
            Boolean.FALSE.equals(validation.phoneNumberValidation(phoneNumber, model)) ||
            Boolean.FALSE.equals(validation.emailValidation(emailID, model)) ||
            Boolean.FALSE.equals(validation.passwordValidation(password, model))) {
            return "userRegistration";
        } else {
            userDAO.saveUserDetails(user);
            return "AdminLogin";
        }
    }
    
    public String login()
	{
		return "AdminLogin";
	}
    
    public String login(String generatedUserID, String password, HttpSession httpSession, Model model) {
        try {
            if (isAdmin(generatedUserID, password, model)) {
                handleAdminLogin(httpSession);
                return "AdminWelcomePage";
            }
            else if (isSeller(generatedUserID, password, model)) {
                handleSellerLogin(generatedUserID, httpSession);
                return "SellerWelcomePage";
            }
             
            else {
                return "home";
            }
        } catch (Exception e)
        {
        	if (isCustomer(generatedUserID, password, model)) {
                handleCustomerLogin(generatedUserID, httpSession);
                return "CustomerWelcomePage";
            }
        	else
        	{
        		return "home";
        	}
        }
    }

    private boolean isAdmin(String generatedUserID, String password, Model model) {
        return generatedUserID.equals("UNR_Admin_1") && password.equals(userDAO.getAdminpassword(generatedUserID)) ||
               Boolean.FALSE.equals(validation.passwordValidation(password, model));
    }

    private boolean isCustomer(String generatedUserID, String password, Model model) {
        return generatedUserID.equals(userDAO.getcustomerId(generatedUserID)) &&
               bcrypt.matches(password, userDAO.getCustomerPassword(generatedUserID)) ||
               Boolean.FALSE.equals(validation.passwordValidation(password, model));
    }

    private boolean isSeller(String generatedUserID, String password, Model model) {
        return generatedUserID.equals(userDAO.getsellerId(generatedUserID)) &&
               bcrypt.matches(password, userDAO.getsellerPassword(generatedUserID)) ||
               Boolean.FALSE.equals(validation.passwordValidation(password, model));
    }

    private void handleAdminLogin(HttpSession httpSession) {
        List<Property> list = userDAO.registeredProperties();
        int size = list.size();

        int land = userDAO.landCount();
        int residential = userDAO.residentialCount();
        int commercial = userDAO.commercialCount();
        int pg = userDAO.pgCount();
        int showroom = userDAO.showroomCount();
        int plot = userDAO.plotCount();
        int floor = userDAO.floorCount();
        int factory = userDAO.factoryCount();

        int getcount = getMaxPropertyCount();

        String propertyType = getPropertyType(land, residential, commercial, pg, showroom, plot, floor, factory, getcount);
        httpSession.setAttribute("getsaledProperty", propertyType);

        String getDistrict = userDAO.getDistrict();
        httpSession.setAttribute("getSaledArea", getDistrict);
        httpSession.setAttribute("getTotalSale", size);
    }

    private String getPropertyType(int land, int residential, int commercial, int pg, int showroom, int plot, int floor, int factory, int getcount) {
        if (land == getcount) return "Land";
        if (residential == getcount) return "Residential";
        if (commercial == getcount) return "Commercial";
        if (pg == getcount) return "PG";
        if (showroom == getcount) return "Showroom";
        if (plot == getcount) return "Plot";
        if (floor == getcount) return "Floor";
        if (factory == getcount) return "Factory";
        return "Unknown";
    }

    private void handleCustomerLogin(String generatedUserID, HttpSession httpSession) {
        String name = userDAO.getcustomerName(generatedUserID);
        httpSession.setAttribute("customerName", name);
        httpSession.setAttribute("customerId", generatedUserID);
    }

    private void handleSellerLogin(String generatedUserID, HttpSession httpSession) {
        String name = userDAO.getsellerName(generatedUserID);

        List<Property> list = userDAO.sellerProperties(generatedUserID);
        int total = list.size();
        httpSession.setAttribute("total", total);

        List<Sales> completed = userDAO.completedDeals(generatedUserID);
        int completedTotal = completed.size();
        httpSession.setAttribute("completedTotal", completedTotal);

        List<Sales> totalAmount = userDAO.totalAmount(generatedUserID);
        double totalAmounts = totalAmount.stream().mapToDouble(Sales::getTotalAmount).sum();
        httpSession.setAttribute("totalAmount", totalAmounts);

        httpSession.setAttribute("sellerName", name);
        httpSession.setAttribute("sellerId", generatedUserID);
    }

    private int getMaxPropertyCount() 
    {
    	int land = userDAO.landCount();
	    int residential = userDAO.residentialCount();
	    int commercial = userDAO.commercialCount();
	    int pg = userDAO.pgCount();
	    int showroom = userDAO.showroomCount();
	    int plot = userDAO.plotCount();
	    int floor = userDAO.floorCount();
	    int factory = userDAO.factoryCount();

	    int maxCount = Math.max(land, Math.max(residential, Math.max(commercial, 
	                     Math.max(pg, Math.max(showroom, Math.max(plot, 
	                     Math.max(floor, factory)))))));

	    return maxCount;
    }
    
    public List<User> getAdminDetails() {
        return userDAO.retriveAdminDetails();
    }
    
    public List<User> getSellerProfile(String sellerId) {
        return userDAO.retriveSellerProfile(sellerId);
    }
    
    public List<User> getCustomerProfile(String customerId) {
        return userDAO.retriveCustomerDetails(customerId);
    }
    
    public void updateAdminDetails(User user) {
        userDAO.updateAdmindetails(user);
    }
    
    public String updatecustomer()
	{
		return "UpdateCustomerDetails";
	}
    
    public void updateCustomerDetails(User user) {
        userDAO.updateCustomerdetails(user);
    }

    public List<User> getCustomerDetails(String customerId) {
        return userDAO.retriveCustomerDetails(customerId);
    }

    public String encodePassword(String password) {
        return bcrypt.encode(password);
    }
    
    public String updateseller()
	{
		return "UpdateSellerDetails";
	}
    
    public void updateSellerDetails(User user) {
        userDAO.updateSellerdetails(user);
    }
    
    public List<User> getSellerCustomerDetails() {
        return userDAO.sellerCustomerDetails();
    }
    
    public List<User> deleteSellerAndRetrieveList(String name) {
        User user = new User();
        user.setName(name);
        userDAO.delete(user);
        return userDAO.sellerCustomerDetails();
    }
    
    public List<User> getCustomerDetails() {
        return userDAO.customerDetails();
    }
   
    public void deleteCustomer(String name) {
        User user = new User();
        user.setName(name);
        userDAO.delete(user);
    }

    public List<User> searchSellers(String id) {
        List<User> sellers = userDAO.sellerSearch(id);
        return sellers.stream()
                      .filter(user -> user.getGeneratedUserID().equalsIgnoreCase(id) 
                                      || user.getName().equalsIgnoreCase(id) 
                                      || user.getEmailID().equalsIgnoreCase(id))
                      .collect(Collectors.toList());
    }
    
    public List<User> searchCustomers(String id) {
        List<User> customers = userDAO.customerSearch(id);
        return customers.stream()
                        .filter(user -> user.getGeneratedUserID().equalsIgnoreCase(id) 
                                        || user.getName().equalsIgnoreCase(id) 
                                        || user.getName().contains(id) 
                                        || user.getEmailID().equalsIgnoreCase(id))
                        .collect(Collectors.toList());
    }
    
   
    public void logOutUser(HttpSession session) {
        session.invalidate();
    }
    

}
