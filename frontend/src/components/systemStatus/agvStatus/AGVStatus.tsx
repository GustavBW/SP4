import React from 'react';
import './AGVStatus.css';
//@ts-ignore
import agvImage from '../../../images/agv.png';
import { getAGVStatus } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { AGVStatus } from '../../../ts/types';


const AGVStatus = (): JSX.Element => {
    const [agvStatus, setAGVStatus] = React.useState<AGVStatus>({
        battery: -1, 
        programName: "unknown",
        state: "unknown",
        timestamp: "-1",
        code: 500
     });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    React.useEffect(() => {
        const timer = setInterval (() => {
            getAGVStatus()
                .catch(error => console.log(error))
                .then((status: AGVStatus | void) => {
                    if(status){
                        setAGVStatus(status)
                        setConnectionStatus(true);
                    }else{
                        setConnectionStatus(false);
                    }
                });
        }, 1000);
        return () => clearInterval(timer);
    }, []);

    return (
        <div className="AGVStatus" style={{display: "flex", flexDirection: "row"}}>
            <div className="vertical-flex">
                <img src={agvImage} alt="agv" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}
                    title={ !connectionStatus ? "Data may be out of date due to connection issues" : "" }
                />
                <h1>AGV</h1>
            </div>
            <div className="stats">
                <h2>Last seen: </h2>
                <h2>{agvStatus.timestamp}</h2>
                <h2>Last known process: </h2>
                <h2>{agvStatus.programName}</h2>
                <h2>Battery: </h2>
                <h2>{agvStatus.battery}</h2>
            </div>
        </div>
    )
}
export default AGVStatus;