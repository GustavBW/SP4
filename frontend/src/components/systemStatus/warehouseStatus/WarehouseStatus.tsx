import React from 'react';
import '../SubSystemStatus.css';
//@ts-ignore
import warehouseImage from '../../../images/warehouse.png';
import { getWHStatus } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { WHStatus } from '../../../ts/types';

const WarehouseStatus = (props: any): JSX.Element => {

    const [warehouseStatus, setWarehouseStatus] = React.useState<WHStatus>({ 
        currentProcess: "unknown",
        message: "none",
        code: -1
     });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);
    const [lastSeen, setLastSeen] = React.useState<number>(new Date().getMilliseconds());

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
            </div>

        </div>
    )
}
export default WarehouseStatus;