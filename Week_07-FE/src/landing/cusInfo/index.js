import React, { useEffect, useState } from "react";

import "./styles.css";
import { Link } from "react-router-dom";
import { Requests } from "../../utils/Request";


function CusInfo() {
  // React States
  const [errorMessages, setErrorMessages] = useState({});
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [haveTK, setHaveTK] = useState(false);
  const[infoTK, setInfoTK] = useState(null);
  const { post,get,put } =   Requests();
  const getInfo = async (aInfo) => {
    let Infos = await get('/customers')
      Infos = Infos.data.content;
      let Info;
      Infos.forEach(element => {
          if (element.custId == aInfo)
          Info= element;
      });
    setInfoTK(Info);
  }
  useEffect(() => {
    const ah= localStorage.getItem('cus_id');
    if (ah) {
      setHaveTK(true);
      getInfo(ah)
    }
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

    var { uname, email,phone,address } = document.forms[0];

    
    const body = {
      custName: uname.value ?  uname.value : infoTK.custName,
      email: email.value?  email.value : infoTK.email,
      phone: phone.value? phone.value : infoTK.phone,
      address: address.value? address.value : infoTK.address
    };
    console.log(body);
    if(!haveTK){
      const res = await post('/customers/create', body)
      localStorage.setItem('cus_id', res.data)
      alert("Create customer successfully");
      window.location.assign('/');
    }
    else {
      const ah= localStorage.getItem('cus_id');
      const res = await put(`/customers/update/${ah}`, body)
      alert("Update customer successfully");
      window.location.assign('/');
    }
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
          <input type="text" name="uname" placeholder={infoTK&&infoTK.custName} required={!haveTK}/>
          {renderErrorMessage("uname")}
        </div>
        <div className="input-container">
          <label>Email: </label>
          <input type="email" name="email" placeholder={infoTK&&infoTK.email} required={!haveTK} />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>Phone: </label>
          <input type="text" name="phone" placeholder={infoTK&&infoTK.phone} required={!haveTK} />
          {renderErrorMessage("pass")}
        </div>
        <div className="input-container">
          <label>Address: </label>
          <input type="text" name="address" placeholder={infoTK&&infoTK.address} required={!haveTK} />
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
        <div className="title">Customer infomation</div>
              {isSubmitted ? <div>User is successfully logged in</div> : renderForm}
              </div>
    </div>
  );
}

export default CusInfo;