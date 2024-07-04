package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Food;
import com.example.demo.entity.Response;
import com.example.demo.exception.OwnException;
import com.example.demo.repository.FoodRepository;
import com.example.demo.service.inter.FoodInterface;

@Service
public class FoodService implements FoodInterface{
	
	@Autowired
	private FoodRepository foodrepo;

	@Override
	public Response addFoodItem(MultipartFile photo, String name, int price, String description, String category) {
		Response response=new Response();
		
		try {
			
			Food newitem=new Food();
			
			String image=Base64.getEncoder().encodeToString(photo.getBytes());
			newitem.setImage(image);
			newitem.setCategory(category);
			newitem.setName(name);
			newitem.setPrice(price);
			newitem.setDescription(description);
			Food food =foodrepo.save(newitem);
			response.setStatusCode(200);
			response.setMessage("new Food item added ");
			response.setFood(food);	
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error adding a new food item");
		}
		return response;
	}

	@Override
	public Response getallfood() {
		
		Response response=new Response();
		List<Food>fooditems=foodrepo.findAll();
		response.setStatusCode(200);
		response.setMessage("success");
		response.setFooditems(fooditems);
		return response;
		
	}

	@Override
	public Response getfooditems(List<Long> fooditemsid) {
		Response response=new Response();
		
		try {
		  List<Food>foodlist=new ArrayList<>();
		  for(Long foodid:fooditemsid) {
			  Food food=foodrepo.findById(foodid).orElseThrow(()->new OwnException("fooditem not found"));
			  foodlist.add(food);
		  }
		  response.setStatusCode(200);
		  response.setMessage("successfull");
		  response.setFooditems(foodlist);
			
		}catch(OwnException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	

}
