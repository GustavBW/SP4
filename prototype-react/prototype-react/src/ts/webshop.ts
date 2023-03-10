export type Part = {
    name: string;
    description: string;
    processTimeSeconds: number;
    inStock: number;
    image: string;
    id: number;
}
export const asPart = (data: any): Part => {
    return {
        name: data.name,
        description: data.description,
        processTimeSeconds: data.processTimeSeconds,
        inStock: data.inStock,
        image: data.image,
        id: data.id
    }
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
    parts: Map<number,number>;
    /**
     * How far the order has progressed (0-1)
     */ 
    completionPercentage: number;
}
export const asOrder = (data: any): Batch => {
    return {
        id: data.id,
        cmr: data.cmr,
        parts: data.parts,
        completionPercentage: data.completionPercentage
    }
}

export type ProcessChain = {
    /**
     * The id of the order this process chain belongs to
     */
    orderId: number;
    /**
     * The part this process chain is fabricating
     */
    partName: string;
    /**
     * The time it takes to fabricate one part of this type
     */
    processTimeSeconds: number;
    /**
     * How far the process of fabricating these/this part(s) has progressed
     * (0-1)
     */
    completionPercentage: number;
    /**
     * How many parts of this type have been fabricated so far
     */
    currentCount: number;
    /**
     * How many parts of this type are to be fabricated
     */
    totalCount: number;
}
export const asProcessChain = (data: any): ProcessChain => {
    return {
        orderId: data.orderId,
        partName: data.part,
        processTimeSeconds: data.processTimeSeconds,
        completionPercentage: data.completionPercentage,
        currentCount: data.currentCount,
        totalCount: data.totalCount
    }
}