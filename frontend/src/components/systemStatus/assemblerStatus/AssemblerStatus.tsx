import React from 'react';
import './AssemblerStatus.css';
import assemblerImage from '../../../images/assembler.png';
import { getStateOf, KnownSystemComponents, IUnknownState } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { useInterval } from 'react-interval-hook';

export interface IAssemblerStatus {
    lastKnownProcess: number;
    message: string;
    state: string;
    timestamp: string;
}

const AssemblerStatus = (props: any): JSX.Element => {

    const [assemblerStatus, setAssemblerStatus] = React.useState<IAssemblerStatus>({ timestamp: "never", lastKnownProcess: 9999, message: "", state: "unknown"  });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    const {start, stop, isActive} = useInterval(() => {
        getStateOf(KnownSystemComponents.Assembler)
            .catch(error => console.log(error))
            .then((status: any | void) => {
                if (status) {
                    setAssemblerStatus({ timestamp: status.timestamp, lastKnownProcess: status.currentProcess, message: status.message, state: status.state })
                    setConnectionStatus(true);
                } else {
                    setConnectionStatus(false);
                }
            });
    }, 1000, {autoStart: true});


    return (
        <div className="AssemblerStatus">
            <div className="vertical-flex">
                <img src={assemblerImage} alt="assembler" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}
                    title={ !connectionStatus ? "Data may be out of date due to connection issues" : "" }
                />
                <h1>Assembler</h1>
            </div>
            <div className="stats">
                <h2>Last seen: </h2>
                <h2>{assemblerStatus.timestamp}</h2>
                <h2>Last known process: </h2>
                <h2>{assemblerStatus.lastKnownProcess}</h2>
                <h2>Message: </h2>
                <h2>{assemblerStatus.message}</h2>
            </div>
        </div>
    )
}
export default AssemblerStatus;