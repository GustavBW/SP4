import React from 'react';
import './InTransitDisplay.css';

import { getQueuedBatches } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { ProcessChain } from '../../../ts/webshop';
import ProductThumbnail from '../productThumbnail/ProductThumbnail';
import { useInterval } from 'react-interval-hook';

const InTransitDisplay = (props: any): JSX.Element => {

    const [activeChains, setActiveChains] = React.useState<ProcessChain[]>([]);
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    const {start, stop, isActive} = useInterval(() => {
        getQueuedBatches()
            .catch(error => console.log(error))
            .then((chainlist: ProcessChain[] | void) => {
                if (chainlist) {
                    setActiveChains(chainlist)
                    setConnectionStatus(true);
                } else {
                    setConnectionStatus(false);
                }
            });
    }, 1000, {autoStart: true});

    return (
        <div className="InTransitDisplay">
            <h2 className={classNames("invalid-data", connectionStatus && "valid-data")} title={ !connectionStatus ? "Below results may be out of date due to connection issues" : "" }>Currently Processing</h2>
            <div className="in-transit-items">
                {activeChains.map((chain: ProcessChain, index: number) => {
                    return <ProductThumbnail key={index} chain={chain} />
                    })
                }
            </div>
        </div>
    )
}
export default InTransitDisplay;