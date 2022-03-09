package com.app.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.loan.model.User;
import com.app.loan.service.RegistrationService;

@CrossOrigin()
@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService service;
	
	@PostMapping("/user/signup")
	public User registerUser(@RequestBody User user) throws Exception{
		String tempEmailId = user.getEmailId();
		if(tempEmailId != null && !"".equals(tempEmailId)) {
			User userObj =service.fetchUserByEmailId(tempEmailId);
			if(userObj !=null) {
				throw new Exception("user with"+tempEmailId+"is already exist");
			}
		}
		User userObj=null;
		userObj= service.saveUser(user);
		return userObj;
	}
	@PostMapping("/admin/signup")
	public User registerAdmin(@RequestBody User user) throws Exception{
		String tempEmailId = user.getEmailId();
		if(tempEmailId != null && !"".equals(tempEmailId)) {
			User userObj =service.fetchUserByEmailId(tempEmailId);
			if(userObj !=null) {
				throw new Exception("admin with"+tempEmailId+"is already exist");
			}
		}
		User userObj=null;
		userObj= service.saveUser(user);
		return userObj;
	}
	
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) throws Exception
	{
		String tempEmailId= user.getEmailId();
		String tempPass=user.getPassword();
		User userObj=null;
		if(tempEmailId != null && tempPass !=null)
		{
			userObj= service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
		}
		if(userObj ==null)
		{
			throw new Exception("bad credentials");
		}
		return userObj;
		
	}
	
}
