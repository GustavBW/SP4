import React from 'react';
import './AssemblerStatus.css';
import assemblerImage from '../../../images/assembler.png';
import { getStateOf, KnownSystemComponents, IUnknownState } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';


export interface IAssemblerStatus {
    lastSeen: Date;
    lastKnownProcess: string;
    battery: number;
}

const AssemblerStatus = (props: any): JSX.Element => {

    const [assemblerStatus, setAssemblerStatus] = React.useState<IAssemblerStatus>({ lastSeen: new Date(), lastKnownProcess: "unknown", battery: -1 });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    React.useEffect(() => {
        getStateOf(KnownSystemComponents.Assembler)
            .catch(error => console.log(error))
            .then((status: IUnknownState | void) => {
                if (status) {
                    setAssemblerStatus({ lastSeen: status.timestamp, lastKnownProcess: status.process, battery: status.battery })
                    setConnectionStatus(true);
                } else {
                    setAssemblerStatus({ lastSeen: assemblerStatus.lastSeen, lastKnownProcess: assemblerStatus.lastKnownProcess, battery: assemblerStatus.battery })
                    setConnectionStatus(false);
                }
            });
    }, []);


    return (
        <div className="AssemblerStatus">
            <div className="vertical-flex">
                <img src={assemblerImage} alt="assembler" className={classNames('system-identifier invalid-data', connectionStatus && 'valid-data')}></img>
                <h1>Assembler</h1>
            </div>
            <div className="stats">
                <h2>Last seen: </h2>
                <h2>{assemblerStatus.lastSeen.toUTCString()}</h2>
                <h2>Last known process: </h2>
                <h2>{assemblerStatus.lastKnownProcess}</h2>
                <h2>Battery: </h2>
                <h2>{assemblerStatus.battery}</h2>
            </div>
        </div>
    )
}
export default AssemblerStatus;