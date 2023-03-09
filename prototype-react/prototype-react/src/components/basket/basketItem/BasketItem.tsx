import React from 'react';
import './BasketItem.css';

import { Part } from '../../../ts/webshop';

const BasketItem = (props: { item: Part, count: number, removeItem: (item: Part) => void, setCount: (val: number) => void}): JSX.Element => {
    const [deleteIsHovered, setDeleteHover] = React.useState(false);
    const [itemIsHovered, setItemHover] = React.useState(false);

    const getIfHovered = (): JSX.Element => {
        if (itemIsHovered){
            return (
                <button className="inline-clear-button"
                    onMouseLeave={e => setDeleteHover(false)}
                    onMouseOver={e => setDeleteHover(true)}
                    onClick={e => props.removeItem(props.item)}>
                    {deleteIsHovered ? "x" : "-"}
                </button>
            )
        }else{
            return <></>;
        }
    }

    return (
        <div className="BasketItem" onMouseOver={e => setItemHover(true)} onMouseLeave={e => setItemHover(false)}>
            <div className="item-name">{props.item.name}</div>
            <input className="chip item-count-input" value={props.count} onChange={e => props.setCount(Number(e.target.value))}/>
            {getIfHovered()}
        </div>
    )
}
export default BasketItem;