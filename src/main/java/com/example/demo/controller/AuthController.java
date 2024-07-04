package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.LoginRequest;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.service.impl.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
	@Autowired
	private UserService userservice;
	
	@PostMapping("/register")
	public Response registerUser(@RequestBody User user) {
		return userservice.register(user);
	}
	
	@PostMapping("/login")
	public Response loginUser(@RequestBody LoginRequest request) {
		return userservice.login(request);
	}
	
}
