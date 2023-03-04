import React from 'react';
import './SetEndpoint.css';
import { setEndpoint } from '../../../ts/api';
import { containsAnyOf } from '../../../ts/stringUtil';
import { ALL_LETTERS } from '../../../ts/stringUtil';

const SetEndpoint = (props: any): JSX.Element => {

    const ipRef = React.useRef<HTMLInputElement>(null);
    const portRef = React.useRef<HTMLInputElement>(null);

    const handleOnSet = () => {
        if(ipRef.current && portRef.current){
            setEndpoint(ipRef.current.value, Number(portRef.current.value));
        }
    }

    const onChangeIp = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value: string = e.target.value;
        const node = ipRef.current;
        if(!node) return;
        
        node.classList.add("invalid-data");

        if(value.length < 12 || !value.includes(".") || containsAnyOf(value,ALL_LETTERS) || value.length > 14){
            node.classList.add("invalid-data");
            node.classList.remove("valid-data");
        }else{
            node.classList.add("valid-data");
            node.classList.remove("invalid-data");
        }
    }

    const onChangePort = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value: string = e.target.value;
        const valueAsNumber: number = Number(value);
        const node = portRef.current;
        if(!node) return;
        
        node.classList.add("invalid-data");

        if(value.length > 5 || value.length > 0 || value.includes(".") || value.includes(",") || Number.isNaN(valueAsNumber) || valueAsNumber === undefined ||valueAsNumber < 0 || valueAsNumber > 65535){
            node.classList.add("invalid-data");
            node.classList.remove("valid-data");
        }else{
            node.classList.add("valid-data");
            node.classList.remove("invalid-data");
        }
    }

    const removeMarkings = (e: React.FocusEvent<HTMLInputElement>) => {
        const node = e.target;
        node.classList.remove("invalid-data");
        node.classList.remove("valid-data");
    }

    return (
        <div className="SetEndpoint">
            <h1>Set Endpoint</h1>
            <div className="horizontal-buttons">
                <label htmlFor="endpoint">IP</label>
                <input className="chip" type="text" id="endpoint" placeholder="IP" ref={ipRef} onChange={e => onChangeIp(e)} onFocus={e => onChangeIp(e)} onBlur={e => removeMarkings(e)}/>
                <label htmlFor="port">Port</label>
                <input className="chip" type="text" id="port" placeholder="Port" ref={portRef} onChange={e => onChangePort(e)} onFocus={e => onChangePort(e)}  onBlur={e => removeMarkings(e)} />
            </div>
            <button className="chip" onClick={handleOnSet}>Confirm</button>
        </div>
    )
}
export default SetEndpoint;