import React from 'react';
import './PartPage.css';
import Confirmation from '../confirmation/Confirmation';
import { IBasketItem, Part } from '../../../ts/webshop';

interface PartPageProps{
    addToBasket: (item: IBasketItem) => void;
    part: Part;
    deselect: () => void;
}

export const PartPage = ({addToBasket, part, deselect}: PartPageProps): JSX.Element => {

    const formRef = React.useRef<HTMLFormElement>(null);
    const [isConfirmingAddition, setConfirmingAddition] = React.useState(false);

    const handlePartToBasket = (e: any): void => {
        e.preventDefault();
        if(formRef.current){
            const quantity = parseInt((formRef.current.elements.namedItem("quantity") as HTMLInputElement).value) | 1;
            
            addToBasket({part: part, count: quantity});
        }
        setConfirmingAddition(true);
    }

    const getBody = (): JSX.Element => {
        if(isConfirmingAddition){
            return (
                <Confirmation message={"Added " + part.name + " to the batch"} deselect={deselect}/>
            )
        }else{
            return(
                <div className="PartPage">
                    <h1 className="product-title">{part.name}</h1>
                    <p className="description">{part.description}</p>

                    <form className="add-to-cart-form" ref={formRef}>
                        <button className="close-button chip" onClick={e => deselect()}>X</button>
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