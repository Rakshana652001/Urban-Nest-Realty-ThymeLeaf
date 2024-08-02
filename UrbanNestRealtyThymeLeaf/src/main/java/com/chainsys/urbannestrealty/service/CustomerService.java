package com.chainsys.urbannestrealty.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Sales;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerService 
{
	@Autowired
    private static UserDAO userDAO;
	
	public List<Sales> getPropertiesUnderReview(String customerId)
	{
        return userDAO.propertiesUnderReview(customerId);
    }
	
	public List<Sales> getApprovedToBuyList() 
	{
        List<Sales> list = userDAO.approveToBuy();
        for (Sales object : list) {
            byte[] getImage = object.getGovernmentId();
            String toBase = Base64.getEncoder().encodeToString(getImage);
            object.setGovId(toBase);
        }
        return list;
    }
	
	public String getApprovalToBuy(String customerId, String approvalStatus, Model model)
	{
		userDAO.updateApproval(customerId, approvalStatus);
		
		List<Sales> list = userDAO.approveToBuy();
		model.addAttribute("list", list);
		return "AdminWelcomePage";
	}
	
	public String registerBuyProperties(Model model, HttpSession session)
	{
		String id = (String)session.getAttribute("customerId");
		List<Sales> list = userDAO.readyToBuy(id);
		model.addAttribute("list", list);
		return "RegisterBuyPropertiesTable";
	}
	
	
}