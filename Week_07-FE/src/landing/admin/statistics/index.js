import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Requests } from "../../../utils/Request";
import { Chart } from "react-google-charts";
import { Button, Select } from "antd";
import { DatePicker, Space } from 'antd';


function AdminHomePage() {
  const [dataPro0, setDataPro0] = useState([]);
  const [dataPro, setDataPro] = useState([]);
  const [dataPro1, setDataPro1] = useState([]);
  const [dataPro2, setDataPro2] = useState([]);
  const [dataPro3, setDataPro3] = useState([]);
  const [dataPro4, setDataPro4] = useState([]);
  const { get, del, post } = Requests();
  const { RangePicker } = DatePicker;
  const [st1, setSt1] = useState('2020-01-01');
  const [et1, setEt1] = useState('2024-01-01');

  const [st3, setSt3] = useState('2020-01-01');
  const [et3, setEt3] = useState('2024-01-01');
  const [st2, setSt2] = useState(1);
  useEffect(() => {
   setDataPro4(dataPro4.filter((i) => {
     if (new Date(st3) <= new Date(i.orderDate) && new Date(i.orderDate) <= new Date(et3))
       return i;
      })) 

  }, [et3])
  useEffect(() => {
    const ah = localStorage.getItem('token');
    if (!ah)
    window.location.assign('/#/login');

}, [])
  const callData = async () => {
    const result0= await get(`/employees?page=0&amount=10000&token=${localStorage.getItem('token')}`);
    setDataPro0(result0.data.content.map((i,idx) => { return { value: i.emp_id, label:i.fullName}}));
    const result = await get(`/statistics/topEmployees`,{date:'2023-11-20',token:localStorage.getItem('token')});
    const data = result.data.map((i) => {
      return[i.employeeName,i.totalPrice]
    })
    setDataPro([["Employees", "ToTal Price",], ...data]);
    const result1 = await get(`/statistics/saleReport`,{date:'2023-11-20',token:localStorage.getItem('token')});
    const data1 = result1.data.map((i) => {
      return[i.month,i.sale]
    })
    setDataPro1([[
      "Month",
      "revenue",
    ], ...data1]);
    const currentDate = new Date();
    
    const year = currentDate.getFullYear();
    
    // Lưu ý rằng getMonth() trả về tháng từ 0 đến 11, nên phải cộng thêm 1.
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    
    const day = currentDate.getDate().toString().padStart(2, '0');
    
    // Tạo định dạng "YYYY-MM-DD"
    const formattedDate = `${year}-${month}-${day}`;
    const result2 = await get(`/statistics/topProducts`,{date:formattedDate,token:localStorage.getItem('token')});
    const data2 = result2.data.map((i) => {
      return[i.productName,i.totalPrice]
    })
    setDataPro2([[
      "Product Name",
      "Total Price",
    ], ...data2]);


    const result3 = await get(`/statistics/topProductsInRange`,{startDate:st1,endDate:et1,token:localStorage.getItem('token')});
    const data3 = result3.data.map((i) => {
      return[i.productName,i.totalPrice]
    })
    setDataPro3([["Employees", "ToTal Price",], ...data3]);
    const result4 = await get(`/orders/byEmployee/${st2}`,{startDate:st1,endDate:et1,token:localStorage.getItem('token')});
    const data4 = result4.data.content.map((i) => {
      return[i.orderDate,i.totalPrice]
    })
    console.log(data4);
    setDataPro4([["OrderDate", "Total Price",], ...data4]);
  }
  useEffect(() => {
    callData();
    console.log(et1);
  }, [et1,st2])
  useEffect(() => {
    console.log(dataPro4);
  }, [dataPro4])
 
  const options = {
    chart: {
      title: "Company Performance",
      subtitle: "Statistics on today's business results",
    },
  };
   const data1 = [
    [
      "Month",
      "revenue",
    ],
    [1, 37.8],
    [2, 30.9],
    [3, 25.4],
    [4, 11.7],
    
  ];
  const options1 = {
    chart: {
      title: "Revenue growth",
      subtitle: "Monthly revenue growth($)",
    },
  };
  const options2 = {
    title: "Statistics for the highest-selling products in terms of revenue.",
  };
  const data2 = [
    ["Task", "Hours per Day"],
    ["Work", 11],
    ["Eat", 2],
    ["Commute", 2],
    ["Watch TV", 2],
    ["Sleep", 7],
  ];

  return (
    <div className="app" style={{ marginTop: '980px' }}>
      <h2>Statistics</h2>
      <Button style={{ position: 'right' }} onClick={() => { window.location.assign('/#/admin/add'); }}>Create new Employees</Button>
      <h2  > Statistics Company Performance</h2>
      <Chart
      chartType="Bar"
      width="100%"
      height="400px"
      data={dataPro}
      options={options}
      /><h2  > Statistics for the highest-selling products in terms of revenue.
      </h2>
      <Chart
      chartType="PieChart"
      data={dataPro2}
      options={options2}
      width={"100%"}
      height={"400px"}
    /><h2  > Company growth statistics chart</h2>
      <Chart
      chartType="Line"
      width="100%"
      height="400px"
      data={dataPro1}
      options={options1}
      />
      

<h2  > Product statistics chart over time period</h2>
      <div>
        Please chose start time end time:
        <RangePicker onChange={(e) => { setSt1(`${e[0].$y}-${e[0].$M}-${e[0].$D}`); setEt1(`${e[1].$y}-${e[1].$M}-${e[1].$D}`)}} />
      </div>
      <Chart
      chartType="Bar"
      width="100%"
      height="400px"
      data={dataPro3}
     // options={options}
      />
     <h2  > Order statistics chart for each emloyess</h2>
      <div>
      Please select employee and time period:
        <Select
    showSearch
    style={{ width: 200 }}
          placeholder="Search to Select"
          defaultValue={1}
    optionFilterProp="children"
    filterOption={(input, option) => (option?.label ?? '').includes(input)}
    filterSort={(optionA, optionB) =>
      (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
    }
          onChange={(e)=>{setSt2(e);}}
    options={dataPro0}
  /><RangePicker onChange={(e) => { setSt3(`${e[0].$y}-${e[0].$M}-${e[0].$D}`); setEt3(`${e[1].$y}-${e[1].$M}-${e[1].$D}`)}} />
      </div>

      <Chart
      chartType="Bar"
      width="100%"
      height="400px"
      data={dataPro4}
      //options={options}
      />
    </div>
  );
}

export default AdminHomePage;