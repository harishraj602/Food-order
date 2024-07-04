import React from 'react'
import axios from 'axios'

export default class  ApiService{
  static BASE_URL="http://localhost:8080";

  static getHeader(){
    const token=localStorage.getItem('token');
    return {
        Authorization:`Bearer ${token}`,
        "Content-Type":'application/json'
    }
  }

// get the logged user
  static async getloggeduser(){
    const response=await axios.get(`${this.BASE_URL}/user/getloggeduser`,{
      headers:this.getHeader()
    })
    return response.data
  }

// get all food items to display
  static async getallitems(){
    const response=await axios.get(`${this.BASE_URL}/food/all`);
    return response.data
  }

// login the user with email and password
   static async loginuser(data){
    const response= await axios.post(`${this.BASE_URL}/auth/login`,data)
    return response.data
   }

//  register the user with all details
    static async registeruser(data){
        const response=await axios.post(`${this.BASE_URL}/auth/register`,data)
        return response.data
    }

//  logout the user
    static logout(){
        localStorage.removeItem('token')
    }

// add items to the cart
    static async additem(userid,foodid){
      console.log("fododid",foodid)
      const response=await axios.post(`${this.BASE_URL}/user/${userid}/additem/${foodid}`,null,{
        headers:this.getHeader()
      })
      return response.data
    }

//  remove items from the cart
    static async removeitem(userid,foodid){
      const response=await axios.post(`${this.BASE_URL}/user/${userid}/removeitem/${foodid}`,null,{
        headers:this.getHeader()
      })
    return response.data
   }

//  get list of fooditems
    static async getuserfooditems(foodlist) {
      const response=await axios.post(`${this.BASE_URL}/food/getFoodItems`,foodlist,{
        headers:this.getHeader()
      })
       return response.data
    } 

   

}

