package com.chainsys.urbannestrealty.service;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;

@Service
public class PropertyService 
{

	@Autowired
    private static UserDAO userDAO;

    public List<Property> searchProperties(String propertyName) 
    {
        return userDAO.properties(propertyName);
    }
    
    public List<Property> searchProperties1(String propertyName)
    {
        return userDAO.propertiesSearch(propertyName);
    }
  
    public void getRegisteredProperties(String id, Model model) 
    {
        List<Property> list = userDAO.sellerRegisteredProperties(id);
		model.addAttribute("list",list);
    }
    
  
    
    public void getAuthorizedProperties(Model model) 
    {
        List<Property> list = userDAO.authorizedProperties();
        model.addAttribute("list", list);
    }
    
    public void approveProperty(String address, String approvalStatus) 
    {
        Property property = new Property();
        property.setApproval(approvalStatus);
        property.setPropertyAddress(address);
        userDAO.approval(property);
    }

    public List<Property> getResidentialProperties() 
    {
        return userDAO.residential();
    }
    
    public List<Property> getPendingProperties(Model model) {
        List<Property> list = userDAO.pendingProperty();
        for (Property property : list) {
            byte[] image = property.getPropertyImages();
            String getImage = Base64.getEncoder().encodeToString(image);
            property.setBase64Image(getImage);

            byte[] document = property.getPropertyDocument();
            String getDocument = Base64.getEncoder().encodeToString(document);
            property.setBase64Document(getDocument);
        }
        model.addAttribute("list", list);
        return list;
    }
    
    
}