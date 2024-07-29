package com.chainsys.urbannestrealty.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;
import com.chainsys.urbannestrealty.model.Sales;
import com.chainsys.urbannestrealty.validation.Validation;


import jakarta.servlet.http.HttpSession;


//@RestController
@Controller
public class CustomerController 
{
	@Autowired
	UserDAO userDAO;
	@Autowired
	Validation validation;
	
	@PostMapping("/Purchase")
	public String purchase(@RequestParam("customerName") String customerName,@RequestParam("sellerName") String sellerName,@RequestParam("accountNumber") long accountNumber,@RequestParam("customerId") String customerId, @RequestParam("governmentId") MultipartFile governmentId, @RequestParam("sellerId") String sellerId, @RequestParam("propertyId") String propertyId, @RequestParam("propertyName") String propertyName, @RequestParam("propertyAddress") String propertyAddress, @RequestParam("propertyPrice") long propertyPrice, @RequestParam("payableAmount") double payableAmount, @RequestParam("paymentMethod") String paymentMethod, HttpSession httpSession) throws IOException
	{
		Sales sale = new Sales();
		if(!governmentId.isEmpty())
		{
			byte[] govId = governmentId.getBytes();
			
			sale.setCustomerId(customerId);
			sale.setGovernmentId(govId);
			sale.setSellerId(sellerId);
			sale.setPropertyId(propertyId);
			sale.setPropertyName(propertyName);
			sale.setPropertyAddress(propertyAddress);
			sale.setTotalAmount(propertyPrice);
			sale.setPayableAmount(payableAmount);
			sale.setPaymentMethod(paymentMethod);
			sale.setApproval("Not Approved");
			sale.setPaidStatus("Not Paid");	
			sale.setCustomerName(customerName);
			sale.setSellerName(sellerName);
			
			
			
			
			httpSession.setAttribute("propertyAddress", propertyAddress);
			userDAO.sale(sale);
			
			userDAO.updateCustomerId(sale);
			
			userDAO.updateSellerAccount(customerName,sellerName, propertyAddress );
		}
		else
		{
			return "Purchase";
		}
			
		return "CustomerWelcomePage";
	}
	
	@RequestMapping("/PropertiesUnderReview")
	public String propertiesUsedReview(Model model, HttpSession httpSession)
	{
		String id = (String)httpSession.getAttribute("customerId");
		List<Sales> list = userDAO.propertiesUnderReview(id);
		
		model.addAttribute("list",list);
		return "CustomerRegisteredPropertiesTable";
		
	}
	
	@RequestMapping("/ApproveToBuy")
	public String approveToBuy(Model model, HttpSession httpSession)
	{
		List<Sales> list = userDAO.approveToBuy();
		for(Sales object:list)
		{
			byte[] getImage = object.getGovernmentId();
			String toBase = Base64.getEncoder().encodeToString(getImage);
			object.setGovId(toBase);
		}
		model.addAttribute("list", list);
		return "ApproveToBuyTable";
	}
	
	@RequestMapping("/ApprovalToBuy")
	public String approvalToBuy(@RequestParam("customerId") String customerId, @RequestParam("approvalStatus") String approvalStatus, Model model)
	{
		userDAO.updateApproval(customerId, approvalStatus);
		
		List<Sales> list = userDAO.approveToBuy();
		model.addAttribute("list", list);
		return "AdminWelcomePage";
	}
	
	@RequestMapping("/RegisterBuyProperties")
	public String registerBuyProperties(Model model, HttpSession session)
	{
		String id = (String)session.getAttribute("customerId");
		
		List<Sales> list = userDAO.readyToBuy(id);
		model.addAttribute("list", list);
		return "RegisterBuyPropertiesTable";
	}
	
	@PostMapping("/payNow")
	public String paynow(HttpSession session)
	{
		return "PayNow";
	}
	@PostMapping("/PayNow")
	public String payNow1(Model model,@RequestParam("address") String address,@RequestParam("yourAccountNumber") long yourAccountNumber, @RequestParam("senderAccountNumber") long senderAccountNumber, @RequestParam("amount") Double amount, @RequestParam("purchasedDate") String purchasedDate, HttpSession session)
	{
		if(Boolean.FALSE.equals(validation.accountNumber(yourAccountNumber, senderAccountNumber,model)))
		{
			userDAO.updatePayment(address, yourAccountNumber, senderAccountNumber, purchasedDate);
			return "CustomerWelcomePage";
		}
		else
		{
			return "PayNow";
		}
	}
	
	@RequestMapping("/PurchasedProperties")
	public String purchasedProperties(Model model, HttpSession session)
	{
		String id = (String)session.getAttribute("customerId");
		List<Property> list = userDAO.purchasedProperties(id);
		
		model.addAttribute("list", list);
		return "BuyedPropertiesCustomerViewTable";
	}
	
	@RequestMapping("/view")
	public String view(Model model, @RequestParam("address") String address)
	{
		List<Property> getImage = userDAO.viewImage(address);
		for(Property property:getImage)
		{
			byte[] getImage1 = property.getPropertyDocument();
			String toBase = Base64.getEncoder().encodeToString(getImage1);
			property.setBase64Image(toBase);
		}
		model.addAttribute("list", getImage);
		return "ViewImage";
	}
	
	@RequestMapping("/viewProperty")
	public String viewProperties(Model model, @RequestParam("address") String address)
	{
		List<Property> getImage = userDAO.viewProperty(address);
		for(Property property:getImage)
		{
			byte[] getImage1 = property.getPropertyImages();
			String toBase = Base64.getEncoder().encodeToString(getImage1);
			property.setBase64Image(toBase);
		}
		model.addAttribute("list", getImage);
		return "PropertyView";
	}
	
	@RequestMapping("/CustomerHistory")
	public String history(Model model, HttpSession session)
	{
		String id = (String)session.getAttribute("customerId");
		List<Sales> list = userDAO.customerTransactionHistory(id);
		model.addAttribute("list",list);
		return "CustomerTransactionHistory";
	}
	
	@PostMapping("/customerDate")
	public String customerDate(HttpSession session, Model model,  @RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate)
	{
		String id = (String)session.getAttribute("customerId");
		List<Sales> list = userDAO.customerDate(id, fromDate, toDate);
		model.addAttribute("list",list);
		return "CustomerTransactionHistory";
	}
	
//	@RequestMapping("/downloads")
//	public void download(HttpServletResponse response, HttpSession session, Model model, @RequestParam("address") String address) throws DocumentException, IOException
//	{		
//		response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"Document.pdf\"");
//        
//		List<Sales> list = userDAO.download(address);
//		
//		Document document = new Document();
//	    PdfWriter.getInstance(document, response.getOutputStream());
//	    document.open();
//	    
//	    Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
//        Paragraph heading = new Paragraph("Receipt", headingFont);
//        heading.setAlignment(Element.ALIGN_CENTER);
//        heading.setSpacingBefore(20f);
//        heading.setSpacingAfter(10f); 
//        document.add(heading);        
//        
//        PdfPTable table = new PdfPTable(2);
//	    table.getDefaultCell().setPadding(10);
//	    table.setWidthPercentage(100);
//	    
//	    for(Sales object:list)
//		{
//			byte[] document1 = object.getGovernmentId();
//			String getDocument = Base64.getEncoder().encodeToString(document1);
//			object.setBase64(getDocument);
//		}
//	    
//		for(Sales property : list)
//		{
//			table.addCell("Customer Name");
//			table.addCell(property.getCustomerName());
//			table.addCell("Property Address");
//			table.addCell(property.getPropertyAddress());
//			table.addCell("Property Price");
//			table.addCell(String.valueOf(property.getTotalAmount()));
//			table.addCell("Paid Amount");
//			table.addCell(String.valueOf(property.getPayableAmount()));
//			table.addCell("Payment Status");
//			table.addCell(property.getPaidStatus());
//			table.addCell("Customer GovID proof");
//			
//			byte[] decodedBytes = Base64.getDecoder().decode(property.getBase64());
//		    
//		    // Create an image from the byte array
//		    Image img = Image.getInstance(decodedBytes);
//		    
//		    // Scale image to fit cell
//		    img.scaleToFit(120, 120); // Adjust size as needed
//
//		    PdfPCell imgCell = new PdfPCell(img);
//		    imgCell.setPadding(10);
//		    imgCell.setColspan(7); // Span across all columns
//		    imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		    table.addCell(imgCell);
//		    
//		}
//				
//		table.setWidthPercentage(50);
//		
//		table.setHorizontalAlignment(Element.ALIGN_CENTER);		
//		document.add(table);
//        document.close();
//	}
}