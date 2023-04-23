import React from 'react';
import './AssemblerStatus.css';
//@ts-ignore
import assemblerImage from '../../../images/assembler.png';
import { getAssmStatus } from '../../../ts/api';
import { classNames } from '../../../ts/classUtil';
import { AssmStatus } from '../../../ts/types';

const AssemblerStatus = (): JSX.Element => {

    const [assemblerStatus, setAssemblerStatus] = React.useState<AssmStatus>({ timestamp: "never", currentProcess: 9999, message: "", state: "unknown"  });
    const [connectionStatus, setConnectionStatus] = React.useState<boolean>(false);

    React.useEffect(() => {
        const timer = setInterval(() => {
            getAssmStatus()
                .catch(error => console.log(error))
                .then((status: AssmStatus | void) => {
                    if(status){
                        setAssemblerStatus(status)
                        setConnectionStatus(true);
                    }else{
                        setConnectionStatus(false);
                    }
                });
        }, 1000)
        return () => clearInterval(timer);
    }, []);


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
                <h2>{assemblerStatus.currentProcess}</h2>
                <h2>State: </h2>
                <h2>{assemblerStatus.state}</h2>
            </div>
        </div>
    )
}
export default AssemblerStatus;