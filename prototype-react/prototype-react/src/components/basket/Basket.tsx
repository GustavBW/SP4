import React from 'react';
import './Basket.css';

import BasketItem from './basketItem/BasketItem';
import { IBasketItem } from '../../ts/webshop';
import { DISPLAYS } from '../../App';
import { Order } from '../../ts/webshop';

export let BASKET: IBasketItem[] = [];

const Basket = (props: {return: (display: string) => void}): JSX.Element => {
    const [ basketItems , setBasketItems] = React.useState(BASKET);

    React.useEffect(() => {
        setBasketItems(basketItems);
    }, [BASKET]);

    const handleBasketClear = (): void => {
        BASKET = [];
        setBasketItems(BASKET);
    }

    const packOrder = (): void => {
        const partCountMap = new Map<string, number>();
        basketItems.forEach((item: IBasketItem) => {
            if (partCountMap.has(item.name)) {
                partCountMap.set(item.name, partCountMap.get(item.name)! + item.count);
            } else {
                partCountMap.set(item.name, item.count);
            }
        });
       
    }

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
                <button className="chip back" onClick={e => props.return(DISPLAYS.products)}>Return</button>
                <button className="chip clear" onClick={e => handleBasketClear()}>Clear</button>
                <button className="chip checkout">Checkout</button>
            </div>
        </div>
    )
}
export default Basket;