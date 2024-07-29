package com.chainsys.urbannestrealty.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.chainsys.urbannestrealty.dao.UserDAO;
import com.chainsys.urbannestrealty.model.Property;

public class PropertyService 
{

	@Autowired
    private UserDAO userDAO;

    public List<Property> searchProperties(String propertyName) {
        List<Property> properties = userDAO.properties(propertyName);
        return properties.stream()
                         .filter(property -> property.getPropertyName().equalsIgnoreCase(propertyName) 
                                             || property.getPropertyName().contains(propertyName))
                         .collect(Collectors.toList());
    }
    
    public List<Property> searchProperties1(String propertyName) {
        List<Property> properties = userDAO.propertiesSearch(propertyName);
        return properties.stream()
                         .filter(property -> property.getPropertyName().equalsIgnoreCase(propertyName)
                                             || property.getPropertyName().contains(propertyName))
                         .collect(Collectors.toList());
    }
    
}
