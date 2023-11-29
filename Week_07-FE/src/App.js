import Template from "./template/Template";
import ProductDetail from "./products/detail/ProductDetail";
import { Switch, Route } from "react-router-dom";
import Landing from "./landing/Landing";
import ProductList from "./products/ProductList";
import LoginPage from "./landing/login";
import CartPage from "./landing/cart";
import CusInfo from "./landing/cusInfo";
import ProductLstPage from "./landing/admin/product";
import AddProduct from "./landing/admin/product/add";
import AdminHomePage from "./landing/admin/statistics";
import UpdateProduct from "./landing/admin/product/edit";
import CustomerLstPage from "./landing/admin/customer";
import OrderLstPage from "./landing/admin/order";
import AddOrder from "./landing/admin/order/add";
import OrderEdit from "./landing/admin/order/edit";
import AddAdmin from "./landing/admin/addAdmin";
import LstEmloyPage from "./landing/admin/addAdmin/lstemployed";

function App() {
  return (
    <Template>
      <Switch>
      <Route path="/cart" exact>
          <CartPage />
        </Route>
        <Route path="/info-customer" exact>
          <CusInfo />
        </Route>
      <Route path="/login" exact>
          <LoginPage />
        </Route>
        <Route path="/products" exact>
          <ProductList />
        </Route>
        <Route path="/products/:slug">
          <ProductDetail />
        </Route>
        <Route path="/" exact>
          <Landing />
        </Route>
        <Route path="/admin" exact>
          <AdminHomePage />
        </Route>
        <Route path="/admin/product" exact>
          <ProductLstPage />
        </Route>
        <Route path="/admin/product/add" exact>
          <AddProduct />
        </Route>
        <Route path="/admin/product/update/:id" exact>
          <UpdateProduct />
        </Route>
        <Route path="/admin/customer" exact>
          <CustomerLstPage />
        </Route>
        <Route path="/admin/order" exact>
          <OrderLstPage />
        </Route>
        <Route path="/admin/order/add" exact>
          <AddOrder />
        </Route>
        <Route path="/admin/order/update/:id" exact>
          <OrderEdit />
        </Route>
        <Route path="/admin/add" exact>
          <AddAdmin />
        </Route>
        <Route path="/admin/list" exact>
          <LstEmloyPage />
        </Route>

      </Switch>
    </Template>
  );
}

export default App;
