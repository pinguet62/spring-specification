import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
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

import {AppComponent} from "./app.component";
import {TreeModule} from "./tree/tree.module";
import {EditRuleDialog, RuleComponent, SettingsRuleDialog} from "./rule/rule.component";
import {DeleteParameterDialog, EditParameterDialog, ParameterComponent} from "./parameter/parameter.component";
import {RuleInformationService} from "./rule/rule-information.service";
import {RuleService} from "./rule/rule.service";
import {ParameterService} from "./parameter/parameter.service";

@NgModule({
    imports: [
        BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, BrowserAnimationsModule,
        MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdListModule, MdSelectModule, MdSidenavModule, MdTableModule, MdToolbarModule,
        CdkTableModule,
        TreeModule
    ],
    providers: [RuleInformationService, RuleService, ParameterService],
    declarations: [
        AppComponent,
        RuleComponent, EditRuleDialog, SettingsRuleDialog,
        ParameterComponent, EditParameterDialog, DeleteParameterDialog
    ],
    entryComponents: [
        EditRuleDialog, SettingsRuleDialog,
        EditParameterDialog, DeleteParameterDialog
    ],
    bootstrap: [AppComponent]
})
export class AppModule {}
