import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
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
} from '@angular/material';
import {CdkTableModule} from '@angular/cdk';

import {appRoutes} from './app.route';
import {AppComponent} from './app.component';
import {TreeModule} from './tree/tree.module';
import {RuleService, RuleServiceResolver} from './rule/rule.service';
import {BusinessRuleService} from './business-rule/business-rule.service';
import {RuleComponentService} from './rule-component/rule-component.service';
import {BusinessRuleListComponent, CreateBusinessRuleDialog, DeleteBusinessRuleDialog} from './business-rule/business-rules-list.component';
import {BusinessRuleDetailComponent} from './business-rule/business-rules-detail.component';
import {DeleteRuleComponentDialog, EditRuleComponentDialog, RuleComponentComponent, SettingsRuleComponentDialog} from './rule-component/rule-component.component';
import {ParameterService} from './parameter/parameter.service';
import {DeleteParameterDialog, EditParameterDialog, ParameterComponent} from './parameter/parameter.component';

@NgModule({
    imports: [
        BrowserModule, FormsModule, ReactiveFormsModule, HttpModule, BrowserAnimationsModule,
        MdButtonModule, MdCheckboxModule, MdDialogModule, MdIconModule, MdInputModule, MdListModule, MdSelectModule, MdSidenavModule, MdTableModule, MdToolbarModule,
        CdkTableModule,
        TreeModule, RouterModule.forRoot(appRoutes)
    ],
    providers: [RuleService, RuleServiceResolver, BusinessRuleService, RuleComponentService, ParameterService],
    declarations: [
        AppComponent,
        BusinessRuleListComponent, CreateBusinessRuleDialog, DeleteBusinessRuleDialog, BusinessRuleDetailComponent, RuleComponentComponent, EditRuleComponentDialog, SettingsRuleComponentDialog, DeleteRuleComponentDialog,
        ParameterComponent, EditParameterDialog, DeleteParameterDialog
    ],
    entryComponents: [
        CreateBusinessRuleDialog, DeleteBusinessRuleDialog,
        EditRuleComponentDialog, SettingsRuleComponentDialog, DeleteRuleComponentDialog,
        EditParameterDialog, DeleteParameterDialog
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
