import React from 'react';
import './Basket.css';

import BasketItem from './basketItem/BasketItem';
import { Part } from '../../ts/webshop';
import { DISPLAYS } from '../../App';
import Checkout from './checkout/Checkout';

export let BASKET: Map<Part,number> = new Map();

const Basket = (props: {return: (display: string) => void}): JSX.Element => {
    const [basketItems, setBasketItems] = React.useState(BASKET);
    const [inCheckout, setInCheckout] = React.useState(false);

    React.useEffect(() => {
        setBasketItems(new Map(BASKET));
    }, [BASKET]);

    const handleBasketClear = (): void => {
        BASKET.clear();
        setBasketItems(BASKET);
    }
    //TODO: Add remove element

    const getIfInCheckout = (): JSX.Element => {
        if(inCheckout){
            return (
                <Checkout items={ basketItems } deselect={setInCheckout}/>
            )
        }else{
            return <></>;
        }
    }

    const handleItemCountChange = (value: number, item: Part): void => {
        BASKET.set(item,value);
        setBasketItems(new Map(BASKET));
    }
    const handleItemClear = (item: Part): void => {
        BASKET.delete(item);
        setBasketItems(new Map(BASKET));
    }

    return (
        <div className="Basket">
            <h1>Basket</h1>
            <div className="basket-header">
                <div>Part Name</div>
                <div>Count</div>
            </div>
            <div className="items">
                {[...basketItems.keys()].map((item: Part, index: number) => {
                    return (
                        <BasketItem item={item} count={basketItems.get(item) || 1} key={index} setCount={value => {
                            handleItemCountChange(value,item);
                        }} removeItem={e => handleItemClear(e)}/>
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