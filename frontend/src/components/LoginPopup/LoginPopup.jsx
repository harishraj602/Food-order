import React, { useState } from "react";
import "./LoginPopup.css";
import { assets } from "../../assets/assets";
import ApiService from "../../apiservices/ApiService";
import { toast } from "react-toastify";

const LoginPopup = ({ setShowLogin }) => {

  const[currState,setCurrState]=useState("Sign Up")


  const[email,setEmail]=useState("")
  const[password,setPassword]=useState("")
  const[username,setUsername]=useState("")

// --------------------------------------------register user function
  const handleRegister= async()=>{
    if(!email||!password||!username){
      console.log("please fill all the fields")
    }
    const data={
      username,
      email,
      password
    }

    const registeruser= await ApiService.registeruser(data)
    console.log("registeruser",registeruser)
  }
// --------------------------------------------Login user function
   
  const handleLogin=async(e)=>{
    e.preventDefault()
    if(!email||!password){
      console.log("Please fill all the fields")
    }

    const data={email,password}
    const loginuser=await ApiService.loginuser(data)
    if(loginuser!=null||loginuser!=undefined)
      {
        localStorage.setItem('token',loginuser.token)
        setShowLogin(false)
        window.location.reload();
        toast.success("Logged in successful")
      }
    console.log("loginuser",loginuser)
  }


  

  return (
    <div className="login-popup">
      <form className="login-popup-container">

        <div className="login-popup-title">
          <h2>{currState}</h2>
          <img onClick={() => setShowLogin(false)} src={assets.cross_icon}alt=" "/>
        </div>

        <div className="login-popup-inputs">
            {currState==="Login"?<></>:<input type="text" value={username} onChange={(e)=>setUsername(e.target.value)} placeholder= 'Your name' required />}
            <input type="email" value={email} onChange={(e)=>setEmail(e.target.value)} placeholder= 'Your email' required />
            <input type="password" value={password} onChange={(e)=>setPassword(e.target.value)} placeholder= 'Password' required />
        </div>
        {currState==="Sign Up"?
                               <button onClick={handleRegister}>Create account</button>
                              :<button onClick={handleLogin}>Login</button>}
        
        <div className= "login-popup-condition">
            <input type="checkbox" required/>
            <p>By continuing, i agree to the terms of use & privacy policy.</p>
        </div>
        {currState=="Login"?<p>Create a new account? <span onClick={()=>setCurrState("Sign Up")}>Click here</span></p>
                           : <p>Already have an account? <span onClick={()=>setCurrState("Login")}>Login here</span></p>}
      </form>
    </div>
  );
};

export default LoginPopup;
