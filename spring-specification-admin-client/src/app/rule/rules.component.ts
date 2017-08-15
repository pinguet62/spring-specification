import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {ObservableDataSource} from '../simple-data-source';
import {RuleService} from './rule.service';
import {Rule} from './rule';

@Component({
    selector: 'p62-rules',
    template: `
        <div style="position: relative;">
            <md-table [dataSource]="dataSource" style="margin-right: 50px;">
                <ng-container cdkColumnDef="id">
                    <md-header-cell *cdkHeaderCellDef>Id</md-header-cell>
                    <md-cell *cdkCellDef="let rule">{{rule.id}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="title">
                    <md-header-cell *cdkHeaderCellDef>Title</md-header-cell>
                    <md-cell *cdkCellDef="let rule">TODO</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="description">
                    <md-header-cell *cdkHeaderCellDef>Description</md-header-cell>
                    <md-cell *cdkCellDef="let rule">{{rule.description}}</md-cell>
                </ng-container>
    
                <md-header-row *cdkHeaderRowDef="displayedColumns"></md-header-row>
                <md-row *cdkRowDef="let rule; columns: displayedColumns;" (click)="router.navigate([rule.id])"></md-row>
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
export class RulesComponent {

    // Table
    displayedColumns = ['id', 'title', 'description'];
    dataSource: ObservableDataSource<Rule> = new ObservableDataSource<Rule>(new BehaviorSubject<Rule[]>([]));

    constructor(public router: Router,
                private ruleService: RuleService) {
        this.ruleService.getAllRoots().subscribe(rs =>
            this.dataSource.observable.next(rs)
        );
    }

}
