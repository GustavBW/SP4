import React from "react";
import { Batch, BatchEvent } from "../../../../ts/webshop";
import "./EventThumbnail.css";

interface EventThumbnailProps {
    event: BatchEvent;
    setSelectedBatch: (batch: Batch | null) => void;
}

export default function EventThumbnail({event, setSelectedBatch}: EventThumbnailProps) {
    return (
        <button className="EventThumbnail" onClick={e => setSelectedBatch(event.batch)}>
            <h2 className="event-title">
                {event.name}
            </h2>
            <p className="event-id">
                {event.id}
            </p>
            <p className="event-date">
                {new Date(event.timestamp).toLocaleString()}
            </p>
        </button>
    )
}