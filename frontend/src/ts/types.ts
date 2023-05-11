export type AssmStatus = {
    currentProcess: number;
    message: string;
    state: string;
    timestamp: string;
}

export type AGVStatus = {
    battery: number;
    programName: string;
    state: string;
    timestamp: string;
    code: number;
}

export type WHStatus = {
    currentProcess: string;
    message: string;
    code: number;
}

export type WHItem = {
    id: number;
    content: string;
}