import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
    MatAutocompleteModule,
    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatSelectModule,
    MatSidenavModule,
    MatTableModule,
    MatToolbarModule
} from '@angular/material';
import {CdkTableModule} from '@angular/cdk/table';

import {appRoutes} from './app.route';
import {AppComponent} from './app.component';
import {ContentPageComponent, EmptyPageComponent} from './generic-pages.component';
import {TreeModule} from './tree/tree.module';
import {RuleListComponent} from './rule/rule-list.component';
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
        BrowserModule, FormsModule, ReactiveFormsModule, HttpClientModule, BrowserAnimationsModule,
        MatAutocompleteModule, MatButtonModule, MatCheckboxModule, MatDialogModule, MatIconModule, MatInputModule, MatListModule, MatSelectModule, MatSidenavModule, MatTableModule, MatToolbarModule,
        CdkTableModule,
        TreeModule, RouterModule.forRoot(appRoutes)
    ],
    providers: [RuleService, RuleServiceResolver, BusinessRuleService, RuleComponentService, ParameterService],
    declarations: [
        AppComponent, ContentPageComponent, EmptyPageComponent,
        RuleListComponent, BusinessRuleListComponent, CreateBusinessRuleDialog, DeleteBusinessRuleDialog, BusinessRuleDetailComponent, RuleComponentComponent, EditRuleComponentDialog, SettingsRuleComponentDialog, DeleteRuleComponentDialog,
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
