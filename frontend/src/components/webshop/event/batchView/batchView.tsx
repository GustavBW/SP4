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
                        setEvents(events.reverse());
                        //to get the newest on top
                    }
                });
        }, 1000);
        return () => clearInterval(timer);
    }, [batch]);

    return (
        <div className='BatchView'>
            <button className="close-button" onClick={e => onDeselect()}>x</button>
            <div className='batch-view-header'>
                <h1>Batch ID {batch.id}</h1>
                <h1>Placed by {batch.employeeId}</h1>
                <h1 className={batch.hasCompleted ? "inactive" : "active"}>{batch.hasCompleted ? "Inactive" : "Active"}</h1>
            </div>
            <div className="body">                
                <div className="body-headers">
                    <h2 className="event-header">Events:</h2>
                    <h2 className="event-header">Contents:</h2>
                </div>
                <div className="events-contents">
                    <div className='batch-view-event-list'>
                        {events.map((event: BatchEvent, index: number) => {
                            return (
                                <div className='batch-view-event' key={index}>
                                    <div className="twobytwo-grid">
                                        <p className="event-header">Name</p>
                                        <p className="event-header">Timestamp</p>
                                        <p className="event-header highlight">{event.name}</p> 
                                        <p className="event-header highlight">{new Date(event.timestamp).toLocaleString()}</p>

                                    </div>
                                    <p className="batch-view-description">
                                        {event.description}
                                    </p>

                                </div>
                            )
                        })}
                    </div>
                    <div className="batch-view-event-list">
                        {batch.parts.map((part, index) => {
                            return (
                                <div className="twobytwo-grid" key={index}>
                                    <p className="event-header">Part ID</p>
                                    <p className="event-header highlight">{part.id}</p>
                                    <p className="event-header">Quantity</p>
                                    <p className="event-header highlight">{part.count}</p>
                                </div>
                            )
                        })}
                    </div>
                </div>
            </div>

            
        </div>
    )
};