package com.chainsys.urbannestrealty.controller;

import java.util.Base64;
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
import com.chainsys.urbannestrealty.service.PropertyService;
import com.chainsys.urbannestrealty.service.UserService;
import com.chainsys.urbannestrealty.validation.Validation;

import jakarta.servlet.http.HttpServletRequest;
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
	
	@RequestMapping("/home2")
	public String home2()
	{
		return "home2";
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

	private BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

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
			HttpSession session) 
	{
		User user = new User();
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

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
				Boolean.FALSE.equals(validation.passwordValidation(password, model))) 
		{
			return "userRegistration";
		}
		else
		{

			userService.saveUserDetails(generatedUserID, name, phoneNumber, designation, emailID, password, district, state, address);
			return "AdminLogin";
		}

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
			Model model) 
	{
		try 
		{
			if (generatedUserID.equals("UNR_Admin_1") && password.equals(userDAO.getAdminpassword(generatedUserID)) || Boolean.FALSE.equals(validation.passwordValidation(password, model))) 
			{
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
				
				httpSession.setAttribute("land", land);
				httpSession.setAttribute("residential", residential);
				httpSession.setAttribute("commercial", commercial);
				httpSession.setAttribute("pg", pg);
				httpSession.setAttribute("showroom", showroom);
				httpSession.setAttribute("plot", plot);
				httpSession.setAttribute("floor", floor);
				httpSession.setAttribute("factory", factory);

				return "AdminWelcomePage";
			}
			else if (generatedUserID.equals(userDAO.getsellerId(generatedUserID)) && bcrypt.matches(password, userDAO.getsellerPassword(generatedUserID)) || Boolean.FALSE.equals(validation.passwordValidation(password, model)))
			{
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

				return "SellerWelcomePage";
			}
			else
			{
				return "home"; 
			}
		} 
		catch (Exception e)
		{
			if (generatedUserID.equals(userDAO.getcustomerId(generatedUserID)) && bcrypt.matches(password, userDAO.getCustomerPassword(generatedUserID)) || Boolean.FALSE.equals(validation.passwordValidation(password, model))) 
			{
				String name = userDAO.getcustomerName(generatedUserID);
				httpSession.setAttribute("customerName", name);
				httpSession.setAttribute("customerId", generatedUserID);
				return "CustomerWelcomePage";
			}
			else
			{
				return "home";
			}
		}
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
			Model model, HttpSession httpSession) 
	{
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
	public String getSellerCustomerDetails(Model model) 
	{
		userService.getSellerCustomerDetails(model);
		return "SellerList";
	}


	@PostMapping("/deleteseller")
	public String deleteSeller(@RequestParam("deleteName") String name, Model model) 
	{
		List<User> list = userService.deleteSellerAndRetrieveList(name);
		model.addAttribute("list", list);
		return "SellerList";
	}


	@GetMapping("/CustomerDetails")
	public String customerDetails(Model model) 
	{
		userService.getCustomerDetails(model);
		return "CustomerList";
	}


	@PostMapping("/deletecustomer")
	public String deleteCustomer(@RequestParam("deleteName") String name, Model model) 
	{
		userService.deleteCustomer(name);
		userService.getCustomerDetails(model);
		return "CustomerList";
	}


	//	 @GetMapping("/SellerSearch")
	//	 public String search(@RequestParam("id") String id, Model model) 
	//	 {
	//		 List<User> list = userService.searchSellers(id);
	//		 model.addAttribute("list", list);
	//		 return "SellerList";
	//	 }

	//	 @GetMapping("/CustomerSearch")
	//	 public String customerSearch(@RequestParam("id") String id, Model model) 
	//	 {
	//		 List<User> list = userService.searchCustomers(id);
	//		 model.addAttribute("list", list);
	//		 return "CustomerList";
	//	 }

	@RequestMapping("/Search")
	public String searchProperties(Model model, @RequestParam("propertyName") String propertyName) 
	{
		List<Property> list = userDAO.properties(propertyName).stream()
				.filter(property -> property.getPropertyName().equalsIgnoreCase(propertyName) 
						|| property.getPropertyName().contains(propertyName))
				.collect(Collectors.toList());
		for(Property object:list)
		{
			byte[] getImage = object.getPropertyImages();
			String image = Base64.getEncoder().encodeToString(getImage);
			object.setBase64Image(image);
		}
//		propertyService.searchProperties(propertyName);
		model.addAttribute("list", list);
		return "PropertyTableForUserDisplay";
	}

	@RequestMapping("/PropertySearch")
	public String propertySearch(Model model, @RequestParam("propertyName") String propertyName) 
	{
		List<Property> list = propertyService.searchProperties1(propertyName).stream()
				.filter(property -> property.getPropertyName().equalsIgnoreCase(propertyName)
						|| property.getPropertyName().contains(propertyName))
				.collect(Collectors.toList());
		model.addAttribute("list", list);
		return "RetrivePropertiesTable";
	}

	@RequestMapping("/LogOut")
	public String logOut(HttpSession session, HttpServletRequest request) 
	{
		HttpSession sesion = request.getSession(false);
		if (sesion != null)
		{
			session.invalidate();
		}
		return "redirect:/home";
	}

}
