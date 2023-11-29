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
function ProductLstPage() {
    const [dataPro, setDataPro] = useState([]);
    const [reload,setReload] = useState(0);
    const { get,del } = Requests();
    const callData = async() => {
      const result = await get('/products?page=0&amount=10000');
      setDataPro(result.data.content);
    }
    const columns = [
        {
            title: 'ID',
            dataIndex: 'productID',
            key: 'productID',
            render: (text) => <a>{text}</a>,
          },
        {
          title: 'Name',
          dataIndex: 'name',
          key: 'name',
          render: (text) => <a>{text}</a>,
        },
        {
          title: 'unit',
          dataIndex: 'unit',
          key: 'unit',
        },
        {
          title: 'manufacturer',
          dataIndex: 'manufacturer',
          key: 'manufacturer',
        },
      {
        title: 'image',
        dataIndex: 'image',
        key: 'image',
        render: (tags) => (
                <img src={`${tags}`} style={{maxHeight:'90px'}}></img>
            )
        },
        {
            title: 'price',
            dataIndex: 'price',
            key: 'price',
          
          },
        {
          title: 'status',
          key: 'status',
          dataIndex: 'tstatusags',
          render: (_, { tags }) => (
              <>
                  {/* tags=[...tags]
              {tags.map((tag) => {
                let color = tag.length > 5 ? 'geekblue' : 'green';
                if (tag === 'loser') {
                  color = 'volcano';
                }
                return (
                  <Tag color={color} key={tag}>
                    {tag.toUpperCase()}
                  </Tag>
                );
              })} */}
            </>
          ),
        },
        {
          title: 'Action',
          key: 'action',
          render: (_, record) => (
              <Space size="middle">
                  <button onClick={async() => {
                      await del(`/products/delete/${record.productID}`, { token: localStorage.getItem('token') });
                     alert('delete product successfully');
                     // window.location.assign('/#/admin/product');
                      setReload((pre=>pre+1))
                }}>delete</button>
              <Link to={`/admin/product/update/${record.productID}`}>Update</Link>
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
    fontSize: '32px'}}>Product Manager</h1></div>
          <Link to={"/admin/product/add"} style={{textDecoration:'none !importance'}} ><span style={{padding:"5px", backgroundColor:"green", borderRadius:"10px",margin:30, color:'white', textDecoration:'none !importance'}}>Create New +</span></Link>
     <Table columns={columns} style={{marginTop:"25px",marginLeft:10,marginRight:10}} dataSource={dataPro}></Table>
</div>
  );
}

export default ProductLstPage;