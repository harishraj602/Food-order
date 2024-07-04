package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Response;
import com.example.demo.service.impl.FoodService;

@RestController
@RequestMapping("/food")
public class FoodController {
    
	@Autowired
	private FoodService foodservice;
	
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Response addFoodItem(
			@RequestParam(value="photo",required = false) MultipartFile photo,
			@RequestParam(value="name", required=false)String name,
			@RequestParam(value="price", required=false)int price,
			@RequestParam(value="description",required=false)String description,
			@RequestParam(value="category",required=false)String category)
	{
		if(photo==null||photo.isEmpty()||name==null||name.isBlank())
		{
			Response response=new Response();
			response.setStatusCode(404);
			response.setMessage("Please provide all fields");
			return response;
		}
		return foodservice.addFoodItem(photo,name,price,description,category);
	}
	
	@GetMapping("/all")
	public Response getallfooditems() {
		return foodservice.getallfood();
	}
	
	@PostMapping("/getFoodItems")
	public Response getfooditems(@RequestBody List<Long> fooditemsid)
	{   
		return foodservice.getfooditems(fooditemsid);
	}
}
