import React from 'react';
import './Basket.css';

import BasketItem from './basketItem/BasketItem';
import { IBasketItem } from './basketItem/BasketItem';

export let BASKET: IBasketItem[] = [
    { id: 1, name: 'Item 1', count: 1 },
    { id: 2, name: 'Item 2', count: 2 },
    { id: 3, name: 'Item 3', count: 3 },
    { id: 4, name: 'Item 4', count: 4 },
    { id: 5, name: 'Item 5', count: 5 },
    { id: 6, name: 'Item 6', count: 6 },
];

const Basket = (props: any): JSX.Element => {
    const [ basketItems , setBasketItems] = React.useState(BASKET);

    React.useEffect(() => {
        setBasketItems(basketItems);
    }, [BASKET]);

    return (
        <div className="Basket">
            <h1>Basket</h1>
            <div className="items">
                <div>id</div>
                <div>name</div>
                <div>count</div>
                {basketItems.map((item: IBasketItem) => {
                    return (
                        <BasketItem item={item} />
                    )
                })}
            </div>
            <div className="horizontal-buttons">
                <button className="chip back">Return</button>
                <button className="chip checkout">Checkout</button>
            </div>
        </div>
    )
}
export default Basket;