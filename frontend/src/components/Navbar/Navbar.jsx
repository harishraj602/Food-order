import React, { useContext, useEffect, useState } from 'react'
import './Navbar.css'
import { assets } from '../../assets/assets'
import { Link } from 'react-router-dom'
import { StoreContext } from '../../Context/StoreContext'
import ApiService from '../../apiservices/ApiService'
import {toast } from 'react-toastify';

const Navbar = ({setShowLogin}) => {

  const[menu,setMenu]=useState('home')


  const {getTotalCartAmount,isAuthenticated,setIsAuthenticated,checkcart,setCheckcart} =useContext(StoreContext)
  console.log("navaar auth",isAuthenticated)

  useEffect(()=>{
   const loggeduser=async()=>{
      const response=await ApiService.getloggeduser();
      setCheckcart(response.user.usercartitems.length)
   }
   loggeduser();
  },[])

  
  const handlelogout=()=>{
    setIsAuthenticated(false)
    ApiService.logout();
    toast.success("Logged out successfull") 
  }



  return (
    <div className='navbar'>
       <Link to={'/'}><img src={assets. logo} alt="" className= "logo" /></Link> 
        <ul className= "navbar-menu">
            <Link to={'/'} onClick={()=>setMenu("home")} className={menu==='home'?'active':""}>home</Link>
            <a href='#explore-menu' onClick={()=>setMenu("menu")} className={menu==='menu'?'active':""}>menu</a>
            <a href='#app-download' onClick={()=>setMenu("mobile-app")} className={menu==='mobile-app'?'active':""}>mobile-app</a>
            <a href='#footer' onClick={()=>setMenu("contact-us")} className={menu==='contact-us'?'active':""}>contact us</a>
        </ul>
        <div className="navbar-right">
            <img src={assets.search_icon} alt=" " />
            <div className= "navbar-search-icon">
               <Link to={'/cart'}><img src={assets.basket_icon} alt="" /></Link> 
                <div className={checkcart===0?"":"dot"}></div>
            </div> 
            {
             isAuthenticated?<button onClick={handlelogout}>logout</button>
                  :<button onClick={()=>setShowLogin(true)}>sign in</button>
            } 
        </div>
    </div>
  )
}

export default Navbar