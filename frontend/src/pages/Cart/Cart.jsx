import React, { useContext, useEffect, useState } from "react";
import "./Cart.css";
import { StoreContext } from "../../Context/StoreContext";
import { useNavigate, useSearchParams } from "react-router-dom";
import ApiService from "../../apiservices/ApiService";
const Cart = () => {
  const { cartItems, food_list, removeFromCart,getTotalCartAmount } = useContext(StoreContext);

  const navigate=useNavigate();
  
  const[loggeduser,setLoggeduser]=useState('')

  const[usercartlist,setUsercartlist]=useState(0);

  const [total,setTotal]=useState(0)


  useEffect(()=>{
      const getLoggedInUser = async () => {
          try {
            const response = await ApiService.getloggeduser();
            setLoggeduser(response.user);

            const cartlist = response.user.usercartitems;

            const quantityMap = cartlist.reduce((acc, itemId) => {
              acc[itemId] = (acc[itemId] || 0) + 1;
              return acc;
            }, {});

            const uniqueItemIds = new Set(cartlist);

            const usercartitems=await ApiService.getuserfooditems([...uniqueItemIds])
  
            const itemsWithQuantity = usercartitems.fooditems.map(item => ({
              ...item,
              quantity: quantityMap[item.id]
            }));

            setUsercartlist(itemsWithQuantity)

            const totalamount=itemsWithQuantity.reduce((sum,item)=>{
              return sum+(item.price*item.quantity)
            },0)
            setTotal(totalamount)

            console.log("userfooditemscartlie",usercartitems)

          } catch (error) {
            console.error('Error fetching logged-in user:', error);
          }
        };
        getLoggedInUser();
  },[])


  return (
    <div className="cart">

      <div className="cart-items">
        <div className="cart-items-title">
          <p>Items</p>
          <p>Title</p>
          <p>Price</p>
          <p>Quantity</p>
          <p>Total</p>
          <p>Remove</p>
        </div>
        <br />
        <hr />
        {usercartlist&&usercartlist.map((item, index)=>{
         
            return (
                <div key={index}>
                    <div className='cart-items-title cart-items-item'>
                      <img src={`data:image/png;base64,${item.image}`} alt=" " />
                      <p>{item.name}</p>
                      <p>${item.price}</p>
                      <p>{item.quantity}</p>
                      <p>${item.price*item.quantity}</p>
                      <p className="cross" onClick={()=>removeFromCart(item._id)}>x</p>
                    </div>
                    <hr />
                </div>
               
              )

        })}
      </div>

      <div className="cart-bottom">
        <div className="cart-total">
            <h2>Cart Totals</h2>
            <div>
                <div className="cart-total-details">
                  <p>SubTotal</p>
                  <p>${total}</p>
                </div>
                <hr />

                <div className="cart-total-details">
                  <p>Delivery Fee</p>
                  <p>${total===0?0:2}</p>
                </div>
                 <hr />

                <div className="cart-total-details">
                   <b>Total</b>
                   <b>${total===0?0:getTotalCartAmount()+2}</b>
                </div>
            </div>
            <button onClick={()=>navigate('/order')}>PROCEED TO CHECKOUT</button>
        </div>
        
        <div className= "cart-promocode">
            <div>
                <p>If you have a promo code, Enter it here</p>
                <div className= 'cart-promocode-input'>
                    <input type= "text" placeholder='promo code' />
                    <button>Submit</button>
                </div>
            </div>
        </div>
        
      </div>


    </div>
  );
};

export default Cart;
