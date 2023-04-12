import React from "react";
import "./ProductThumbnail.css";
import { Batch, Part } from "../../../ts/webshop";

const ProductThumbnail = (props: {batch?: Batch, part?: Part}): JSX.Element => {

    if(props.part !== undefined && props.part !== null) {
        return (
            <div className="chip ProductThumbnail">
                <div className="name-and-count">
                    <h2 className="part-name">{props.part.name}</h2>
                </div>
            </div>
        );
    } else if(props.batch !== undefined && props.batch !== null) {
        let sum: number = 0;
        for(let i = 0; i < props.batch.parts.length; i++) {
            sum += props.batch.parts[i].count;
        }
        return (
            <div className="chip ProductThumbnail process-chain" 
                style={{backgroundImage: "linear-gradient(90deg, transparent, var(--border-color) " + (props.batch.completionPercentage * 100-1) +"%, transparent  " + props.batch.completionPercentage * 100 +"% )"}}>
                <div className="name-and-count">
                    <h2 className="part-name">{props.batch.cmr}</h2>
                    <h2 className="count-completion">Completion: {props.batch.hasCompleted}</h2>
                    <h2 className="process-time">Parts:  {sum}</h2>
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