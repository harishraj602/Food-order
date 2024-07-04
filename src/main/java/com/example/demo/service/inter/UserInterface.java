package com.example.demo.service.inter;

import java.util.List;

import com.example.demo.entity.LoginRequest;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;

public interface UserInterface {
   
	Response register(User user); 
	
	Response login(LoginRequest request);
	
	List<User> getalluser();
	Response additem(Long userid, Long foodid);
	Response removeitem(Long userid, Long foodid);
	Response getuserbyemail(String email);
}
