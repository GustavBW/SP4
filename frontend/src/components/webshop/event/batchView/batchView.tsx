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
    }, []);

    return (
        <div className='BatchView'>
            <div className='batch-view-header'>
                <div className="batch-info">
                    <h2 className="larger-font">Batch ID {batch.id}</h2>
                    <h2 className="larger-font">Placed by {batch.employeeId}</h2>
                    <h1>{batch.hasCompleted ? "Inactive" : "Active"}</h1>
                </div>
                <div className="body-headers">
                    <p className="event-header">Events:</p>
                    <p className="event-header">Contents:</p>
                </div>
            </div>
            <div className="body">
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
                                <p className="description">
                                    {event.description}
                                </p>

                            </div>
                        )
                    })}
                </div>
                <div className="batch-contents">
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

            <button className="close-button" onClick={e => onDeselect()}>x</button>
        </div>
    )
};