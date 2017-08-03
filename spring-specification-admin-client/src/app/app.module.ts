import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CdkTableModule} from "@angular/cdk";
import {MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdListModule, MdSelectModule, MdSidenavModule, MdTableModule, MdToolbarModule} from "@angular/material";
import {ButtonModule, DataTableModule, DialogModule, DropdownModule, InplaceModule, InputTextModule, SharedModule, TreeModule} from "primeng/primeng";

import {AppComponent} from "./app.component";
import {RuleComponent, EditRuleDialog, SettingsRuleDialog} from "./rule/rule.component";
import {ParameterComponent, EditParameterDialog, DeleteParameterDialog} from "./parameter/parameter.component";
import {RuleService} from "./rule/rule.service";
import {ParameterService} from "./parameter/parameter.service";

@NgModule({
    imports: [
        BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, BrowserAnimationsModule,
        ButtonModule, DataTableModule, DialogModule, DropdownModule, InplaceModule, InputTextModule, SharedModule, TreeModule,
        CdkTableModule,
        MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdListModule, MdSelectModule, MdSidenavModule, MdTableModule, MdToolbarModule
    ],
    providers: [RuleService, ParameterService],
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
