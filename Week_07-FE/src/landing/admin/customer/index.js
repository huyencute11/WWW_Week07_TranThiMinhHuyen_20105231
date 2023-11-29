import React, { useEffect, useState } from "react";
import { Space, Table, Tag } from 'antd';

//import "./styles.css";
import { Link } from "react-router-dom";
import { Requests } from "../../../utils/Request";
//import { Requests } from "../../utils/Request";



  const data = [
    {
      key: '1',
      name: 'John Brown',
      age: 32,
      address: 'New York No. 1 Lake Park',
      tags: ['nice', 'developer'],
    },
    {
      key: '2',
      name: 'Jim Green',
      age: 42,
      address: 'London No. 1 Lake Park',
      tags: ['loser'],
    },
    {
      key: '3',
      name: 'Joe Black',
      age: 32,
      address: 'Sydney No. 1 Lake Park',
      tags: ['cool', 'teacher'],
    },
  ];
function CustomerLstPage() {
    const [dataPro, setDataPro] = useState([]);
    const [reload,setReload] = useState(0);
    const { get,del } = Requests();
    const callData = async() => {
      const result = await get('/customers?page=0&amount=10000');
      setDataPro(result.data.content);
    }
    const columns = [
        {
            title: 'ID',
            dataIndex: 'custId',
            key: 'custId',
            render: (text) => <a>{text}</a>,
          },
        {
          title: 'Name',
          dataIndex: 'custName',
          key: 'custName',
          render: (text) => <a>{text}</a>,
        },
        {
          title: 'Email',
          dataIndex: 'email',
          key: 'email',
        },
        {
          title: 'Address',
          dataIndex: 'address',
          key: 'address',
        },
        {
            title: 'Phone',
            dataIndex: 'phone',
            key: 'phone',
          
          },
        {
          title: 'Status',
          key: 'deleted',
          dataIndex: 'tstatusags',
          render: (_, { tags }) => (
              <>
              {!tags?<Tag color={"green"} key={tags}>
                    {"Active"}
                  </Tag>:<Tag color={"red"} key={tags}>
                    {"Deleted"}
                  </Tag>
                
                  
               
              }
            </>
          ),
        },
        {
          title: 'Action',
          key: 'action',
          render: (_, record) => (
              <Space size="middle">
                  <button onClick={async() => {
                      await del(`/customers/delete/${record.custId}`, { token: localStorage.getItem('token') });
                     alert('delete product successfully');
                     // window.location.assign('/#/admin/product');
                      setReload((pre=>pre+1))
                }}>delete</button>
              {/* <Link to={`/admin/product/update/${record.productID}`}>Update</Link> */}
            </Space>
          ),
        },
      ];
    useEffect(() => {
      callData();
    }, [reload])
  return (
    <div style={{ marginTop: "100px" }}>
      <div><h1 style={{margin: '23px',
    fontSize: '32px'}}>Customer Manager</h1></div>
          {/* <Link to={"/admin/product/add"} ><span style={{padding:"5px", backgroundColor:"green", borderRadius:"10px",margin:30, color:'white'}}>Create New</span></Link> */}
     <Table columns={columns} dataSource={dataPro}></Table>
</div>
  );
}

export default CustomerLstPage;