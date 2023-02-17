import React from 'react';
import './BasketItem.css';

export interface IBasketItem {
    id: number;
    name: string;
    count: number;
}

const BasketItem = (props: {item: IBasketItem}): JSX.Element => {
    return (
        <div className="BasketItem">
            <div>{props.item.id}</div>
            <div>{props.item.name}</div>
            <div>{props.item.count}</div>
        </div>
    )
}
export default BasketItem;