import React from "react";
import "./ProductThumbnail.css";
import { Part } from "../../../ts/webshop";

const ProductThumbnail = (props: {part: Part}): JSX.Element => {
    return (
        <div className="chip ProductThumbnail">
            <img src={props.part.image} alt={props.part.name} className="part-image"/>
            <div className="name-and-count">
                <h2 className="part-name">{props.part.name}</h2>
                <h2 className="in-stock">Stock: {props.part.inStock}</h2>
            </div>
        </div>
    );
};
export default ProductThumbnail;