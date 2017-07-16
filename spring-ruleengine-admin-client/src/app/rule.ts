import {Parameter} from "./parameter";

export interface Rule {
    id?: number;
    key?: string;
    components?: Rule[];
    parameters?: Parameter[];

    // used when client>server
    parent: number;
}