import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import {ObservableDataSource} from '../simple-data-source';
import {BusinessRuleService} from './business-rule.service';
import {BusinessRule} from './business-rule';

@Component({
    selector: 'p62-business-rule-list',
    template: `
        <div style="position: relative;">
            <md-table [dataSource]="dataSource" style="margin-right: 50px;">
                <ng-container cdkColumnDef="id">
                    <md-header-cell *cdkHeaderCellDef>Id</md-header-cell>
                    <md-cell *cdkCellDef="let businessRule">{{businessRule.id}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="title">
                    <md-header-cell *cdkHeaderCellDef>Title</md-header-cell>
                    <md-cell *cdkCellDef="let businessRule">{{businessRule.title}}</md-cell>
                </ng-container>

                <md-header-row *cdkHeaderRowDef="displayedColumns"></md-header-row>
                <md-row *cdkRowDef="let businessRule; columns: displayedColumns;" (click)="router.navigate([businessRule.id])"></md-row>
            </md-table>

            <button md-mini-fab style="position: absolute; bottom: 0; margin-bottom: 5px; right: 0; margin-right: 5px;">
                <md-icon>add</md-icon>
            </button>
        </div>`,
    styles: [
            `
            .mat-row:hover {
                background-color: #f0f0f0;
            }`
    ]
})
export class BusinessRuleListComponent {

    // Table
    displayedColumns = ['id', 'title'];
    dataSource: ObservableDataSource<BusinessRule> = new ObservableDataSource<BusinessRule>(new BehaviorSubject<BusinessRule[]>([]));

    constructor(public router: Router,
                private businessRuleService: BusinessRuleService) {
        this.businessRuleService.getAll().subscribe(rs =>
            this.dataSource.observable.next(rs)
        );
    }

}
