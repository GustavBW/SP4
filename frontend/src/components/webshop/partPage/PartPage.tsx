import React from 'react';
import './PartPage.css';
import Confirmation from '../confirmation/Confirmation';

import { Part } from '../../../ts/webshop';
import { BASKET } from '../../basket/Basket';

export const PartPage = (props: {part: Part, deselect: () => void}): JSX.Element => {

    const formRef = React.useRef<HTMLFormElement>(null);
    const [isConfirmingAddition, setConfirmingAddition] = React.useState(false);

    const handlePartToBasket = (e: any): void => {
        e.preventDefault();
        if(formRef.current){
            const quantity = parseInt((formRef.current.elements.namedItem("quantity") as HTMLInputElement).value) | 1;
            BASKET.set(props.part, quantity + (BASKET.get(props.part) || 0));
        }
        setConfirmingAddition(true);
    }

    const getBody = (): JSX.Element => {
        if(isConfirmingAddition){
            return (
                <Confirmation message={"Added " + props.part.name + " to the batch"} close={props.deselect}/>
            )
        }else{
            return(
                <div className="PartPage">
                    <h1 className="product-title">{props.part.name}</h1>
                    <img src={props.part.image} alt={props.part.name} className="part-image2"/>
                    <p className="description">{props.part.description}</p>

                    <form className="add-to-cart-form" ref={formRef}>
                        <button className="close-button chip" onClick={e => props.deselect()}>X</button>
                        <label htmlFor="quantity">Quantity:</label>
                        <input type="text" inputMode="numeric" pattern="\d*" id="quantity" name="quantity" min="1" placeholder="1" className="chip"/>
                        <input className="chip" type="submit" value="Add to Batch" onClick={e => handlePartToBasket(e)} />
                    </form>
                </div>
            );
        }
    }

    return (
        <>            
            {getBody()}
        </>
    );
}
export default PartPage;