import Image from "../../nillkin-case-1.jpg";
import { Link } from "react-router-dom";

function RelatedProduct(props) {
  const price = 10000;
  let percentOff;
  let offPrice = `${props.children.price}Ks`;

  if (props.percentOff && props.percentOff > 0) {
    percentOff = (
      <div
        className="badge bg-dim py-2 text-white position-absolute"
        style={{ top: "0.5rem", right: "0.5rem" }}
      >
        {props.percentOff}% OFF
      </div>
    );

    offPrice = (
      <>
        <del>{price}Ks</del> {price - (props.percentOff * price) / 100}Ks
      </>
    );
  }

  return (
    <Link
      to={`/products/${props.children.productID}`}
      className="col text-decoration-none"
      href="!#"
      replace
    >
      <div className="card shadow-sm">
        {percentOff}
        <img
          className="card-img-top bg-dark cover"
          height="200"
          alt=""
          src={props.children.image}
        />
        <div className="card-body">
          <h5 className="card-title text-center text-dark text-truncate">
          {props.children.name}
          </h5>
          <p className="card-text text-center text-muted">{props.children.price}$</p>
        </div>
      </div>
    </Link>
  );
}

export default RelatedProduct;
