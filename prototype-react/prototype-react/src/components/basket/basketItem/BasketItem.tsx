import React from 'react';
import './BasketItem.css';

import { Part } from '../../../ts/webshop';

const BasketItem = (props: { item: Part, count: number, removeItem: (item: Part) => void, setCount: (val: number) => void}): JSX.Element => {
    const [deleteIsHovered, setDeleteHover] = React.useState(false);
    const [itemIsHovered, setItemHover] = React.useState(false);

    const getClasses = (): string => {
        return "inline-clear-button" + (itemIsHovered ? "" : "hidden");
    }

    return (
        <div className="BasketItem" onMouseOver={e => setItemHover(true)} onMouseLeave={e => setItemHover(false)}>
            <div className="item-name">{props.item.name}</div>
            <input className="chip item-count-input" value={props.count} onChange={e => props.setCount(Number(e.target.value))}/>
            <button className={`chip item-clear-button ${itemIsHovered ? "" : "semi-hidden"}`}
                onMouseLeave={e => setDeleteHover(false)}
                onMouseOver={e => setDeleteHover(true)}
                onClick={e => props.removeItem(props.item)}>
                {deleteIsHovered ? "x" : "-"}
            </button>
        </div>
    )
}
export default BasketItem;