export interface Parameter {
    id?: number;
    key?: string;
    value?: string;
    type?: string;

    // used when client>server
    rule?: number;
}