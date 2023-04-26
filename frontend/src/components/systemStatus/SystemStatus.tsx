import React from 'react';
import './SystemStatus.css';
import WarehouseStatus from './warehouseStatus/WarehouseStatus';
import AGVStatus from './agvStatus/AGVStatus';
import AssemblerStatus from './assemblerStatus/AssemblerStatus';
import SetEndpoint from './setEndpoint/SetEndpoint';

const SystemStatus = (props: any): JSX.Element => {
    return (
        <div className="SystemStatus">
            <h1>System Status</h1>
            <WarehouseStatus />
            <AGVStatus />
            <AssemblerStatus />
        </div>
    )
}
export default SystemStatus;