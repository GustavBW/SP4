import React from 'react';
import './Basket.css';

import BasketItem from './basketItem/BasketItem';
import { IBasketItem } from '../../ts/webshop';
import { DISPLAYS } from '../../App';
import { Order } from '../../ts/webshop';
import Checkout from './checkout/Checkout';

export let BASKET: IBasketItem[] = [];

const Basket = (props: {return: (display: string) => void}): JSX.Element => {
    const [ basketItems , setBasketItems] = React.useState(BASKET);
    const [inCheckout, setInCheckout] = React.useState(false);

    React.useEffect(() => {
        setBasketItems(basketItems);
    }, [BASKET]);

    const handleBasketClear = (): void => {
        BASKET = [];
        setBasketItems(BASKET);
    }

    const getIfInCheckout = (): JSX.Element => {
        if(inCheckout){
            return (
                <Checkout items={ basketItems } deselect={setInCheckout}/>
            )
        }else{
            return <></>;
        }
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
                <button className="chip checkout" onClick={e => setInCheckout(true)}>Checkout</button>
            </div>
            {getIfInCheckout()}
        </div>
    )
}
export default Basket;