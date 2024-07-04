import React, { useContext, useEffect, useState } from "react";
import "./FoodDisplay.css";
import { StoreContext } from "../../Context/StoreContext";
import FoodItem from "../FoodItem/FoodItem";
import ApiService from "../../apiservices/ApiService";

const FoodDisplay = ({category}) => {

  // const {food_list} =useContext(StoreContext);
  const [fooditems,setFooditems]=useState([]);

  useEffect(()=>{
     const allitems=async()=>{
           const response= await ApiService.getallitems();
           setFooditems(response.fooditems)
     }
     allitems()
  },[])

  return (
    <div className="food-display" id="food-display">
      <h2>Top dishes near you</h2>
      <div className="food-display-list">
        {fooditems.length>0&&fooditems.map((item, index)=>{
            if(category==="All"||category==item.category)
            {
                return <FoodItem key={index} id={item.id} name={item.name} price={item.price} description={item.description} image={item.image}/>
            }
            })}
        </div>

    </div>
  );
};

export default FoodDisplay;
