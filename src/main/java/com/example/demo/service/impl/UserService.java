package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LoginRequest;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.exception.OwnException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.inter.UserInterface;
import com.example.demo.utils.JwtUtils;

import lombok.experimental.var;

@Service
public class UserService implements UserInterface{
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private JwtUtils jwtutils;

	@Override
	public Response register(User user) {
		
		Response response=new Response();
		
		try {
			
			if(user.getRole()==null||user.getRole().isBlank()) {
				user.setRole("USER");
			}
			if(userrepo.existsByEmail(user.getEmail())) {
				response.setStatusCode(200);
				response.setMessage("User already exist with this email");
				return response;
			}
			user.setPassword(encoder.encode(user.getPassword()));
			User savedUser=userrepo.save(user);
			response.setStatusCode(200);
			response.setMessage("user registered successfully");
			response.setUser(savedUser);
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured during registering the user with email"+user.getEmail());
		}
		
		return response;	
	}

	@Override
	public Response login(LoginRequest request) {
		Response response=new Response();
		User loggeduser=null;
		try {
			authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
			loggeduser=userrepo.findByEmail(request.getEmail()).orElseThrow(()->new OwnException("user not found"));
			var token=jwtutils.generateToken(loggeduser);
			response.setStatusCode(200);
			response.setMessage("loggin successfull");
			response.setToken(token);
			response.setRole(loggeduser.getRole());
			
			
		}catch(OwnException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurred during login user");
		}
		return response;
	}

	@Override
	public List<User> getalluser() {
		return userrepo.findAll();
	}

	@Override
	public Response additem(Long userid, Long foodid) {
		Response response=new Response();
		try {
			User user=userrepo.findById(userid).orElseThrow(()->new OwnException("user not found"));
			List<Long>listitem=user.getUsercartitems();
			listitem.add(foodid);
			user.setUsercartitems(listitem);
			userrepo.save(user);
			response.setStatusCode(200);
			response.setMessage("item added successfully");
			response.setUser(user);
			
		}catch(OwnException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("error occured while adding the item to the user");
		}
		return response;
	}
	@Override
	public Response removeitem(Long userid, Long foodid) {
		Response response=new Response();
		try {
			User user=userrepo.findById(userid).orElseThrow(()->new OwnException("user not found"));
			List<Long>listitem=user.getUsercartitems();
			int indextoremove=listitem.indexOf(foodid);
			if(indextoremove!=-1)
				listitem.remove(indextoremove);
			user.setUsercartitems(listitem);
			userrepo.save(user);
			response.setStatusCode(200);
			response.setMessage("item removed successfully");
			response.setUser(user);
			
		}catch(OwnException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("error occured while adding the item to the user");
		}
		return response;
	}

	@Override
	public Response getuserbyemail(String email) {
		Response response=new Response();
		
		try {
			User user=userrepo.findByEmail(email).orElseThrow(()->new OwnException("user not found with email"));
			response.setStatusCode(200);
			response.setMessage("found loggedd user");
			response.setUser(user);
			
		}catch(OwnException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured while getting loggeduser");
		}
		return response;
	}

	



}

	

