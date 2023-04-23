import React from 'react';
import './Basket.css';
import { Part, IBasketItem } from '../../ts/webshop';
import { DISPLAYS } from '../../App';
import Checkout from './checkout/Checkout';
import BasketItem from './basketItem/BasketItem';

interface BasketProps{
    basketContent: IBasketItem[];
    setBasketContent: (basketContent: IBasketItem[]) => void;
    setDisplay: (display: string) => void;
}

const Basket = ({ basketContent, setDisplay, setBasketContent }: BasketProps): JSX.Element => {
    const [inCheckout, setInCheckout] = React.useState(false);

    const handleBasketClear = (): void => {
        setBasketContent([]);
    }

    const getIfInCheckout = (): JSX.Element => {
        if(inCheckout){
            return (
                <Checkout basket={ basketContent } deselect={setInCheckout}/>
            )
        }else{
            return <></>;
        }
    }

    const handleItemCountChange = (value: number, itemId: number): void => {
        basketContent.map((basketItem) => {
            if(basketItem.part.id === itemId){
                basketItem.count = value;
            }
        })
        setBasketContent([...basketContent]);
    }
    const handleItemClear = (item: Part): void => {
        basketContent.filter((basketItem) => {
            return basketItem.part.id !== item.id;
        })
        setBasketContent([...basketContent]);
    }

    return (
        <div className="Basket">
            <h1>Basket</h1>
            <div className="basket-header">
                <div>Part Name</div>
                <div>Count</div>
            </div>
            <div className="items">
                {basketContent.map((item: IBasketItem, index: number) => {
                    return (
                        <BasketItem batchPart={item} key={index} setCount={value => {
                            handleItemCountChange(value,item.part.id);
                        }} removeItem={e => handleItemClear(e)}/>
                    )
                })}
            </div>
            <div className="horizontal-buttons">
                <button className="chip back" onClick={e => setDisplay(DISPLAYS.products)}>Return</button>
                <button className="chip clear" onClick={e => handleBasketClear()}>Clear</button>
                <button className="chip checkout" onClick={e => setInCheckout(true)}>Queue Batch</button>
            </div>
            {getIfInCheckout()}
        </div>
    )
}
export default Basket;