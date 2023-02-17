import React from "react";
import "./ProductThumbnail.css";

const ProductThumbnail = (props: any): JSX.Element => {
    return (
        <div className="chip ProductThumbnail">
            <img src={props.image} alt={props.name} />
            <h1>{props.name}</h1>
            <h2>{props.price}</h2>
        </div>
    );
};
export default ProductThumbnail;