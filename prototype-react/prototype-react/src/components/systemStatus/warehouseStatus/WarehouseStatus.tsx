import React from 'react';
import './WarehouseStatus.css';
import warehouseImage from '../../../images/warehouse.png';
import { getStateOf, KnownSystemComponents, IUnknownState } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';

export interface IWarehouseStatus {
    timestamp: Date;
    process: string;
    capacity: number;
}

const WarehouseStatus = (props: any): JSX.Element => {

    const [warehouseStatus, setWarehouseStatus] = React.useState<IWarehouseStatus>({ timestamp: new Date(), process: "unknown", capacity: -1 });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    React.useEffect(() => {
        getStateOf(KnownSystemComponents.Warehouse)
            .catch(error => console.log(error))
            .then((status: IUnknownState | void) => {
                if (status) {
                    setWarehouseStatus({ timestamp: status.timestamp, process: status.process, capacity: status.capacity })
                    setConnectionStatus(true);
                } else {
                    setWarehouseStatus({ timestamp: warehouseStatus.timestamp, process: warehouseStatus.process, capacity: warehouseStatus.capacity })
                    setConnectionStatus(false);
                }
            });
    }, []);

    return (
        <div className="WarehouseStatus">
            <div className="vertical-flex">
                <img src={warehouseImage} alt="warehouse" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}></img>
                <h1>Warehouse</h1>
            </div>
            <div className="stats">
                <h2>Last seen: </h2>
                <h2>{warehouseStatus.timestamp.toUTCString()}</h2>
                <h2>Last known process: </h2>
                <h2>{warehouseStatus.process}</h2>
                <h2>Capacity: </h2>
                <h2>{warehouseStatus.capacity}</h2>
            </div>

        </div>
    )
}
export default WarehouseStatus;