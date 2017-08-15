import {Parameter} from '../parameter/parameter';

export interface RuleComponent {
    id?: number;
    key?: string;
    description?: string;
    components?: RuleComponent[];
    parameters?: Parameter[];

    // used when client>server
    parent?: number;
    index?: number;
}
