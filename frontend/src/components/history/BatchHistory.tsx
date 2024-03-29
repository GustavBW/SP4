import React from 'react';
import "./BatchHistory.css";
import { Batch } from '../../ts/webshop';
import { getCompletedBatches, getQueuedBatches } from '../../ts/api';

interface BatchHistoryProps {
    setSelectedBatch: (batch: Batch) => void;
}

export default function BatchHistory({setSelectedBatch}: BatchHistoryProps){
    const [activeBatches, setActiveBatches] = React.useState<Batch[]>([]);
    const [inactiveBatches, setInactiveBatches] = React.useState<Batch[]>([]);
    const [reload, setReload] = React.useState<number>(0);
    const [reloading, setReloading] = React.useState<boolean>(false);

    React.useEffect(() => {
        const load = async () => {
            const qdPromise = getQueuedBatches()
                .then((batches) => {
                    setActiveBatches(batches);
                });
            const compPromize = getCompletedBatches()
                .then((batches) => {
                    setInactiveBatches(batches);
                });
            await Promise.all([qdPromise, compPromize]);
            setReloading(false);
        }
        load();
    }, [reload]);

    return (
        <div className="BatchHistory">
            <div className="history-header">
                <h1>Active</h1>
                <h1>Inactive</h1>
            </div>
            <div className="history-body">
                <div className="history-batch-list">
                    {activeBatches.map((batch) => {
                        return (
                            <div key={batch.id} className="history-batch" onClick={() => setSelectedBatch(batch)}>
                                <h3>{batch.id}</h3>
                                <p>{batch.employeeId}</p>
                            </div>
                        )
                    })}
                </div>
                <div className="history-batch-list">
                    {inactiveBatches.map((batch) => {
                        return (
                            <div key={batch.id} className="history-batch" onClick={() => setSelectedBatch(batch)}>
                                <h3>{batch.id}</h3>
                                <p>{batch.employeeId}</p>
                            </div>
                        )
                    })}
                </div>
            </div>
            <button disabled={reloading} className="reload-button" 
                onClick={() => {
                    setReloading(true);
                    setReload(reload + 1)
                    }}>Reload</button>

        </div>
    )
}