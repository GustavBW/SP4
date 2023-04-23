export enum KnownSystemComponents {
    AGV = "agv", 
    Warehouse = "warehouse", 
    Assembler = "assembler"
}

export interface IUnknownState {
    component: KnownSystemComponents;
    process: string;
    status: string;
    message: string;
    battery: number;
    temperature: number;
    humidity: number;
    pressure: number;
    capacity: number;
    velocity: number;
    timestamp: Date;
}


let ip = "http://localhost";
let port = 6969;

export const getStateOf = async (component: KnownSystemComponents): Promise<IUnknownState> => {
    // ...
    return fetch(ip + ":" + port + "/status/" + component, { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching state of " + component);
        }
        return response.json();
    })
    .then(json => json as IUnknownState);
} 

import { Part, Recipe } from "./webshop";

export const getWarehouseInventory = async (): Promise<Part[]> => {
    // ...

    return fetch(ip + ":" + port + "/warehouse/inventory", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching all possible parts");
        }
        return response.json();
    })
    .then(json => json as Part[]);
}

export const getRecipes = async (): Promise<Recipe[]> => {
    return fetch (ip + ":" + port + "/recipes", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching all possible parts");
        }
        console.log(response);
        return response.json();
    })
    .then(json => json as Recipe[]);
}

export const getAvailableParts = async (): Promise<Part[]> => {
    // ...

    return fetch(ip + ":" + port + "/parts", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching all possible parts");
        }
        console.log(response);
        return response.json();
    })
    .then(json => json as Part[]);
}

import { Batch } from "./webshop";

export const getQueuedBatches = async (): Promise<Batch[]> => {
    // ...

    return fetch(ip + ":" + port + "/batch/active", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching active active batches");
        }
        return response.json();
    })
    .then(json => json as Batch[]);
}

export const getCompletedBatches = async (): Promise<Batch[]> => {
    return fetch(ip + ":" + port + "/batch/inactive", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching inactive batches");
        }
        return response.json();
    })
    .then(json => json as Batch[]);
}


import {BatchEvent} from "./webshop";

export const getEventsForBatch = async (batch: Batch): Promise<BatchEvent[]> => {
    // ...
    return fetch(ip + ":" + port + "/batch/" + batch.id + "/events", { method: 'GET', mode: 'no-cors' })
     .then(response => {
         if (!response.ok) {
             throw new Error("Error occured while fetching events for batch " + batch.id);
         }
         return response.json();
    })
    .then(json => json as BatchEvent[]);
}

export const getNewestEventForBatch = async (batch:Batch): Promise<BatchEvent> => {
    // ...
    return fetch(ip + ":" + port + "/batch/" + batch.id + "/events/newest", { method: 'GET', mode: 'no-cors' })
     .then(response => {
         if (!response.ok) {
             throw new Error("Error occured while fetching newest event for batch " + batch.id);
         }
         return response.json();
    })
    .then(json => json as BatchEvent);
}

export const getNewestForNBatches = async (n: number): Promise<BatchEvent[]> => {
    // ...
    if(n < 1) n = 10;

    return fetch(ip + ":" + port + "/batch/events/newest?amount=" + n, { method: 'GET', mode: 'no-cors' })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error occured while fetching newest events for " + n + " batches");
            }
            return response.json();
        })
        .then(json => json as BatchEvent[]);
}

export const queueNewBatch = async (batch: Batch): Promise<Response> => {
    // ...
    return fetch(ip + ":" + port + "/batch", { method: 'POST', mode: 'no-cors', body: JSON.stringify(batch) })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while placing new order");
        }
        return response;
    });
}

export const setEndpoint = (newIp: string, newPort: number) => {
    //show error
    if (newIp === "" || newIp === undefined || newIp === null || newPort === undefined || newPort === null || newPort === 0) {
        return;
    }
    if(window.confirm("Are you sure you want to change the Endpoint? This will not cause any change in production, but may harm monitoring.")){
        ip = newIp;
        port = newPort;
    }
}