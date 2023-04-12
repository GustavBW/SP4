import React from 'react';
import './WarehouseStatus.css';
import warehouseImage from '../../../images/warehouse.png';
import { getStateOf, KnownSystemComponents, IUnknownState } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { useInterval } from 'react-interval-hook';

export interface IWarehouseStatus {
    timestamp: Date;
    process: string;
    capacity: number;
}

const WarehouseStatus = (props: any): JSX.Element => {

    const [warehouseStatus, setWarehouseStatus] = React.useState<IWarehouseStatus>({ timestamp: new Date(), process: "unknown", capacity: -1 });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    const {start, stop, isActive} = useInterval(() => {
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
    }, 1000, {autoStart: true});

    return (
        <div className="WarehouseStatus">
            <div className="vertical-flex">
                <img src={warehouseImage} alt="warehouse" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}
                    title={ !connectionStatus ? "Data may be out of date due to connection issues" : "" }
                />
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