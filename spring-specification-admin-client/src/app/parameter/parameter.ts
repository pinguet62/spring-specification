export interface Parameter {
    id?: number;
    key?: string;
    value?: string;

    // used when client>server
    ruleComponent?: number;
}
