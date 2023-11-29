import React, { useEffect, useState } from "react";

import "./styles.css";
import { Link } from "react-router-dom";
import { RequestsMT } from "../../../utils/RequestMuilt";


function AddOrder() {
  // React States
  const [errorMessages, setErrorMessages] = useState({});
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [haveTK, setHaveTK] = useState(false);
  const[infoTK, setInfoTK] = useState(null);
  const { post,get,put } =   RequestsMT();
//   const getInfo = async (aInfo) => {
//     let Infos = await get('/customers')
//       Infos = Infos.data.content;
//       let Info;
//       Infos.forEach(element => {
//           if (element.custId == aInfo)
//           Info= element;
//       });
//     setInfoTK(Info);
//   }
  useEffect(() => {
      const ah = localStorage.getItem('token');
      if (!ah)
      window.location.assign('/#/login');
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
        name:uname.value, description:description.value,manufacturer:manufacturer.name,unit:unit.value,status:status.value,price:price.value,images:images.files[0]
    };
    console.log(body );
    // if(!haveTK){
      const res = await post('/products/create', body, )
      console.log(res);
    //   localStorage.setItem('cus_id', res.data)
     alert("Create Product successfully");
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
          <input type="text" name="uname" placeholder={infoTK&&infoTK.custName} required/>
          {renderErrorMessage("uname")}
        </div>
        <div className="input-container">
          <label>Description: </label>
          <input type="text" name="description" placeholder={infoTK&&infoTK.email} required />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>Manufacturer: </label>
          <input type="text" name="manufacturer" placeholder={infoTK&&infoTK.phone} required />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>Unit: </label>
          <input type="text" name="unit" placeholder={infoTK&&infoTK.address} required />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>File: </label>
          <input type="file" name="images" placeholder={infoTK&&infoTK.address} required />
          {renderErrorMessage("pass")}
              </div>
              <div className="input-container">
          <label>Price: </label>
          <input type="text" name="price" placeholder={infoTK&&infoTK.address} required />
          {renderErrorMessage("pass")}
              </div>
              <div className="input-container">
          <label>Status(ACTIVE,ON_LEAVE,TERMINATED): </label>
          <input type="text" name="status" placeholder={infoTK&&infoTK.address} required />
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

export default AddOrder;