import React from 'react';
import './Checkout.css';
import { IBasketItem } from '../../../ts/webshop';
import { Order } from '../../../ts/webshop';
import { placeNewOrder } from '../../../ts/api';

export enum CheckoutFlowStatus {
    FILLING_IN_INFO = 0, ON_AWAITING_SERVER_RESPONSE = 1, ON_SUCCESSFUL_RESPONSE = 2, ON_FAILED_RESPONSE = 3
}

const Checkout = (props: {items: IBasketItem[], deselect: (state: boolean) => void}): JSX.Element => {

    //Progress denotates how far in the "checkout" progress the cmr is.
    const [progress, setProgress] = React.useState<number>(CheckoutFlowStatus.FILLING_IN_INFO);
    const [submitError, setSubmitError] = React.useState<string>("");

    const handleOrderSubmit = (event: React.FormEvent<HTMLFormElement>): void => {
        const partCountMap = new Map<string, number>();
        props.items.forEach((item: IBasketItem) => {
            if (partCountMap.has(item.name)) {
                partCountMap.set(item.name, partCountMap.get(item.name)! + item.count);
            } else {
                partCountMap.set(item.name, item.count);
            }
        });

        const order: Order = {
            id: -1,
            cmr: (event.target as any)[0].value,
            parts: partCountMap,
            completionPercentage: 0
        }
        event.preventDefault();

        placeNewOrder(order)
            .catch((error) => {
                setProgress(CheckoutFlowStatus.ON_FAILED_RESPONSE);
                setSubmitError(error.toString());
            })
            .then((response) => {
                if(response){
                    setProgress(CheckoutFlowStatus.ON_SUCCESSFUL_RESPONSE);
                }
            });
    }

    const getProgressView = (): JSX.Element => {
        switch (progress) {
            case 0:
                return (
                    <form onSubmit={handleOrderSubmit} className="checkout-form">
                        <label htmlFor="cmr">Full Name</label>
                        <input className="chip higher-input" type="text" id="cmrName" placeholder="Full name" />
                        <label htmlFor="cmr">Address</label>
                        <input className="chip higher-input" type="text" id="cmrAddress" placeholder="Address" />
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
                        <p>Order placed succesfully!</p>
                        <button className="chip checkout-form" onClick={e => props.deselect(false)}>X</button>
                    </div>
                    )
            case 3:
                return (
                    <div className="checkout-form">
                        <p>Order failed: {submitError}</p>
                        <button className="chip" onClick={e => props.deselect(false)}>x</button>
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