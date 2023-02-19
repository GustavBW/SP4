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

const ip = '123.123.12', port = 1234;

export const getStateOf = (component: KnownSystemComponents): Promise<IUnknownState> => {
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

import { Part } from "./webshop";
import placeholderParts from "./placeholderParts.json";

export const getAllPossibleParts = (): Promise<Part[]> => {
    // ...
    return loadPlaceholderPartsAsync();

    return fetch(ip + ":" + port + "/warehouse/parts", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching all possible parts");
        }
        return response.json();
    })
    .then(json => json as Part[]);
}
const loadPlaceholderPartsAsync = async (): Promise<Part[]> => {
    const parts = new Array<Part>();
    for (const part of placeholderParts) {
        parts.push({
            name: part.name,
            description: part.description,
            processTimeSeconds: -1,
            inStock: Number(part.inStock),
            image: part.image
        });
        parts.push({
            name: part.name,
            description: part.description,
            processTimeSeconds: -1,
            inStock: Number(part.inStock),
            image: part.image
        });
    }
    return parts;
}

import { Order } from "./webshop";

export const placeOrder = (parts: Order): Promise<void> => {
    // ...
    return fetch(ip + ":" + port + "/order", { method: 'POST', mode: 'no-cors', body: JSON.stringify(parts) })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while placing order");
        }
    });
}

import placeholderCategories from "./placeholderCategories.json";

export const getWarehouseCategories = (): Promise<string[]> => {
    // ...
    return Promise.resolve(placeholderCategories as string[]);

    return fetch(ip + ":" + port + "/warehouse/categories", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching warehouse categories");
        }
        return response.json();
    })
    .then(json => json as string[]);
}

import { ProcessChain } from "./webshop";
import placeholderProcessChains from "./placeholderProcessChains.json";

export const getActiveProcessChains = (): Promise<ProcessChain[]> => {
    // ...
    
    return Promise.resolve(placeholderProcessChains as ProcessChain[]);

    return fetch(ip + ":" + port + "/process/active", { method: 'GET', mode: 'no-cors' })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while fetching active process chains");
        }
        return response.json();
    })
    .then(json => json as ProcessChain[]);
}

export const placeNewOrder = (order: Order): Promise<void> => {
    // ...
    return fetch(ip + ":" + port + "/order", { method: 'POST', mode: 'no-cors', body: JSON.stringify(order) })
    .then(response => {
        if (!response.ok) {
            throw new Error("Error occured while placing new order");
        }
    });
}