import React from 'react';
import '../SubSystemStatus.css';
//@ts-ignore
import warehouseImage from '../../../images/warehouse.png';
import { getWHStatus } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { WHStatus } from '../../../ts/types';
import WHInventoryView from '../whInventoryView/WHInventoryView';

const WarehouseStatus = (props: any): JSX.Element => {

    const [warehouseStatus, setWarehouseStatus] = React.useState<WHStatus>({ 
        currentProcess: "unknown",
        message: "none",
        code: -1
     });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);
    const [lastSeen, setLastSeen] = React.useState<number>(new Date().getMilliseconds());
    const [showInventory, setShowInventory] = React.useState<boolean>(false);

    React.useEffect(() => {
        const timer = setInterval(() => {
            getWHStatus()
                .catch(error => console.log(error))
                .then((status: WHStatus | void) => {
                    if (status) {
                        setWarehouseStatus(status)
                        setConnectionStatus(true);
                        setLastSeen(new Date().getMilliseconds())
                    } else {
                        setConnectionStatus(false);
                    }
                });
        }, 1000);
        return () => clearInterval(timer);
    })

    const appendInventory = () => {
        if(showInventory) {
            return (
                <WHInventoryView onDeselect={() => setShowInventory(false)} />
            )
        }
    }

    return (
        <div className="SubSystemStatus">
            <div className="vertical-flex">
                <img src={warehouseImage} alt="warehouse" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}
                    title={ !connectionStatus ? "Data may be out of date due to connection issues" : "" }
                />
                <h1>Warehouse</h1>
            </div>
            <div className="wh-stats">
                <div className="row">
                    <h2>Last seen: </h2>
                    <h2>{new Date(lastSeen).toLocaleDateString()}</h2>
                </div>
                <div className="row">
                    <h2>Last known process: </h2>
                    <h2>{warehouseStatus.currentProcess}</h2>
                </div>
                <div className="row">
                    <h2>Code: </h2>
                    <h2>{warehouseStatus.code}</h2>
                </div>
                <div className="row">
                    <></>
                    <button className="chip"
                        onClick={() => setShowInventory(!showInventory)}
                    >View Inventory</button>
                </div>
            </div>
            {appendInventory()}
        </div>
    )
}
export default WarehouseStatus;