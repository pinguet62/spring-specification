import {Component} from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {ObservableDataSource} from '../simple-data-source';
import {Rule} from './rule';
import {RuleService} from './rule.service';

@Component({
    selector: 'p62-rule-list',
    template: `
        <mat-table [dataSource]="dataSource" style="margin-right: 50px;">
            <ng-container cdkColumnDef="key">
                <mat-header-cell *cdkHeaderCellDef>Key</mat-header-cell>
                <mat-cell *cdkCellDef="let rule">{{rule.key}}</mat-cell>
            </ng-container>
            <ng-container cdkColumnDef="name">
                <mat-header-cell *cdkHeaderCellDef>Name</mat-header-cell>
                <mat-cell *cdkCellDef="let rule">{{rule.name}}</mat-cell>
            </ng-container>
            <ng-container cdkColumnDef="description">
                <mat-header-cell *cdkHeaderCellDef>Description</mat-header-cell>
                <mat-cell *cdkCellDef="let rule">{{rule.description}}</mat-cell>
            </ng-container>

            <mat-header-row *cdkHeaderRowDef="displayedColumns"></mat-header-row>
            <mat-row *cdkRowDef="let rule; columns: displayedColumns;"></mat-row>
        </mat-table>
    `
})
export class RuleListComponent {

    // Table
    displayedColumns = ['key', 'name', 'description'];
    dataSource: ObservableDataSource<Rule> = new ObservableDataSource<Rule>(new BehaviorSubject<Rule[]>([]));

    constructor(private ruleService: RuleService) {
        this.ruleService.load().subscribe(rs =>
            this.dataSource.observable.next(rs)
        );
    }

}
