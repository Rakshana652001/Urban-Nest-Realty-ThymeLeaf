package com.chainsys.urbannestrealty.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

@Repository
public class Validation
{

	public boolean nameValidation(String name, Model model)
	{
		Pattern p = Pattern.compile("[a-z .A-Z]+");
		Matcher m = p.matcher(name);
		boolean match = m.matches();
		
		if(Boolean.FALSE.equals(match))
		{
			String errorMessage = name+" is a invalid name use only alphabetic sequence";
			model.addAttribute(errorMessage, model);
			return false;
		}
		return match;		
	}

	public boolean phoneNumberValidation(long phoneNumber, Model model)
	{
		Pattern p = Pattern.compile("[789][\\d]{9}");
		String number = Long.toString(phoneNumber);
		Matcher m = p.matcher(number);
		boolean match = m.matches();
		
		if(Boolean.FALSE.equals(match))
		{
			String errorMessage = phoneNumber+" is a invalid phone number use only digits starts with (7,8,9) and want to have only 10 digits";
			model.addAttribute(errorMessage, model);
			return false;
		}
		return match;		
	}

	public boolean emailValidation(String emailID, Model model)
	{
		Pattern p = Pattern.compile("[a-z0-9_/-/.]+[@][a-z]+[/.][a-z]{2,3}$");
		Matcher m = p.matcher(emailID);
		boolean match = m.matches();
		
		if(Boolean.FALSE.equals(match))
		{
			String errorMessage = emailID+" is a invalid emailID use only alphabets, number, _,- with @gmail.com";
			model.addAttribute(errorMessage, model);
			return false;
		}
		return match;
	}

	public boolean passwordValidation(String password, Model model)
	{
		Pattern p = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
		Matcher m = p.matcher(password);
		boolean match = m.matches();
		
		if(Boolean.FALSE.equals(match))
		{
			String errorMessage = password+" is a invalid password use only upper, lower alphabets, number and one special charecter";
			model.addAttribute(errorMessage, model);
			return false;
		}
		return match;
	}

	public Object accountNumber(long yourAccountNumber, long senderAccountNumber, Model model) 
	{
		Pattern p = Pattern.compile("[0-9]{12}");
		Matcher m = p.matcher(Long.toString(yourAccountNumber));
		Matcher m2 = p.matcher(Long.toString(senderAccountNumber));
		boolean match = m.matches();
		boolean match2 = m2.matches();
		if(Boolean.FALSE.equals(match))
		{
			String errorMessage = yourAccountNumber+" is a invalid account number, Account number want to be only 12 digits";
			model.addAttribute(errorMessage, model);
			return false;
		}
		else if(Boolean.FALSE.equals(match2))
		{
			String errorMessage = senderAccountNumber+" is a invalid account number, Account number want to be only 12 digits";
			model.addAttribute(errorMessage, model);
			return false;
		}
		return match;
	}
}