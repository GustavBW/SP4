import React from 'react';
import './Basket.css';

import BasketItem from './basketItem/BasketItem';
import { IBasketItem } from '../../ts/webshop';

export let BASKET: IBasketItem[] = [];

const Basket = (props: any): JSX.Element => {
    const [ basketItems , setBasketItems] = React.useState(BASKET);

    React.useEffect(() => {
        setBasketItems(basketItems);
    }, [BASKET]);

    return (
        <div className="Basket">
            <h1>Basket</h1>
            <div className="basket-header">
                <div>Part Name</div>
                <div>Count</div>
            </div>
            <div className="items">
                {basketItems.map((item: IBasketItem, index: number) => {
                    return (
                        <BasketItem item={item} key={index} />
                    )
                })}
            </div>
            <div className="horizontal-buttons">
                <a className="chip back" href="/">Clear</a>
                <button className="chip checkout">Checkout</button>
            </div>
        </div>
    )
}
export default Basket;