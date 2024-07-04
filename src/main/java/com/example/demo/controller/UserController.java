package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
  
	@Autowired
	private UserService userservice;
	
	@Autowired
	private UserRepository userrepo;
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<User>getalluser(){
		return userservice.getalluser();
	}
	
	@PostMapping("/{userid}/additem/{foodid}")
	public Response additemtouser(@PathVariable Long userid,@PathVariable Long foodid)
	{
		return userservice.additem(userid,foodid);
	}
	@PostMapping("/{userid}/removeitem/{foodid}")
	public Response removeitemfromuser(@PathVariable Long userid,@PathVariable Long foodid)
	{
		return userservice.removeitem(userid,foodid);
	}
	
	@GetMapping("/getloggeduser")
	public Response getloggeduser() {
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String email=authentication.getName();
		return userservice.getuserbyemail(email);
	}
	
}
