import {Routes} from '@angular/router';

import {RulesComponent} from './rule/rules.component';
import {RuleInformationServiceResolver} from './rule/rule-information.service';
import {RuleComponent} from './rule/rule.component';

export const appRoutes: Routes = [
    {
        path: '',
        component: RulesComponent,
        resolve: [RuleInformationServiceResolver]
    },
    {
        path: ':id',
        component: RuleComponent
    }
];
