import React from 'react';
import './BasketItem.css';

import { IBasketItem, Part } from '../../../ts/webshop';

interface BasketItemProps{
    batchPart: IBasketItem;
    setCount: (val: number) => void;
    removeItem: (item: Part) => void;
}

export default function BasketItem({batchPart, setCount, removeItem}: BasketItemProps): JSX.Element {
    const [deleteIsHovered, setDeleteHover] = React.useState(false);
    const [itemIsHovered, setItemHover] = React.useState(false);

    return (
        <div className="BasketItem" onMouseOver={e => setItemHover(true)} onMouseLeave={e => setItemHover(false)}>
            <div className="item-name">{batchPart.part.name}</div>
            <input className="chip item-count-input" value={batchPart.count} onChange={e => setCount(Number(e.target.value))}/>
            <button className={`chip item-clear-button ${itemIsHovered ? "" : "semi-hidden"}`}
                onMouseLeave={e => setDeleteHover(false)}
                onMouseOver={e => setDeleteHover(true)}
                onClick={e => removeItem(batchPart.part)}>
                {deleteIsHovered ? "x" : "-"}
            </button>
        </div>
    )
}