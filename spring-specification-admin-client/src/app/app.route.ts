import {Routes} from "@angular/router";
import {AppComponent} from "./app.component";
import {RuleInformationServiceResolver} from "./rule/rule-information.service";

export const appRoutes: Routes = [
    {
        path: '',
        component: AppComponent,
        resolve: [RuleInformationServiceResolver]
    }
];