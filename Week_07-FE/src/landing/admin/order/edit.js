import React, { useEffect, useState } from "react";

import "./styles.css";
import { Link } from "react-router-dom";
import { RequestsMT } from "../../../utils/RequestMuilt";
import { useParams } from "react-router-dom/cjs/react-router-dom.min";


function OrderEdit() {
  // React States
  const [errorMessages, setErrorMessages] = useState({});
  const [isSubmitted, setIsSubmitted] = useState(false);
  const params = useParams();
  console.log("params nè",params);
  const [haveTK, setHaveTK] = useState(false);
  const[infoTK, setInfoTK] = useState(null);
  const { post,get,put } =   RequestsMT();
  const getInfo = async () => {
    let Info = await get(`/products/${params.id}`)
    console.log(Info);
    setInfoTK(Info.data);
  }
  useEffect(() => {
      const ah = localStorage.getItem('token');
      if (!ah)
      window.location.assign('/#/login');
      getInfo()
    // if (ah) {
    // //   setHaveTK(true);
    // //   getInfo(ah)
    // }
  },[])

  // User Login info
  const database = [
    {
      username: "user1",
      password: "pass1"
    },
    {
      username: "user2",
      password: "pass2"
    }
  ];

  const errors = {
    uname: "invalid email",
    pass: "invalid password"
    };
 

  const handleSubmit = async(event) => {
    //Prevent page reload
    event.preventDefault();

    var { uname, description,manufacturer,unit,status,images,price } = document.forms[0];

      console.log({ uname, description, manufacturer, unit, status, images, price });
      const token=localStorage.getItem('token');
      const body = {
        token: token,
        name: uname.value?uname.value:infoTK.name,
        description: description.value?description.value:infoTK.description,
        manufacturer: manufacturer.value?manufacturer.value:infoTK.manufacturer,
        unit: unit.value?unit.value:infoTK.unit,
        status: status.value?status.value:infoTK.status,
        price: price.value?price.value:infoTK.price,
        
    };
    if (images.files[0]) body.images = images.files[0];
    console.log(body );
    // if(!haveTK){
      const res = await put(`/products/update/${params.id}`, body, )
      console.log(res);
    //   localStorage.setItem('cus_id', res.data)
     alert("Update Product successfully");
    window.location.assign('/#/admin/product');
    // }
    // else {
    //   const ah= localStorage.getItem('cus_id');
    //   const res = await put(`/customers/update/${ah}`, body)
    //   alert("Update customer successfully");
    //   window.location.assign('/');
    // }
  };

  // Generate JSX code for error message
  const renderErrorMessage = (name) =>
    name === errorMessages.name && (
      <div className="error">{errorMessages.message}</div>
    );

  // JSX code for login form
  const renderForm = (
    <div className="form">
      <form onSubmit={handleSubmit}>
        <div className="input-container">
          <label>Name: </label>
          <input type="text" name="uname" placeholder={infoTK&&infoTK.name} />
          {renderErrorMessage("uname")}
        </div>
        <div className="input-container">
          <label>Description: </label>
          <input type="text" name="description" placeholder={infoTK&&infoTK.description}  />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>Manufacturer: </label>
          <input type="text" name="manufacturer" placeholder={infoTK&&infoTK.manufacturer}  />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>Unit: </label>
          <input type="text" name="unit" placeholder={infoTK&&infoTK.unit}  />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>File: </label>
          <input type="file" name="images" placeholder={infoTK&&infoTK.address}  />
          {renderErrorMessage("pass")}{infoTK && infoTK.images.map((i) => {
            return <img src={"https://res.cloudinary.com/practicaldev/image/fetch/s--Tmx9gqIx--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://i2.wp.com/blogreact.com/wp-content/uploads/2020/06/charts.png%3Ffit%3D750%252C398%26ssl%3D1"} style={{maxHeight:'50px'}}></img>
          })}
              </div>
              <div className="input-container">
          <label>Price: </label>
          <input type="text" name="price" placeholder={infoTK&&infoTK.price}  />
          {renderErrorMessage("pass")}
          
              </div>
              <div className="input-container">
          <label>Status(ACTIVE,ON_LEAVE,TERMINATED): </label>
          <input type="text" name="status" placeholder={infoTK&&infoTK.status}  />
          {renderErrorMessage("pass")}
        </div>
        <div className="button-container">
          <input type="submit" />
        </div>
      </form>
    </div>
  );

  return (
    <div className="app">
      <div className="login-form">
        <div className="title">Add Product</div>
              {isSubmitted ? <div>User is successfully logged in</div> : renderForm}
              </div>
    </div>
  );
}

export default OrderEdit;