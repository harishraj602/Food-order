package com.example.demo.service.inter;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Response;

public interface FoodInterface {
	

     Response addFoodItem(MultipartFile photo, String name, int price, String description, String category);
     
     Response getallfood();
     
     Response getfooditems(List<Long>fooditemsid);
		
}
