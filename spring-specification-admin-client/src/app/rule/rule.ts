import {Parameter} from "../parameter/parameter";

export interface Rule {
    id?: number;
    key?: string;
    name?: string;
    description?: string;
    components?: Rule[];
    parameters?: Parameter[];

    // used when client>server
    parent?: number;
    index?: number;
}