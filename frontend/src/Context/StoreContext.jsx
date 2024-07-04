import { createContext, useEffect, useState } from "react";
import { food_list } from "../assets/assets";
import ApiService from "../apiservices/ApiService";

export const StoreContext = createContext(null);

const StoreContextProvider = (props) => {

  const [cartItems, setCartItems] = useState({});


  const [isAuthenticated,setIsAuthenticated]=useState(!!localStorage.getItem('token'))


  const [checkcart, setCheckcart] = useState(0);
  const fetchCartItems = async () => {
    try {
        const response = await ApiService.getloggeduser();
        setCheckcart(response.user.usercartitems.length);
        console.log("context",checkcart)
        console.log("checkcart", response.user.usercartitems.length);
    } catch (error) {
        console.error("Error fetching cart items:", error);
    }
};



  const addToCart = (itemId) => {
    if (!cartItems[itemId]) {
      setCartItems((prev) => ({ ...prev, [itemId]: 1 }));
    } else {
      setCartItems((prev) => ({ ...prev, [itemId]: prev[itemId] + 1 }));
    }
  };



  const removeFromCart = (itemId) => {
    setCartItems((prev) => ({ ...prev, [itemId]: prev[itemId] - 1 }));
  };

  
  const getTotalCartAmount = () =>{
     let totalAmount = 0;
     for (const item in cartItems)
     {
         if (cartItems[item] > 0) {
             let itemInfo = food_list.find( (product) => product._id === item);
             totalAmount += itemInfo.price * cartItems[item];
         }
     }
     return totalAmount
  }






  const contextValue = {
    food_list,cartItems,setCartItems,addToCart,removeFromCart,getTotalCartAmount,isAuthenticated,setIsAuthenticated,checkcart,fetchCartItems,setCheckcart
  };

  return (
    <StoreContext.Provider value={contextValue}>
      {props.children}
    </StoreContext.Provider>
  );
};

export default StoreContextProvider;
