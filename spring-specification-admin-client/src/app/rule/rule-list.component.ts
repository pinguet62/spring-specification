import {Component} from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {ObservableDataSource} from '../simple-data-source';
import {Rule} from './rule';
import {RuleService} from './rule.service';

@Component({
    selector: 'p62-rule-list',
    template: `
        <md-table [dataSource]="dataSource" style="margin-right: 50px;">
            <ng-container cdkColumnDef="key">
                <md-header-cell *cdkHeaderCellDef>Key</md-header-cell>
                <md-cell *cdkCellDef="let rule">{{rule.key}}</md-cell>
            </ng-container>
            <ng-container cdkColumnDef="name">
                <md-header-cell *cdkHeaderCellDef>Name</md-header-cell>
                <md-cell *cdkCellDef="let rule">{{rule.name}}</md-cell>
            </ng-container>
            <ng-container cdkColumnDef="description">
                <md-header-cell *cdkHeaderCellDef>Description</md-header-cell>
                <md-cell *cdkCellDef="let rule">{{rule.description}}</md-cell>
            </ng-container>

            <md-header-row *cdkHeaderRowDef="displayedColumns"></md-header-row>
            <md-row *cdkRowDef="let rule; columns: displayedColumns;"></md-row>
        </md-table>
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
