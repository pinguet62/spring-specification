import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MdDialog, MdDialogConfig, MdDialogRef} from '@angular/material';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import {ObservableDataSource} from '../simple-data-source';
import {BusinessRuleService} from './business-rule.service';
import {BusinessRule} from './business-rule';

@Component({
    selector: 'p62-business-rule-update',
    template: `
        <h2 md-dialog-title>Rule</h2>
        <md-dialog-content>
            <form #form="ngForm">
                <md-input-container>
                    <input mdInput placeholder="Id" [(ngModel)]="businessRule.id" name="id" required>
                </md-input-container>
                <br>
                <md-input-container>
                    <input mdInput placeholder="Argument type" [(ngModel)]="businessRule.argumentType" name="argumentType">
                </md-input-container>
                <br>
                <md-input-container>
                    <input mdInput placeholder="Title" [(ngModel)]="businessRule.title" name="title">
                </md-input-container>
            </form>
        </md-dialog-content>
        <md-dialog-actions>
            <button md-button (click)="dialogRef.close(undefined)">Cancel</button>
            <button md-button [disabled]="!form.form.valid" (click)="dialogRef.close(businessRule)">Apply</button>
        </md-dialog-actions>`
})
export class CreateBusinessRuleDialog {

    businessRule: BusinessRule = {};

    constructor(public dialogRef: MdDialogRef<CreateBusinessRuleDialog>) {
    }

}

@Component({
    selector: 'p62-business-rule-delete',
    template: `
        <h2 md-dialog-title>Delete</h2>
        <md-dialog-content>
            Delete this rule?
        </md-dialog-content>
        <md-dialog-actions>
            <button md-button (click)="dialogRef.close(false)">Cancel</button>
            <button md-button (click)="dialogRef.close(true)">Discard</button>
        </md-dialog-actions>`
})
export class DeleteBusinessRuleDialog {

    constructor(public dialogRef: MdDialogRef<DeleteBusinessRuleDialog>) {
    }

}

@Component({
    selector: 'p62-business-rule-list',
    template: `
        <div style="position: relative;">
            <md-table [dataSource]="dataSource" style="margin-right: 50px;">
                <ng-container cdkColumnDef="id">
                    <md-header-cell *cdkHeaderCellDef>Id</md-header-cell>
                    <md-cell *cdkCellDef="let businessRule">{{businessRule.id}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="argumentType">
                    <md-header-cell *cdkHeaderCellDef>Argument type</md-header-cell>
                    <md-cell *cdkCellDef="let businessRule">{{businessRule.argumentType}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="title">
                    <md-header-cell *cdkHeaderCellDef>Title</md-header-cell>
                    <md-cell *cdkCellDef="let businessRule">{{businessRule.title}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="actions">
                    <md-header-cell *cdkHeaderCellDef></md-header-cell>
                    <md-cell *cdkCellDef="let businessRule">
                        <!-- TODO fix <md-row (click)> triggered when <button (click)> -->
                        <button md-icon-button (click)="openDeleteDialog(businessRule)">
                            <md-icon>delete</md-icon>
                        </button>
                    </md-cell>
                </ng-container>

                <md-header-row *cdkHeaderRowDef="displayedColumns"></md-header-row>
                <md-row *cdkRowDef="let businessRule; columns: displayedColumns;" (click)="router.navigate([businessRule.id])"></md-row>
            </md-table>

            <button md-mini-fab (click)="openCreateDialog()" style="position: absolute; bottom: 0; margin-bottom: 5px; right: 0; margin-right: 5px;">
                <md-icon>add</md-icon>
            </button>
        </div>`,
    styles: [`
        .mat-row:hover {
            background-color: #f0f0f0;
        }
    `, `
        /* column width */

        .mat-cell, .mat-header-cell {
            flex: auto;
        }

        .mat-column-id {
            width: 15%;
        }

        .mat-column-argumentType {
            width: 25%;
        }

        .mat-column-title {
            width: 50%;
        }

        .mat-column-actions {
            width: 10%;
        }
    `
    ]
})
export class BusinessRuleListComponent {

    // Table
    displayedColumns = ['id', 'argumentType', 'title', 'actions'];
    dataSource: ObservableDataSource<BusinessRule> = new ObservableDataSource<BusinessRule>(new BehaviorSubject<BusinessRule[]>([]));

    constructor(public router: Router,
                private dialog: MdDialog,
                private businessRuleService: BusinessRuleService) {
        this.refresh();
    }

    refresh(): void {
        this.businessRuleService.getAll().subscribe(rs =>
            this.dataSource.observable.next(rs)
        );
    }

    openCreateDialog(): void {
        let createDialog: MdDialogRef<CreateBusinessRuleDialog> = this.dialog.open(CreateBusinessRuleDialog, this.getCommonDialogConfig());
        createDialog.afterClosed().subscribe((createdBusinessRule: BusinessRule) => {
            // Canceled
            if (createdBusinessRule == null)
                return;

            this.businessRuleService.create(createdBusinessRule).subscribe(x =>
                this.refresh()
            );
        });
    }

    openDeleteDialog(businessRule: BusinessRule): void {
        let deleteDialog: MdDialogRef<DeleteBusinessRuleDialog> = this.dialog.open(DeleteBusinessRuleDialog, this.getCommonDialogConfig());
        deleteDialog.afterClosed().subscribe((confirm: boolean) => {
            if (confirm)
                this.businessRuleService.delete(businessRule).subscribe(x =>
                    this.refresh()
                );
        });
    }

    getCommonDialogConfig(): MdDialogConfig {
        return {disableClose: true};
    }

}
