import React from 'react';
import './EventList.css';
import { getNewestForNBatches } from '../../../ts/api';
import { Batch, BatchEvent } from '../../../ts/webshop';
import EventThumbnail from './eventThumbnail/EventThumbnail';

interface EventListProps {
    setDisplay: (display: string) => void;
    setSelectedBatch: (batch: Batch | null) => void;
}

const EventList = ({setDisplay, setSelectedBatch}: EventListProps): JSX.Element => {

    const [events, setEvents] = React.useState<BatchEvent[]>([]);

    React.useEffect(() => {
        const timer = setInterval(() => {
            getNewestForNBatches(10)
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
        <div className="EventList">
            <div className="event-list-header">
                <h2>Latest Events</h2>
            </div>
            {events.map((event: BatchEvent, index: number) => {
                return (
                    <EventThumbnail event={event} key={index} setSelectedBatch={setSelectedBatch}/>
                )
            })}
        </div>
    )
}
export default EventList;