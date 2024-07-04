package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
  
	private int statusCode;
	
	private String message;
	
	private String token;
	 
	private String role;
	
	private String expirationTime;
	
	private User user;
	
	private Food food;
	
	private List<Food>fooditems=new ArrayList<>();
}
