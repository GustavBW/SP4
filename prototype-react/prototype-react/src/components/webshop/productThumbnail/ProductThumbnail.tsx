import React from "react";
import "./ProductThumbnail.css";
import { Part } from "../../../ts/webshop";
import { ProcessChain } from "../../../ts/webshop";

const ProductThumbnail = (props: {part?: Part, chain?: ProcessChain}): JSX.Element => {

    if(props.part !== undefined && props.part !== null) {
        return (
            <div className="chip ProductThumbnail">
                <img src={props.part.image} alt={props.part.name} className="part-image"/>
                <div className="name-and-count">
                    <h2 className="part-name">{props.part.name}</h2>
                </div>
            </div>
        );
    } else if(props.chain !== undefined && props.chain !== null) {
        return (
            <div className="chip ProductThumbnail process-chain" 
                style={{backgroundImage: "linear-gradient(90deg, var(--background-color), var(--border-color) " + (props.chain.completionPercentage * 100-1) +"%, var(--background-color)  " + props.chain.completionPercentage * 100 +"% )"}}>
                <div className="name-and-count">
                    <h2 className="part-name">{props.chain.partName}</h2>
                    <h2 className="count-completion">Completion: {props.chain.currentCount} / {props.chain.totalCount}</h2>
                    <h2 className="process-time">Seconds pr. part:  {props.chain.processTimeSeconds}</h2>
                </div>
            </div>
        );
    } else {
        return (
            <div className="chip ProductThumbnail">
                <h2>Unknown Product</h2>
            </div>
        );
    }
};
export default ProductThumbnail;