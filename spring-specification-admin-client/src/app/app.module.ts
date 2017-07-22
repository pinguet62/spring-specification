import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {CdkTableModule} from "@angular/cdk";
import {MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdSelectModule, MdTableModule} from '@angular/material';
import {ButtonModule, DataTableModule, DialogModule, DropdownModule, InplaceModule, InputTextModule, SharedModule, TreeModule} from "primeng/primeng";

import {AppComponent} from './app.component';
import {RuleComponent, EditRuleDialog} from "./rule/rule.component";
import {ParameterComponent, EditParameterDialog, DeleteParameterDialog} from "./parameter/parameter.component";
import {RuleService} from "./rule/rule.service";
import {ParameterService} from "./parameter/parameter.service";

@NgModule({
    imports: [
        BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, BrowserAnimationsModule,
        ButtonModule, DataTableModule, DialogModule, DropdownModule, InplaceModule, InputTextModule, SharedModule, TreeModule,
        CdkTableModule,
        MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdSelectModule, MdTableModule
    ],
    providers: [RuleService, ParameterService],
    declarations: [
        AppComponent,
        RuleComponent, EditRuleDialog,
        ParameterComponent, EditParameterDialog, DeleteParameterDialog
    ],
    entryComponents: [
        EditRuleDialog,
        EditParameterDialog, DeleteParameterDialog
    ],
    bootstrap: [AppComponent]
})
export class AppModule {}
