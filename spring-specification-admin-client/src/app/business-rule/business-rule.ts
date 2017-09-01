import {RuleComponent} from '../rule-component/rule-component';

export interface BusinessRule {
    id?: string;
    argumentType?: string;
    rootRuleComponent?: RuleComponent;
    title?: string;
}
