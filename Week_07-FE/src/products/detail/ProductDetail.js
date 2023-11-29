import Image from "../../nillkin-case-1.jpg";
import RelatedProduct from "./RelatedProduct";
import Ratings from "react-ratings-declarative";
import { Link } from "react-router-dom";
import ScrollToTopOnMount from "../../template/ScrollToTopOnMount";
import { useParams } from "react-router-dom/cjs/react-router-dom.min";
import { useEffect, useState } from "react";
import { Requests } from "../../utils/Request";

const iconPath =
  "M18.571 7.221c0 0.201-0.145 0.391-0.29 0.536l-4.051 3.951 0.96 5.58c0.011 0.078 0.011 0.145 0.011 0.223 0 0.29-0.134 0.558-0.458 0.558-0.156 0-0.313-0.056-0.446-0.134l-5.011-2.634-5.011 2.634c-0.145 0.078-0.29 0.134-0.446 0.134-0.324 0-0.469-0.268-0.469-0.558 0-0.078 0.011-0.145 0.022-0.223l0.96-5.58-4.063-3.951c-0.134-0.145-0.279-0.335-0.279-0.536 0-0.335 0.346-0.469 0.625-0.513l5.603-0.815 2.511-5.078c0.1-0.212 0.29-0.458 0.547-0.458s0.446 0.246 0.547 0.458l2.511 5.078 5.603 0.815c0.268 0.045 0.625 0.179 0.625 0.513z";

function ProductDetail() {
  function changeRating(newRating) { }
  const params = useParams();
  console.log(11122, params);
  const [dataPro, setDataPro] = useState([]);
  const { get } = Requests();
  const callData = async() => {
    const result = await get(`/products/${params.slug}`);
    console.log(result.data);
    setDataPro(result.data);
    const result1 = await get('/products?page=0&num=8');
    setDataRelate(result1.data.content);
  }
  const [dataRelate, setDataRelate] = useState([]);
  
  useEffect(() => {
    callData();
    window.scrollTo(0, 0)
  }, [params])

  return (
    <div className="container mt-5 py-4 px-xl-5">
      <ScrollToTopOnMount/>
      <nav aria-label="breadcrumb" className="bg-custom-light rounded mb-4">
        <ol className="breadcrumb p-3">
          <li className="breadcrumb-item">
            <Link className="text-decoration-none link-secondary" to="/products">
              All Prodcuts
            </Link>
          </li>
          {/* <li className="breadcrumb-item">
            <a className="text-decoration-none link-secondary" href="!#">
              Cases &amp; Covers
            </a>
          </li> */}
          <li className="breadcrumb-item active" aria-current="page">
            {dataPro&&dataPro.name}
          </li>
        </ol>
      </nav>
      <div className="row mb-4">
        <div className="d-none d-lg-block col-lg-1">
          <div className="image-vertical-scroller">
            <div className="d-flex flex-column">
              {dataPro.images&&dataPro.images.map((_, i) => {
                let selected = i !== 1 ? "opacity-6" : "";
                return (
                  <a key={i} href="!#">
                    <img
                      className={"rounded mb-2 ratio " + selected}
                      alt=""
                      src={i}
                    />
                  </a>
                );
              })}
            </div>
          </div>
        </div>
        <div className="col-lg-6">
          <div className="row">
            <div className="col-12 mb-4">
              <img
                className="border rounded ratio ratio-1x1"
                alt=""
                src={dataPro.images&&dataPro.images[0]}
              />
            </div>
          </div>

          {/* <div className="row mt-2">
            <div className="col-12">
              <div
                className="d-flex flex-nowrap"
                style={{ overflowX: "scroll" }}
              >
                {Array.from({ length: 8 }, (_, i) => {
                  return (
                    <a key={i} href="!#">
                      <img
                        className="cover rounded mb-2 me-2"
                        width="70"
                        height="70"
                        alt=""
                        src={Image}
                      />
                    </a>
                  );
                })}
              </div>
            </div>
          </div> */}
        </div>

        <div className="col-lg-5">
          <div className="d-flex flex-column h-100">
            <h2 className="mb-1">{dataPro&&dataPro.name}</h2>
            <h4 className="text-muted mb-4">{dataPro&&dataPro.price} $</h4>

            <div className="row g-3 mb-4">
              <div className="col">
                {dataPro&&dataPro.status=='ACTIVE'?
                <button className="btn btn-outline-dark py-2 w-100" onClick={
                  () => {
                    const a = localStorage.getItem('cart');
                    if (a) {
                      const list = a.split(',');
                      if (list.includes(params.slug)) {
                        alert('The product is already in the shopping cart');
                      }
                      else {
                        localStorage.setItem('cart', `${params.slug},${a}`);
                        alert('Add to cart successfully');
                      }
                    }
                    else {
                      localStorage.setItem('cart', params.slug);
                      alert('Add to cart successfully');
                    }
                  }
                }>
                  Add to cart
                </button>:''}
              </div>
              <div className="col">
              {dataPro&&dataPro.status=='ACTIVE'?
                  <button className="btn btn-dark py-2 w-100" onClick={()=>{const a = localStorage.getItem('cart');
                  if (a) {
                    const list = a.split(',');
                    if (list.includes(params.slug)) {
                      ///alert('The product is already in the shopping cart');
                    }
                    else {
                      localStorage.setItem('cart', `${params.slug},${a}`);
                      //alert('Add to cart successfully');
                    }
                  }
                  else {
                    localStorage.setItem('cart', params.slug);
                    ///alert('Add to cart successfully');
                  } window.location.assign('/#/cart')}}>Buy now</button>
                 :''}
              </div>
            </div>

            <h4 className="mb-0">Details</h4>
            <hr />
            <dl className="row">
              <dt className="col-sm-4">Code</dt>
              <dd className="col-sm-8 mb-3">CDKHA_{params.slug}</dd>

              <dt className="col-sm-4">Unit</dt>
              <dd className="col-sm-8 mb-3">{dataPro&&dataPro.unit}</dd>

              <dt className="col-sm-4">Brand</dt>
              <dd className="col-sm-8 mb-3">No Brand</dd>

              <dt className="col-sm-4">Manufacturer</dt>
              <dd className="col-sm-8 mb-3">{dataPro&&dataPro.manufacturer}</dd>

              <dt className="col-sm-4">Color</dt>
              <dd className="col-sm-8 mb-3">Red, Green, Blue, Pink</dd>

              <dt className="col-sm-4">Status</dt>
              <dd className="col-sm-8 mb-3" ><span style={{color:dataPro?.status=='ACTIVE'?'green':'red'}}>{dataPro?.status}</span></dd>

              <dt className="col-sm-4">Rating</dt>
              <dd className="col-sm-8 mb-3">
                <Ratings
                  rating={4.5}
                  widgetRatedColors="rgb(253, 204, 13)"
                  changeRating={changeRating}
                  widgetSpacings="2px"
                >
                  {Array.from({ length: 5 }, (_, i) => {
                    return (
                      <Ratings.Widget
                        key={i}
                        widgetDimension="20px"
                        svgIconViewBox="0 0 19 20"
                        svgIconPath={iconPath}
                        widgetHoverColor="rgb(253, 204, 13)"
                      />
                    );
                  })}
                </Ratings>
              </dd>
            </dl>

            <h4 className="mb-0">Description</h4>
            <hr />
            <p className="lead flex-shrink-0">
              <small>
              {dataPro&&dataPro.description}
              </small>
            </p>
          </div>
        </div>
      </div>

      <div className="row">
        <div className="col-md-12 mb-4">
          <hr />
          <h4 className="text-muted my-4">Related products</h4>
          <div className="row row-cols-1 row-cols-md-3 row-cols-lg-4 g-3">
            {dataRelate.map((i,idx)=> {
              return (
                <RelatedProduct key={idx} percentOff={null}>{i}</RelatedProduct>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;
