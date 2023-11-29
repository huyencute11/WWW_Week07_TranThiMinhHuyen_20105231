import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import { useState } from "react";

function Header() {

  const [openedDrawer, setOpenedDrawer] = useState(false)

  function toggleDrawer() {
    setOpenedDrawer(!openedDrawer);
  }

  function changeNav(event) {
    if (openedDrawer) {
      setOpenedDrawer(false)
    }
  }

  return (
    <header>
      <nav className="navbar fixed-top navbar-expand-lg navbar-light bg-white border-bottom">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/" onClick={changeNav}>
            <FontAwesomeIcon
              icon={["fab", "bootstrap"]}
              className="ms-1"
              size="lg"
            />
            <span className="ms-2 h5">Shop</span>
          </Link>

          <div className={"navbar-collapse offcanvas-collapse " + (openedDrawer ? 'open' : '')}>
            {localStorage.getItem('token') ? <>
              <ul className="navbar-nav me-auto mb-lg-0">
                <li className="nav-item">
                  <Link to="/admin/product" className="nav-link" replace onClick={changeNav}>
                    Product 
                  </Link>
                </li>
              </ul>
              <ul className="navbar-nav me-auto mb-lg-0">
              <li className="nav-item">
                <Link to="/admin/customer" className="nav-link" replace onClick={changeNav}>
                  Customer 
                </Link>
              </li>
              </ul>
              <ul className="navbar-nav me-auto mb-lg-0">
              <li className="nav-item">
                <Link to="/admin/order" className="nav-link" replace onClick={changeNav}>
                  Order
                </Link>
              </li>
              </ul>
            </> : <><ul className="navbar-nav me-auto mb-lg-0">
              <li className="nav-item">
                <Link to="/info-customer" className="nav-link" replace onClick={changeNav}>
                  Customer infomation
                </Link>
              </li>
            </ul>
            <Link to={'/cart'} className="btn btn-outline-dark me-3 d-none d-lg-inline">
              <FontAwesomeIcon icon={["fas", "shopping-cart"]} />
              <span className="ms-3 badge rounded-pill bg-dark">{localStorage.getItem('cart')?localStorage.getItem('cart').split(",").length:0}</span>
            </Link></>}
          </div>

          <div className="d-inline-block d-lg-none">
            <Link to={'/cart'} className="btn btn-outline-dark">
              <FontAwesomeIcon icon={["fas", "shopping-cart"]} />
              <span className="ms-3 badge rounded-pill bg-dark">{localStorage.getItem('cart')?localStorage.getItem('cart').split(",").length:5}</span>
            </Link>
            {/* <button className="navbar-toggler p-0 border-0 ms-3" type="button" onClick={toggleDrawer}>
              <span className="navbar-toggler-icon"></span>
            </button> */}
          </div>
        </div>
      </nav>
    </header>
  );
}

export default Header;
