import {Routes} from '@angular/router';

import {BusinessRuleListComponent} from './business-rule/business-rules-list.component';
import {RuleServiceResolver} from './rule/rule.service';
import {BusinessRuleDetailComponent} from './business-rule/business-rules-detail.component';

export const appRoutes: Routes = [
    {
        path: '',
        component: BusinessRuleListComponent,
        resolve: [RuleServiceResolver]
    },
    {
        path: ':id',
        component: BusinessRuleDetailComponent
    }
];
