import { AGVStatus, AssmStatus, WHStatus } from "./types";

let ip = "http://localhost"; //remember the "http://"" part
let port = 6969;

export const getAGVStatus = async (): Promise<AGVStatus> => {
    const response = await fetch(ip + ":" + port + "/status/agv", { method: "GET", mode: "cors" });
    const data = await response.json();
    return data as AGVStatus;
}

export const getAssmStatus = async (): Promise<AssmStatus> => {
    const response = await fetch(ip + ":" + port + "/status/assembler", { method: "GET", mode: "cors" });
    const data = await response.json();
    return data as AssmStatus;
}

export const getWHStatus = async (): Promise<WHStatus> => {
    const response = await fetch(ip + ":" + port + "/status/warehouse", { method: "GET", mode: "cors" });
    const data = await response.json();
    return data as WHStatus;
}

import { Part, Recipe } from "./webshop";

export const getWarehouseInventory = async (): Promise<Part[]> => {
    // ...

    return fetch(ip + ":" + port + "/warehouse/inventory", { method: 'GET', mode: 'cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching all possible parts");
        }
        return response.json();
    })
    .then(json => json as Part[]);
}

export const getRecipes = async (): Promise<Recipe[]> => {
    return fetch (ip + ":" + port + "/recipes", { method: 'GET', mode: 'cors' })
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

    const response = await fetch(ip + ":" + port + "/parts", { mode: "cors" });
     const data = await response.json();
   
    return data;

}

import { Batch } from "./webshop";

export const getQueuedBatches = async (): Promise<Batch[]> => {
    // ...

    return fetch(ip + ":" + port + "/batch/active", { method: 'GET', mode: 'cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching active active batches");
        }
        return response.json();
    })
    .then(json => json as Batch[]);
}

export const getCompletedBatches = async (): Promise<Batch[]> => {
    return fetch(ip + ":" + port + "/batch/inactive", { method: 'GET', mode: 'cors' })
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
    return fetch(ip + ":" + port + "/batch/" + batch.id + "/events", { method: 'GET', mode: 'cors' })
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
    return fetch(ip + ":" + port + "/batch/" + batch.id + "/events/newest", { method: 'GET', mode: 'cors' })
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

    return fetch(ip + ":" + port + "/batch/events/newest?amount=" + n, { method: 'GET', mode: 'cors' })
        .then(response => {
            if (!response.ok) {
                throw new Error("Error occured while fetching newest events for " + n + " batches");
            }
            return response.json();
        })
        .then(json => json as BatchEvent[]);
}

export const queueNewBatch = async (batch: Batch): Promise<Response> => {
    //stripping them of id's to help hibernate out
    const requestParts: {partId: number,count: number}[] = []; 
    batch.parts.forEach(part => {
        requestParts.push({
            partId: part.partId,
            count: part.count,
        })
    });
    const requestBatch = {
        employeeId: batch.employeeId,
        parts: requestParts, 
        hasCompleted: false
    }


    return fetch(ip + ":" + port + "/batch", { 
        method: 'POST', 
        mode: 'cors', 
        body: JSON.stringify(requestBatch),
        headers: {
            'Content-Type': 'application/json'
        }
    })
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
        ip = "http://" + newIp;
        port = newPort;
    }
}