import React, { useEffect } from 'react';
import "./batchView.css";
import { Batch, BatchEvent } from '../../../../ts/webshop';
import { getEventsForBatch } from '../../../../ts/api';

interface BatchViewProps {
    batch: Batch;
    onDeselect: () => void;
}

export default function BatchView({batch, onDeselect}: BatchViewProps) {
    const [events, setEvents] = React.useState<BatchEvent[]>([]);

    React.useEffect(() => {
        const timer = setInterval(() => {
            getEventsForBatch(batch)
                .catch(error => console.log(error))
                .then((events: BatchEvent[] | void) => {
                    if (events) {
                        setEvents(events);
                    }
                });
        }, 1000);
        return () => clearInterval(timer);
    }, []);

    return (
        <div className='BatchView'>
            <div className='batch-view-header'>
                {batch.id}
                {batch.employeeId}
            </div>
            <div className='batch-view-event-list'>
                
            {events.map((event: BatchEvent, index: number) => {
                return (
                    <div className='batch-view-event'>
                        <p>
                            {event.name}
                        </p>
                        <p>
                            {event.description}
                        </p>
                        <p>
                            {new Date(event.timestamp).toLocaleString()}
                        </p>
                    </div>
                )
            })}
            </div>
            <button onClick={e => onDeselect()}>
                x
            </button>
        </div>
    )
};