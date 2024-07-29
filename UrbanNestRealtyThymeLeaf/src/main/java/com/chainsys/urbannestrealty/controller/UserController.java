package com.chainsys.urbannestrealty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.User;
import com.chainsys.urbannestrealty.service.PropertyService;
import com.chainsys.urbannestrealty.service.UserService;
import com.chainsys.urbannestrealty.validation.Validation;

import jakarta.servlet.http.HttpSession;

//@RestController
@Controller
public class UserController
{
	@Autowired 
	UserDAO userDAO;
	@Autowired
	Validation validation;
	@Autowired
    UserService userService;
	
	PropertyService propertyService;
	
	@RequestMapping("/home")
	public String home()
	{        
		return "home";
	}
	
	@RequestMapping("/userRegistration")
	public String user()
	{
		return "UserRegistration";
	}
	
	@RequestMapping("/AdminWelcome")
	public String adminWelcome()
	{
		return "AdminWelcomePage";
	}
	
	@RequestMapping("/sellerHome")
	public String sellerHome()
	{
		return "SellerWelcomePage";
	}
	
	@RequestMapping("/customerWelcome")
	public String customerWelcome()
	{
		return "CustomerWelcomePage";
	}
	
	@RequestMapping("/SellerDetails")
	public String sellerDetails()
	{
		return "SellerList";
	}
	
	@PostMapping("/UserRegistration")
    public String saveUserDetails(@RequestParam("generatedUserID") String generatedUserID,
                                  @RequestParam("name") String name,
                                  @RequestParam("phoneNumber") long phoneNumber,
                                  @RequestParam("designation") String designation,
                                  @RequestParam("emailID") String emailID,
                                  @RequestParam("password") String password,
                                  @RequestParam("district") String district,
                                  @RequestParam("state") String state,
                                  @RequestParam("address") String address,
                                  Model model,
                                  HttpSession session) {
        return userService.saveUserDetails(generatedUserID, name, phoneNumber, designation, emailID, password, district, state, address, model);
    }
	
	@RequestMapping("/Login")
	public String login()
	{
		return "AdminLogin";
	}
	
	@PostMapping("/login")
    public String login(@RequestParam("generatedUserID") String generatedUserID,
                        @RequestParam("password") String password,
                        HttpSession httpSession,
                        Model model) {
        return userService.login(generatedUserID, password, httpSession, model);
    }

	@GetMapping("/AdProfile")
	public String adminProfile(Model model) {
        List<User> list = userService.getAdminDetails();
        model.addAttribute("list", list);
        return "AdminProfile";
    }
	
	 @GetMapping("/Sellerprofile")
	    public String sellerProfile(Model model, HttpSession httpSession) {
	        String id = (String) httpSession.getAttribute("sellerId");
	        List<User> list = userService.getSellerProfile(id);
	        model.addAttribute("list", list);
	        return "SellerProfile";
	    }
	
	@GetMapping("/Customerprofile")
    public String customerProfile(Model model, HttpSession httpSession) {
        String id = (String) httpSession.getAttribute("customerId");
        List<User> list = userService.getCustomerProfile(id);
        model.addAttribute("list", list);
        return "CustomerProfile";
    }

	
	@RequestMapping("/UpdateAdmin")
	public String adminUpdate()
	{
		return "UpdateAdminDetails";
	}
	
	 @PostMapping("/UpdateAd")
	    public String updateAdmin(@RequestParam("phoneNumber") long phoneNumber, 
	                              @RequestParam("password") String password, 
	                              @RequestParam("address") String address, 
	                              @RequestParam("name") String name, 
	                              Model model) {
	        User user = new User();
	        user.setPhoneNumber(phoneNumber);
	        user.setPassword(password);
	        user.setAddress(address);
	        user.setName(name);

	        userService.updateAdminDetails(user);

	        List<User> list = userService.getAdminDetails();
	        model.addAttribute("list", list);
	        return "AdminProfile";
	    }
	
	@RequestMapping("/Updatecustomer")
	public String updatecustomer()
	{
		return "UpdateCustomerDetails";
	}
	
	@PostMapping("/UpdateCustomer")
    public String updateCustomer(@RequestParam("phoneNumber") long phoneNumber, 
                                 @RequestParam("password") String password, 
                                 @RequestParam("address") String address, 
                                 @RequestParam("name") String name, 
                                 Model model, HttpSession httpSession) {
        User user = new User();
        
        String encryptedPassword = userService.encodePassword(password);
        
        user.setPhoneNumber(phoneNumber);
        user.setPassword(encryptedPassword);
        user.setAddress(address);
        user.setName(name);
        
        userService.updateCustomerDetails(user);
        
        String id = (String) httpSession.getAttribute("customerId");
        List<User> list = userService.getCustomerDetails(id);
        model.addAttribute("list", list);
        return "CustomerProfile";
    }
	
	@RequestMapping("/Updateseller")
	public String updateseller()
	{
		return "UpdateSellerDetails";
	}
	
	@PostMapping("/UpdateSeller")
    public String updateSeller(@RequestParam("phoneNumber") long phoneNumber, 
                               @RequestParam("password") String password, 
                               @RequestParam("address") String address, 
                               @RequestParam("name") String name, 
                               Model model, HttpSession httpSession) {
        User user = new User();
        
        String encryptedPassword = userService.encodePassword(password);
        
        user.setPhoneNumber(phoneNumber);
        user.setPassword(encryptedPassword);
        user.setAddress(address);
        user.setName(name);
        
        userService.updateSellerDetails(user);
        
        String id = (String) httpSession.getAttribute("sellerId");
        List<User> list = userService.getSellerProfile(id);
        model.addAttribute("list", list);
        return "SellerProfile";
    }

	@GetMapping("/SellerCustomerDetails")
    public String getSellerCustomerDetails(Model model) {
        List<User> list = userService.getSellerCustomerDetails();
        model.addAttribute("list", list);
        return "SellerList";
    }

	
	 @PostMapping("/deleteseller")
	    public String deleteSeller(@RequestParam("deleteName") String name, Model model) {
	        List<User> list = userService.deleteSellerAndRetrieveList(name);
	        model.addAttribute("list", list);
	        return "SellerList";
	    }
	 
	 
	 @GetMapping("/CustomerDetails")
	    public String customerDetails(Model model) {
	        List<User> list = userService.getCustomerDetails();
	        model.addAttribute("list", list);
	        return "CustomerList";
	    }
	 
	 	
	 @PostMapping("/deletecustomer")
	    public String deleteCustomer(@RequestParam("deleteName") String name, Model model) {
	        userService.deleteCustomer(name);
	        List<User> list = userService.getCustomerDetails();
	        model.addAttribute("list", list);
	        return "CustomerList";
	    }
	 
	
	 @GetMapping("/SellerSearch")
	    public String search(@RequestParam("id") String id, Model model) {
	        List<User> list = userService.searchSellers(id);
	        model.addAttribute("list", list);
	        return "SellerList";
	    }
	
	 @GetMapping("/CustomerSearch")
	    public String customerSearch(@RequestParam("id") String id, Model model) {
	        List<User> list = userService.searchCustomers(id);
	        model.addAttribute("list", list);
	        return "CustomerList";
	    }
	
	 @RequestMapping("/Search")
	    public String searchProperties(Model model, @RequestParam("propertyName") String propertyName) {
	        List<Property> list = propertyService.searchProperties(propertyName);
	        model.addAttribute("list", list);
	        return "PropertyTableForUserDisplay";
	    }
	
	 @RequestMapping("/PropertySearch")
	    public String propertySearch(Model model, @RequestParam("propertyName") String propertyName) {
	        List<Property> list = propertyService.searchProperties1(propertyName);
	        model.addAttribute("list", list);
	        return "RetrivePropertiesTable";
	    }
	
	@RequestMapping("/LogOut")
	public String logOut(HttpSession session) {
        userService.logOutUser(session);  // Use the service layer to handle logout
        return "redirect:/home";  // Redirect to the home page
    }

}