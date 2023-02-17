import React from 'react';
import './AGVStatus.css';
import agvImage from '../../../images/agv.png';
import { KnownSystemComponents } from '../../../ts/api';
import { getStateOf } from '../../../ts/api';
import { IUnknownState } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';

export interface IAGVStatus {
    lastSeen: Date;
    lastKnownProcess: string;
    battery: number;
}

const AGVStatus = (props: any): JSX.Element => {

    const [agvStatus, setAGVStatus] = React.useState<IAGVStatus>({lastSeen: new Date(), lastKnownProcess: "unknown", battery: -1});
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    React.useEffect(() => {
        getStateOf(KnownSystemComponents.AGV)
            .catch(error => console.log(error))
            .then((status: IUnknownState | void) => {
                if(status){
                    setAGVStatus({lastSeen: status.timestamp, lastKnownProcess: status.process, battery: status.battery})
                    setConnectionStatus(true);
                }else{
                    setAGVStatus({lastSeen: agvStatus.lastSeen, lastKnownProcess: agvStatus.lastKnownProcess, battery: agvStatus.battery})
                    setConnectionStatus(false);
                }
            });
    }, []);

    return (
        <div className="AGVStatus" style={{display: "flex", flexDirection: "row"}}>
            <div className="vertical-flex">
                <img src={agvImage} alt="agv" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}></img>
                <h1>AGV</h1>
            </div>
            <div className="stats">
                <h2>Last seen: </h2>
                <h2>{agvStatus.lastSeen.toUTCString()}</h2>
                <h2>Last known process: </h2>
                <h2>{agvStatus.lastKnownProcess}</h2>
                <h2>Battery: </h2>
                <h2>{agvStatus.battery}</h2>
            </div>
        </div>
    )
}
export default AGVStatus;