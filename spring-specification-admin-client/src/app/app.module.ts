import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {ButtonModule, DataTableModule, DialogModule, DropdownModule, InplaceModule, InputTextModule, SharedModule, TreeModule} from "primeng/primeng";

import {AppComponent} from './app.component';
import {RuleComponent} from "./rule.component";
import {ParameterComponent} from "./parameter.component";
import {RuleService} from "./rule.service";
import {ParameterService} from "./parameter.service";

@NgModule({
    declarations: [
        AppComponent, RuleComponent, ParameterComponent
    ],
    imports: [
        BrowserModule, BrowserAnimationsModule, FormsModule, ReactiveFormsModule, HttpModule,
        ButtonModule, DataTableModule, DialogModule, DropdownModule, InplaceModule, InputTextModule, SharedModule, TreeModule
    ],
    providers: [RuleService, ParameterService],
    bootstrap: [AppComponent]
})
export class AppModule { }
