import React from 'react';
import './InTransitDisplay.css';

import { getQueuedBatches } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import ProductThumbnail from '../productThumbnail/ProductThumbnail';
import { Batch } from '../../../ts/webshop';

const InTransitDisplay = (props: any): JSX.Element => {
    const [activeChains, setActiveChains] = React.useState<Batch[]>([]);
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    React.useEffect(() =>{
        const timer = setInterval(() => {
            getQueuedBatches()
            .catch(error => console.log(error))
            .then((partlist: Batch[] | void) => {
                if (partlist) {
                    setActiveChains(partlist)
                    setConnectionStatus(true);
                } else {
                    setConnectionStatus(false);
                }
            });
        }, 1000)
        return () => clearInterval(timer);
    }, []);
        

    return (
        <div className="InTransitDisplay">
            <h2 className={classNames("invalid-data", connectionStatus && "valid-data")} title={ !connectionStatus ? "Below results may be out of date due to connection issues" : "" }>Currently Processing</h2>
            <div className="in-transit-items">
                {activeChains.map((batch: Batch, index: number) => {
                    return <ProductThumbnail key={index} batch={batch} />
                    })
                }
            </div>
        </div>
    )
}
export default InTransitDisplay;