import React, { useEffect, useState } from "react";

import "./styles.css";
import { Link } from "react-router-dom";
import { Requests } from "../../utils/Request";
import { Button, Modal } from 'antd';


function CartPage() {
    const [modal, contextHolder] = Modal.useModal();
    const [dataPro, setDataPro] = useState([]);
    const [value, setValue] = useState(0);
    const { get,post } = Requests();
    const [valueNote, setValueNote] = useState('');
    const [product, setProduct] = useState([]);
    const [productID, setProductID] = useState([]);
    const callData = async () => {
        let result =[]
        const a = localStorage.getItem('cart') ? localStorage.getItem('cart').split(",") : [];
        console.log(a);
        let total = 0;
        for (let index = 0; index < a.length; index++) {
            const rs = await get(`/products/${a[index]}`);
            // console.log(rs);
            total += rs.data.price;
            setProductID((pre) => [...product, a[index]])
            rs.data.id = a[index];
            result.push(rs.data) 
        }
        setValue(total);
    console.log("LQ",result);
        setDataPro(result);
        result.map((i) => setProduct((pre) => [...product, i.name]))
  }
  useEffect(() => {
    callData();
  }, [])
  useEffect(() => {
    console.log(valueNote);
  }, [valueNote])
  const countDown = (str) => {
    let secondsToGo = 15;

    const instance = modal.success({
      title: 'Payment Success',
        content: `${str}This modal will be destroyed after ${secondsToGo} second.`,
        onOk: () => {
            window.location.assign('/')
      }
    });

    const timer = setInterval(() => {
      secondsToGo -= 1;
      instance.update({
          content: `${str}
        This modal will be destroyed after ${secondsToGo} second.`,
      });
    }, 1000);

    setTimeout(() => {
        clearInterval(timer);
        window.location.assign('/')
      instance.destroy();
    }, secondsToGo * 1000);
  };
    return (<div class="container">
        {contextHolder}
    <div class="divTable div-hover">
        
            <div class="rowTable bg-primary text-white pb-2">
                <div class="divTableCol">Product</div>
                <div class="divTableCol">Name</div>
                <div class="divTableCol">Quantity</div>
                <div class="divTableCol">Price</div>
                <div class="divTableCol">Total</div>
                <div class="divTableCol">Actions</div>
            </div>
           {
                dataPro.map((i) => {
                    
            return(
                <div class="rowTable">
                <div class="divTableCol">
                   <img style={{maxHeight:50}} src={i.images[0]}></img>
                </div>
                <div class="divTableCol"><strong class="label label-warning">{i.name}</strong></div>
                <div class="divTableCol">
                    <strong>1</strong>
                </div>
                    <div class="divTableCol"><strong>{i.price}$</strong></div>
                <div class="divTableCol"><strong>{i.price}$</strong></div>
                <div class="divTableCol">
                        <button type="button" class="btn btn-danger"onClick={() => {
                            let a = localStorage.getItem('cart').split(',');
                            let b = a.filter(e => e != i.id); 
                            if (b.length > 0) {
                              localStorage.setItem('cart', b.toString());  
                            } else {
                                localStorage.removeItem('cart');
                            }
                            
                            window.location.assign('/#/cart')
                            

                    }}><span class="fa fa-remove" ></span> Remove</button>
                </div>
            </div>)
               })
        }
        
            
            <div class="rowTable">
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"><h5>Subtotal</h5></div>
                <div class="divTableCol">
                    <h5><strong>€{value}</strong></h5>
                </div>
            </div>
            <div class="rowTable">
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"><h5>Prezzo totale</h5></div>
                <div class="divTableCol">
                    <h5><strong>€{value}</strong></h5>
                </div>
            </div>
            <div class="rowTable">
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"><h3>Total</h3></div>
                <div class="divTableCol">
                    <h3><strong>€{value}</strong></h3>
                </div>
           </div>
           <div class="rowTable">
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"><h5>Note</h5></div>
                <div class="divTableCol">
                    <input id="note" type="text" onChange={(value)=>{setValueNote(value.target.value)}}></input>
                </div>
            </div>
            <div class="rowTable">
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol"></div>
                <div class="divTableCol">
                    <button onClick={() => { localStorage.removeItem('cart'); alert("Payment success!!!"); window.location.assign('/')}} type="button" class="btn btn-default col-6"> Delete all </button>
                </div>
                <div class="divTableCol">
                   <button onClick={async() => {
                       const aInfo = localStorage.getItem('cus_id');
                       if (aInfo) {
                           let Infos = await get('/customers')
                           Infos = Infos.data.content;
                           let Info;
                           Infos.forEach(element => {
                               if (element.custId == aInfo)
                               Info= element;
                           });
                           const Params = productID.map((i) => {
                               return {
                                   productId: i,
                                   quantity: 1,
                                   note:valueNote
                               }
                           })
                           console.log(Params);
                           //localStorage.removeItem('cart');
                           await post('/orders/create',{customerId:aInfo,orderDetails:Params})
                           countDown(`Payment success! Your order will be delivered to ${Info.phone}, ${Info.custName}, ${Info.address}.Your order includes:${product.toString()}.Note:<${valueNote}>.  `);
                          // alert(`Payment success! Your order will be delivered to ${Info.phone}, ${Info.custName}, ${Info.address} `);
                          
                       }
                       else{
                       alert("You must fill in the information before returning here to pay.!!!");
                    //    localStorage.removeItem('cart');
                    //    alert("Payment success!!!");
                        window.location.assign('/#/info-customer')}
                   }} type="button" class="btn btn-success">Pay</button>
                </div>
            </div>        
    </div>
</div>)
}

export default CartPage;