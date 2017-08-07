import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
    MdButtonModule,
    MdCheckboxModule,
    MdDialogModule,
    MdIconModule,
    MdInputModule,
    MdListModule,
    MdSelectModule,
    MdSidenavModule,
    MdTableModule,
    MdToolbarModule
} from "@angular/material";
import {CdkTableModule} from "@angular/cdk";

import {appRoutes} from './app.route';
import {AppComponent} from "./app.component";
import {TreeModule} from "./tree/tree.module";
import {RuleInformationService, RuleInformationServiceResolver} from "./rule/rule-information.service";
import {RuleService} from "./rule/rule.service";
import {RulesComponent} from "./rule/rules.component";
import {EditRuleDialog, RuleComponent, SettingsRuleDialog} from "./rule/rule.component";
import {ParameterService} from "./parameter/parameter.service";
import {DeleteParameterDialog, EditParameterDialog, ParameterComponent} from "./parameter/parameter.component";

@NgModule({
    imports: [
        BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, BrowserAnimationsModule,
        MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdListModule, MdSelectModule, MdSidenavModule, MdTableModule, MdToolbarModule,
        CdkTableModule,
        TreeModule, RouterModule.forRoot(appRoutes)
    ],
    providers: [RuleInformationService, RuleInformationServiceResolver, RuleService, ParameterService],
    declarations: [
        AppComponent,
        RulesComponent, RuleComponent, EditRuleDialog, SettingsRuleDialog,
        ParameterComponent, EditParameterDialog, DeleteParameterDialog
    ],
    entryComponents: [
        EditRuleDialog, SettingsRuleDialog,
        EditParameterDialog, DeleteParameterDialog
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
