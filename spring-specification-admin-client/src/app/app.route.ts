import {Routes} from '@angular/router';

import {ContentPageComponent, EmptyPageComponent} from './generic-pages.component';
import {RuleListComponent} from './rule/rule-list.component';
import {RuleServiceResolver} from './rule/rule.service';
import {BusinessRuleListComponent} from './business-rule/business-rules-list.component';
import {BusinessRuleDetailComponent} from './business-rule/business-rules-detail.component';

export const appRoutes: Routes = [
    {
        path: '',
        component: EmptyPageComponent
    },
    {
        path: 'rule',
        component: RuleListComponent
    },
    {
        path: 'businessRule',
        component: ContentPageComponent,
        children: [
            {
                path: '',
                component: BusinessRuleListComponent,
                resolve: [RuleServiceResolver]
            },
            {
                path: ':id',
                component: BusinessRuleDetailComponent
            }
        ]
    }
];
