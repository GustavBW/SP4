import React from 'react';
import './BasketItem.css';

import { IBasketItem } from '../../../ts/webshop';

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