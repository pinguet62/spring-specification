import {RuleComponent} from '../rule-component/rule-component';

export interface BusinessRule {
    id?: string;
    title?: string;
    rootRuleComponent?: RuleComponent;
}
