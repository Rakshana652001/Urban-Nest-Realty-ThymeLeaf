package com.chainsys.urbannestrealty.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.Sales;
import com.chainsys.urbannestrealty.model.User;
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
	public String saveUserDetails(@RequestParam("generatedUserID") String generatedUserID ,@RequestParam ("name") String name ,@RequestParam("phoneNumber") long phoneNumber ,@RequestParam ("designation") String designation, @RequestParam ("emailID") String emailID, @RequestParam ("password") String password, @RequestParam("district") String district, @RequestParam("state") String state, @RequestParam("address") String address, @RequestParam("accountNumber") long accountNumber, Model model, HttpSession session)
	{
		User user = new User();
		
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
        String encryptedPassword=bcrypt.encode(password);
		
		user.setGeneratedUserID(generatedUserID);
		user.setName(name);		
		user.setPhoneNumber(phoneNumber);
		user.setDesignation(designation);
		user.setEmailID(emailID);
		user.setPassword(encryptedPassword);
		user.setDistrict(district);
		user.setState(state);
		user.setAddress(address);
		
		session.setAttribute("sellerAccount", accountNumber);
		
		if(Boolean.FALSE.equals(validation.nameValidation(name, model))||Boolean.FALSE.equals(validation.phoneNumberValidation(phoneNumber,model))||Boolean.FALSE.equals(validation.emailValidation(emailID, model))||Boolean.FALSE.equals(validation.passwordValidation(password,model)))
		{
			
			return "userRegistration";
		}
		else
		{
			userDAO.saveUserDetails(user);
			return "AdminLogin";
		}
		
	}
	
	@RequestMapping("/Login")
	public String login()
	{
		return "AdminLogin";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("generatedUserID") String generatedUserID, @RequestParam("password") String password, HttpSession httpSession, Model model)
	{
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
		
		try
		{
			if(generatedUserID.equals("UNR_Admin_1")&& password.equals(userDAO.getAdminpassword(generatedUserID)) || Boolean.FALSE.equals(validation.passwordValidation(password,model)))
			{
				httpSession.setAttribute("UNR_Admin_1", generatedUserID);
				return "AdminWelcomePage";
			}
			
			else if(generatedUserID.equals(userDAO.getcustomerId(generatedUserID))&& bcrypt.matches(password, userDAO.getCustomerPassword(generatedUserID))||Boolean.FALSE.equals(validation.passwordValidation(password,model)))
			{
				String name = userDAO.getcustomerName(generatedUserID);
				
				httpSession.setAttribute("customerName", name);
				httpSession.setAttribute("customerId", generatedUserID);
				
				return "CustomerWelcomePage";
			}
			else
			{
				return "AdminLogin";
			}	
			
		}
		catch(Exception e)
		{
			if(generatedUserID.equals(userDAO.getsellerId(generatedUserID))&& bcrypt.matches(password, userDAO.getsellerPassword(generatedUserID))||Boolean.FALSE.equals(validation.passwordValidation(password,model)))
			{
				String name = userDAO.getsellerName(generatedUserID);
				
				List<Property> list = userDAO.sellerProperties(generatedUserID);
				int total = list.size();				
				httpSession.setAttribute("total", total);
				
				List<Sales> completed = userDAO.completedDeals(generatedUserID);
				int completedTotal = completed.size();
				httpSession.setAttribute("completedTotal", completedTotal);
				
				List<Sales> totalAmount = userDAO.totalAmount(generatedUserID);
				double totalAmounts = 0.0;
				
				for(Sales i : totalAmount)
				{
					totalAmounts += i.getTotalAmount();
					
				}
				httpSession.setAttribute("totalAmount", totalAmounts);
				
				httpSession.setAttribute("sellerName", name);
				httpSession.setAttribute("sellerId", generatedUserID);
				return "SellerWelcomePage";
			}
			else
			{
				return "AdminLogin";
			}
		}
		
	}
	
	@GetMapping("/AdProfile")
	public String adminProfile(Model model)
	{
		List<User> list = userDAO.retriveAdminDetails();
		model.addAttribute("list", list);
		return "AdminProfile";
	}
	
	
	@GetMapping("/Sellerprofile")
	public String sellerProfile(Model model, HttpSession httpSession)
	{
		String id = (String) httpSession.getAttribute("sellerId");
		List<User> list = userDAO.retriveSellerProfile(id);
		model.addAttribute("list", list);
		return "SellerProfile";
	}
	
	@GetMapping("/Customerprofile")
	public String customerProfile(Model model, HttpSession httpSession)
	{
		String id = (String) httpSession.getAttribute("customerId");
		List<User> list = userDAO.retriveCustomerDetails(id);
		model.addAttribute("list", list);
		return "CustomerProfile";
	}
	
	@RequestMapping("/UpdateAdmin")
	public String adminUpdate()
	{
		return "UpdateAdminDetails";
	}
	
	@PostMapping("/UpdateAd")
	public String updateAdmin(@RequestParam("phoneNumber") long phoneNumber, @RequestParam("password") String password, @RequestParam("address") String address, @RequestParam("name") String name, Model model)
	{
		User user = new User();
		
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
        String encryptedPassword=bcrypt.encode(password);
        
		user.setPhoneNumber(phoneNumber);
		user.setPassword(encryptedPassword);
		user.setAddress(address);
		user.setName(name);
		
		userDAO.updateAdmindetails(user);
		
		List<User> list = userDAO.retriveAdminDetails();
		model.addAttribute("list", list);
		return "AdminProfile";
		
	}
	
	
	@RequestMapping("/Updatecustomer")
	public String updatecustomer()
	{
		return "UpdateCustomerDetails";
	}
	
	@PostMapping("/UpdateCustomer")
	public String updateCustomer1(@RequestParam("phoneNumber") long phoneNumber, @RequestParam("password") String password, @RequestParam("address") String address, @RequestParam("name") String name, Model model,HttpSession httpSession)
	{
		User user = new User();
		
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
        String encryptedPassword=bcrypt.encode(password);
		
		user.setPhoneNumber(phoneNumber);
		user.setPassword(encryptedPassword);
		user.setAddress(address);
		user.setName(name);
		
		userDAO.updateCustomerdetails(user);
		
		String id = (String) httpSession.getAttribute("customerId");
		List<User> list = userDAO.retriveCustomerDetails(id);
		model.addAttribute("list", list);
		return "CustomerProfile";		
	}
	
	@RequestMapping("/Updateseller")
	public String updateseller()
	{
		return "UpdateSellerDetails";
	}
	@PostMapping("/UpdateSeller")
	public String updateSeller1(@RequestParam("phoneNumber") long phoneNumber, @RequestParam("password") String password, @RequestParam("address") String address, @RequestParam("name") String name, Model model,HttpSession httpSession)
	{
		User user = new User();
		
		BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
        String encryptedPassword=bcrypt.encode(password);
		
		user.setPhoneNumber(phoneNumber);
		user.setPassword(encryptedPassword);
		user.setAddress(address);
		user.setName(name);
		userDAO.updateSellerdetails(user);
		
		String id = (String)httpSession.getAttribute("sellerId");
		
		List<User> list = userDAO.retriveSellerProfile(id);
		model.addAttribute("list", list);
		return "SellerProfile";
		
	}

	@GetMapping("/SellerCustomerDetails")
	public String SellerCustomerDetails(Model model)
	{
		List<User> list = userDAO.sellerCustomerDetails();
		model.addAttribute("list",list);
		return "SellerList";
	}
	
	@PostMapping("/deleteseller")
	public String delete(@RequestParam ("deleteName") String name, Model model)
	{
		User user = new User();
		user.setName(name);
		
		userDAO.delete(user);
		
		List<User> list = userDAO.sellerCustomerDetails();
		model.addAttribute("list",list);
		return "SellerList";
	}
	
	@GetMapping("/CustomerDetails")
	public String CustomerDetails(Model model)
	{
		List<User> list = userDAO.customerDetails();
		model.addAttribute("list",list);
		return "CustomerList";
	}
	
	
	@PostMapping("/deletecustomer")
	public String deletecustomer(@RequestParam ("deleteName") String name, Model model)
	{
		User user = new User();
		user.setName(name);
		
		userDAO.delete(user);
		
		List<User> list = userDAO.customerDetails();
		model.addAttribute("list",list);
		return "CustomerList";
	}
	
//	@GetMapping("/SellerSearch")
//	public String Search(@RequestParam("id") String id, Model model)
//	{
//		List<User> list = userDAO.sellerSearch(id);
//		model.addAttribute("list",list);
//		return "SellerList.jsp";
//	}
	
	@GetMapping("/SellerSearch")
	public String search(@RequestParam("id") String id, Model model) 
	{
	    List<User> list = userDAO.sellerSearch(id).stream().filter(user -> user.getGeneratedUserID().equalsIgnoreCase(id) || user.getName().equalsIgnoreCase(id) || user.getEmailID().equalsIgnoreCase(id))
	        .collect(Collectors.toList());
	    model.addAttribute("list", list);
	    return "SellerList";
	}

	
	@GetMapping("/CustomerSearch")
	public String customerSearch(@RequestParam("id") String id, Model model)
	{
		List<User> list = userDAO.customerSearch(id).stream().filter(user->user.getGeneratedUserID().equalsIgnoreCase(id) || user.getName().equalsIgnoreCase(id)|| user.getName().contains(id) || user.getEmailID().equalsIgnoreCase(id)).collect(Collectors.toList());
		model.addAttribute("list",list);
		return "CustomerList";
	}
	
	@RequestMapping("/Search")
	public String searchProperties(Model model, @RequestParam("propertyName") String propertyName, @RequestParam("propertyDistrict") String propertyDistrict)
	{
		List<Property> list = userDAO.properties(propertyName, propertyDistrict).stream().filter(match->match.getPropertyName().equalsIgnoreCase(propertyName) || match.getPropertyName().contains(propertyName) || match.getPropertyDistrict().equalsIgnoreCase(propertyDistrict) || match.getPropertyDistrict().contains(propertyDistrict)).collect(Collectors.toList());
		model.addAttribute("list",list);
		return "PropertyTableForUserDisplay";
	}
	
	@RequestMapping("/PropertySearch")
	public String propertySearch(Model model, @RequestParam("propertyName") String propertyName)
	{
		List<Property> list = userDAO.propertiesSearch(propertyName).stream()
	            .filter(match -> match.getPropertyName().equalsIgnoreCase(propertyName) || match.getPropertyName().contains(propertyName))
	            .collect(Collectors.toList());
		model.addAttribute("list",list);
		return "RetrivePropertiesTable";
	}
	
	@RequestMapping("/LogOut")
	public String logOut(HttpSession session)
	{
		session.invalidate();
		return "home";
	}
	
	@PostMapping("/Contact")
	public String contact(@RequestParam("email") String email, @RequestParam("query") String query)
	{
		
		return query;
	}
	
}