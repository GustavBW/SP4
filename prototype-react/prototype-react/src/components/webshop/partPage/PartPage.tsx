import React from 'react';
import './PartPage.css';

import { Part } from '../../../ts/webshop';
import { BASKET } from '../../basket/Basket';
import { IBasketItem } from '../../../ts/webshop';

export const PartPage = (props: {part: Part, deselect: () => void}): JSX.Element => {

    const formRef = React.useRef<HTMLFormElement>(null);

    const handlePartToBasket = (e: any): void => {
        e.preventDefault();
        if(formRef.current){
            const quantity = parseInt((formRef.current.elements.namedItem("quantity") as HTMLInputElement).value) | 1;
            BASKET.push({ id: 1, name: props.part.name, count: quantity } as IBasketItem);
        }
    }

    return (
        <div className="PartPage">
            <h1 className="title">{props.part.name}</h1>
            <img src={props.part.image} alt={props.part.name} className="part-image2"/>
            <p className="description">{props.part.description}</p>

            <h2 className="in-stock2">Stock: {props.part.inStock}</h2>

            <form className="add-to-cart-form" ref={formRef}>
                <button className="close-button chip" onClick={e => props.deselect()}>X</button>
                <label htmlFor="quantity">Quantity:</label>
                <input type="text" inputMode="numeric" pattern="\d*" id="quantity" name="quantity" min="1" placeholder="1" className="chip"/>
                <input className="chip" type="submit" value="Add to cart" onClick={e => handlePartToBasket(e)} />
            </form>
        </div>
    );
}
export default PartPage;