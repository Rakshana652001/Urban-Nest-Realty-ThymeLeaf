package com.chainsys.urbannestrealty.controller;


import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.Sales;

import jakarta.servlet.http.HttpSession;


//@RestController
@Controller

public class PropertiesController 
{
	@Autowired 
	UserDAO userDAO;
	
	@RequestMapping("/registration")
	public String registration()
	{
		return "PropertyRegistration";
	}

	@PostMapping("/Registration")
	public String saveUserDetails(@RequestParam("sellerName") String sellerName,@RequestParam("payableAmount") double payableAmount,@RequestParam("accountNumber") long accountNumber,@RequestParam("sellerId") String sellerId ,@RequestParam ("propertyName") String propertyName ,@RequestParam("propertyId") String propertyId ,@RequestParam ("registeredDate") String registeredDate, @RequestParam ("propertyPrice") long propertyPrice, @RequestParam ("propertyImages") MultipartFile propertyImages, @RequestParam("propertyDocument") MultipartFile propertyDocument, @RequestParam("propertyAddress") String propertyAddress, @RequestParam("propertyDistrict") String propertyDistrict, @RequestParam("propertyState") String propertyState, HttpSession httpSession) throws IOException 
	{ 
		if(!propertyImages.isEmpty() && !propertyDocument.isEmpty())
		{
			
		Property property = new Property();
		byte[] imagebytes = propertyImages.getBytes();
		byte[] documetnImages = propertyDocument.getBytes();
		        
		property.setSellerId(sellerId);
		property.setPropertyName(propertyName);
		property.setPropertyId(propertyId);
		property.setRegisteredDate(registeredDate);
		property.setPropertyPrice(propertyPrice);
		property.setPropertyAddress(propertyAddress);
		property.setPropertyImages(imagebytes);
		property.setPropertyDocument(documetnImages);
		property.setPropertyDistrict(propertyDistrict);
		property.setPropertyState(propertyState);
		property.setApproval("Not Approved");
		property.setCustomerId("Not Purchased");
		property.setRegisterStatus("Not Registered");
		property.setPaymentStatus("Not Paid");
		property.setAccountNumber(accountNumber);
		property.setPayableAmount(payableAmount);
		property.setSellerName(sellerName);
		
		httpSession.setAttribute("sellerId", sellerId);
		httpSession.setAttribute("propertyName", propertyName);
		
		userDAO.property(property);
		}
		else
		{
			return "Registration";
		}
		
		return "SellerWelcomePage";
	}
	
	@RequestMapping("/Buynow")
	public String buyNow(HttpSession session)
	{
		session.getAttribute("customerId");
		return "BuyNowForm";
	}
	
	@RequestMapping("/RegisteredProperties")
	public String registeredPropertiesRetrive(Model model, HttpSession session)
	{
		String id = (String)session.getAttribute("sellerId");
		List<Property> list = userDAO.sellerRegisteredProperties(id);
		
		int size = list.size();
		System.out.println(size);
		
		for(Property property:list)
		{
			byte[] image = property.getPropertyImages();
			String getImage = Base64.getEncoder().encodeToString(image);
			property.setBase64Image(getImage);
			
		}
		
		for(Property object:list)
		{
			byte[] document = object.getPropertyDocument();
			String getDocument = Base64.getEncoder().encodeToString(document);
			object.setBase64Document(getDocument);
		}
		
		
		model.addAttribute("list",list);
		return "PropertiesTableSellerView";
	}
	
	@GetMapping("/PendingProperty")
	public String pendingProperties(Model model)
	{
		List<Property> list = userDAO.pendingProperty();
		for(Property property:list)
		{
			byte[] image = property.getPropertyImages();
			String getImage = Base64.getEncoder().encodeToString(image);
			property.setBase64Image(getImage);
			
		}
		
		for(Property object:list)
		{
			byte[] document = object.getPropertyDocument();
			String getDocument = Base64.getEncoder().encodeToString(document);
			object.setBase64Document(getDocument);
		}
		model.addAttribute("list",list);
		return "RegisteredPropertyAdminView";
	}
	
	@GetMapping("/Authorized")
	public String authorized(Model model)
	{
		List<Property> list = userDAO.authorizedProperties();
		for(Property property:list)
		{
			byte[] image = property.getPropertyImages();
			String getImage = Base64.getEncoder().encodeToString(image);
			property.setBase64Image(getImage);
			
		}
		
		for(Property object:list)
		{
			byte[] document = object.getPropertyDocument();
			String getDocument = Base64.getEncoder().encodeToString(document);
			object.setBase64Document(getDocument);
		}
		model.addAttribute("list",list);
		return "RegisteredPropertiesTable";
	}
	
	@PostMapping("/Approval")
	public String approval(@RequestParam("address") String address, @RequestParam("approvalStatus") String approvalStatus, Model model)
	{
		Property property = new Property();
		
		property.setApproval(approvalStatus);
		property.setPropertyAddress(address);
		
		userDAO.approval(property);
		
		List<Property> list = userDAO.pendingProperty();
		for(Property property1:list)
		{
			byte[] image = property1.getPropertyImages();
			String getImage = Base64.getEncoder().encodeToString(image);
			property1.setBase64Image(getImage);
			
		}
		
		for(Property object:list)
		{
			byte[] document = object.getPropertyDocument();
			String getDocument = Base64.getEncoder().encodeToString(document);
			object.setBase64Document(getDocument);
		}
		model.addAttribute("list",list);
		return "RegisteredPropertyAdminView";
	}
	
	@RequestMapping("/Residential")
	public String residential(Model model)
	{
		List<Property> list = userDAO.residential();
		for(Property object:list)
		{
			byte[] getImage = object.getPropertyImages();
			String image = Base64.getEncoder().encodeToString(getImage);
			object.setBase64Image(image);
		}
		model.addAttribute("list",list);
		return "PropertyTableForUserDisplay";
	}
	
	@RequestMapping("/Land")
	public String land(Model model)
	{
		List<Property> list = userDAO.land();
		for(Property object:list)
		{
			byte[] getImage = object.getPropertyImages();
			String toBase64 = Base64.getEncoder().encodeToString(getImage);
			object.setBase64Image(toBase64);
		}
		model.addAttribute("list",list);
		return "PropertyTableForUserDisplay";
	}
	
	@RequestMapping("/Industrial")
	public String industrial(Model model)
	{
		List<Property> list = userDAO.industrial();
		for(Property object:list)
		{
			byte[] getImage = object.getPropertyImages();
			String toBase = Base64.getEncoder().encodeToString(getImage);
			object.setBase64Image(toBase);
		}
		model.addAttribute("list",list);
		return "PropertyTableForUserDisplay";
	}
	
	@RequestMapping("/Commercial")
	public String commercial(Model model)
	{
		List<Property> list = userDAO.commercial();
		for(Property object:list)
		{
			byte[] getImage = object.getPropertyImages();
			String toBase = Base64.getEncoder().encodeToString(getImage);
			object.setBase64Image(toBase);
		}
		model.addAttribute("list",list);
		return "PropertyTableForUserDisplay";
	}

	@RequestMapping("/districtSearch")
	public String districtSearch(@RequestParam("district") String district, Model model)
	{
		List<Property> list = userDAO.districtSearch(district);
		for(Property object:list)
		{
			byte[] getImage = object.getPropertyImages();
			String toBase = Base64.getEncoder().encodeToString(getImage);
			object.setBase64Image(toBase);
		}
		model.addAttribute("list",list);
		return "PropertyTableForUserDisplay";
	}
	
	@GetMapping("/RegisterStatus")
	public String registerStatus(@RequestParam("address") String address, @RequestParam("registerStatus") String registerStatus, Model model)
	{
		userDAO.registerUpdate(address, registerStatus);
		List<Property> list = userDAO.authorizedProperties();
		for(Property property:list)
		{
			byte[] image = property.getPropertyImages();
			String getImage = Base64.getEncoder().encodeToString(image);
			property.setBase64Image(getImage);
			
		}
		
		for(Property object:list)
		{
			byte[] document = object.getPropertyDocument();
			String getDocument = Base64.getEncoder().encodeToString(document);
			object.setBase64Document(getDocument);
		}
		model.addAttribute("list",list);
		return "RegisteredPropertiesTable";
	}

	@RequestMapping("/ClosedDeals")
	public String registeredProperties(Model model)
	{
		List<Property> list = userDAO.registeredProperties();
		for (Property object : list)
		{
            byte[] images = object.getPropertyImages();
            String getImage = Base64.getEncoder().encodeToString(images);
            object.setBase64Image(getImage);
		}    
		
		model.addAttribute("list", list);
		return "RetrivePropertiesTable";
	}
	
	@RequestMapping("/CompletedDeals")
	public String completedDeals(Model model, HttpSession session)
	{
		String id = (String)session.getAttribute("sellerId");
		List<Sales> list = userDAO.completedDeals(id);
		model.addAttribute("list", list);
		return "PaidTable";
	}
	
	@RequestMapping("/Date")
	public String date(Model model, @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate)
	{
		List<Property> list = userDAO.dateFromTo(fromDate,toDate);
		model.addAttribute("list", list);
		return "RetrivePropertiesTable";
	}
	
	@RequestMapping("/View")
	public String view(HttpSession session, Model model)
	{
		String image = (String)session.getAttribute("propImage");
		List<Property> list = userDAO.viewImage(image);
		model.addAttribute("list",list);
		return "Image.jsp";
	}
	
	@RequestMapping("/SellerHistory")
	public String sellerHistory(HttpSession session, Model model)
	{
		String id = (String)session.getAttribute("sellerId");
		List<Sales> list = userDAO.sellerHistory(id);
		model.addAttribute("list",list);
		return "SellerHistory";
	}
	
	@RequestMapping("/SellerDate")
	public String sellerDate(HttpSession session, Model model,  @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate)
	{
		String id = (String)session.getAttribute("sellerId");
		List<Sales> list = userDAO.sellerDate(id, fromDate, toDate);
		model.addAttribute("list",list);
		return "SellerHistory";
	}
}