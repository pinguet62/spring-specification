import {Routes} from "@angular/router";

import {RulesComponent} from "./rule/rules.component";
import {RuleInformationServiceResolver} from "./rule/rule-information.service";

export const appRoutes: Routes = [
    {
        path: '',
        component: RulesComponent,
        resolve: [RuleInformationServiceResolver]
    }
];