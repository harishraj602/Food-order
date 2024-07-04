import React, { useCallback, useContext, useEffect, useState } from 'react'
import './FoodItem.css'
import { assets } from '../../assets/assets'
import { StoreContext } from '../../Context/StoreContext'
import ApiService from '../../apiservices/ApiService'


const FoodItem = ({id,name,price,image,description}) => {


const{isAuthenticated,fetchCartItems} = useContext(StoreContext);

const[loggeduser,setLoggeduser]=useState('')

const [updateCount, setUpdateCount] = useState(0); 


useEffect(()=>{
    const getLoggedInUser = async () => {
        try {
          const response = await ApiService.getloggeduser();
          setLoggeduser(response.user);
          // Update updateCount based on loggeduser.usercartitems
          const counts = response.user.usercartitems.filter(item => item === id).length;
          setUpdateCount(counts);
        } catch (error) {
          console.error('Error fetching logged-in user:', error);
        }
      };
      getLoggedInUser();
},[id])


const additemtocart=async(foodid)=>{
    const response=await ApiService.additem(loggeduser.id,foodid)
    setUpdateCount(prev=>prev+1) 
    await fetchCartItems()
}
const removeitemfromcart=async(foodid)=>{
    const response=await ApiService.removeitem(loggeduser.id,foodid)
    setUpdateCount(prev=>prev-1)
    await fetchCartItems()
}


  return (
    <div className='food-item'>
        <div className="food-item-img-container">
             <img className='food-item-image' src={`data:image/png;base64,${image}`} alt=" " />
             {isAuthenticated&&(updateCount===0
                ?<img className='add' onClick={()=>additemtocart(id)} src={assets.add_icon_white} alt=""/>
                :<div className='food-item-counter'> 
                    <img onClick={()=>removeitemfromcart(id)} src={assets.remove_icon_red} alt="" />
                    <p>{updateCount}</p>
                    <img onClick={()=>additemtocart(id)} src={assets.add_icon_green} alt="" />
                 </div>
             )
            }
            
        </div>
        <div className="food-item-info">
            <div className="food-item-name-rating">
                <p>{name}</p>
                <img src={assets.rating_starts} alt="" />
            </div>
    
            <p className="food-item-desc">{description}</p>
            <p className="food-item-price">${price}</p>
        </div>
    </div>
  )
}

export default FoodItem