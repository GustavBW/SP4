import React from 'react';
import './Checkout.css';
import { Part } from '../../../ts/webshop';
import { Batch } from '../../../ts/webshop';
import { placeNewOrder } from '../../../ts/api';

export enum CheckoutFlowStatus {
    FILLING_IN_INFO = 0, ON_AWAITING_SERVER_RESPONSE = 1, ON_SUCCESSFUL_RESPONSE = 2, ON_FAILED_RESPONSE = 3
}

const Checkout = (props: {items: Map<Part,number>, deselect: (state: boolean) => void}): JSX.Element => {

    //Progress denotates how far in the "checkout" progress the cmr is.
    const [progress, setProgress] = React.useState<number>(CheckoutFlowStatus.FILLING_IN_INFO);
    const [submitError, setSubmitError] = React.useState<string>("");

    const handleOrderSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
        event.preventDefault();
        const idCountMap: Map<number, number> = new Map();
        props.items.forEach((value: number, key: Part) => {
            idCountMap.set(key.id, value);
        });

        const order: Batch = {
            id: -1,
            cmr: (event.target as any)[0].value,
            parts: idCountMap,
            completionPercentage: 0
        }
        
        placeNewOrder(order)
            .catch((error) => {
                setProgress(CheckoutFlowStatus.ON_FAILED_RESPONSE);
                setSubmitError(error.toString());
            })
            .then((response) => {
                if(response && response.ok){
                    setProgress(CheckoutFlowStatus.ON_SUCCESSFUL_RESPONSE);
                }else{
                    setProgress(CheckoutFlowStatus.ON_FAILED_RESPONSE);
                    if(response){
                        setSubmitError("Server responded with error code: " + response.status);
                    }
                }
            });
    }

    const getProgressView = (): JSX.Element => {
        switch (progress) {
            case 0:
                return (
                    <form onSubmit={handleOrderSubmit} className="checkout-form">
                        <label htmlFor="cmr">Employee ID</label>
                        <input className="chip higher-input" type="text" id="cmrName" placeholder="Employee ID" />
                        <label htmlFor="cmr">Superviser ID</label>
                        <input className="chip higher-input" type="text" id="cmrAddress" placeholder="Superviser ID" />
                        <div className="horizontal-buttons">
                            <button className="chip" onClick={e => props.deselect(false)}>Cancel</button>
                            <button className="chip" type="submit">Submit</button>
                        </div>
                    </form>
                )
            case 1:
                return <p>Waiting for server response...</p>
            case 2:
                return (
                    <div className="checkout-form">
                        <p>Batch placed succesfully in queue!</p>
                        <button className="chip checkout-form" onClick={e => props.deselect(false)}>X</button>
                    </div>
                    )
            case 3:
                return (
                    <div className="checkout-form">
                        <p>Batch placement failed: {submitError}</p>
                        <button className="chip" onClick={e => props.deselect(false)}>X</button>
                    </div>
                )
            default:
                return <></>
        }
    }

    return (
        <div className="Checkout">
            <h1>Checkout</h1>
            {getProgressView()}
        </div>
    )
}
export default Checkout;