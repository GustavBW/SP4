export type Part = {
    name: string;
    description: string;
    count: number;
    id: number;
    /**
     * Array of batch ids that this part is a part of
     */
    batches: number[];
}

export type BatchPart = {
    description: string;
    id: number;
    name: string;
    count: number;
}

export type Batch = {
    /**
     * The internal id of the order
     */
    id: number;
    /**
     * The customer identifier
     */
    cmr: string;
    /**
     * The parts to be fabricated and the amount of each
     * part to be fabricated
     */
    parts: BatchPart[];
    /**
     * How far the order has progressed (0-1)
     */ 
    hasCompleted: boolean;
}

export type Component = {
    id: number;
    name: string;
}

export type Recipe = {
    id: number; 
    part: Part;
    components: Component[];
}

export type BatchEvent = {
    batch: Batch;
    name: string;
    eventId: number;
    description: string;
    timestamp: number;
    progression: number;
    faulty: boolean;
}
