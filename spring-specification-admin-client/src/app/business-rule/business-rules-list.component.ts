import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material/dialog';
import {BehaviorSubject} from 'rxjs';

import {ObservableDataSource} from '../simple-data-source';
import {BusinessRuleService} from './business-rule.service';
import {BusinessRule} from './business-rule';

@Component({
    selector: 'app-business-rule-update',
    template: `
        <h2 mat-dialog-title>Rule</h2>
        <mat-dialog-content>
            <form #form="ngForm">
                <mat-form-field>
                    <input matInput placeholder="Id" [(ngModel)]="businessRule.id" name="id" required>
                </mat-form-field>
                <br>
                <mat-form-field>
                    <input matInput placeholder="Argument type" [(ngModel)]="businessRule.argumentType" name="argumentType">
                </mat-form-field>
                <br>
                <mat-form-field>
                    <input matInput placeholder="Title" [(ngModel)]="businessRule.title" name="title">
                </mat-form-field>
            </form>
        </mat-dialog-content>
        <mat-dialog-actions>
            <button mat-button (click)="dialogRef.close(undefined)">Cancel</button>
            <button mat-button [disabled]="!form.form.valid" (click)="dialogRef.close(businessRule)">Apply</button>
        </mat-dialog-actions>`
})
export class CreateBusinessRuleDialogComponent {

    businessRule: BusinessRule = {};

    constructor(public dialogRef: MatDialogRef<CreateBusinessRuleDialogComponent>) {
    }

}

@Component({
    selector: 'app-business-rule-delete',
    template: `
        <h2 mat-dialog-title>Delete</h2>
        <mat-dialog-content>
            Delete this rule?
        </mat-dialog-content>
        <mat-dialog-actions>
            <button mat-button (click)="dialogRef.close(false)">Cancel</button>
            <button mat-button (click)="dialogRef.close(true)">Discard</button>
        </mat-dialog-actions>`
})
export class DeleteBusinessRuleDialogComponent {

    constructor(public dialogRef: MatDialogRef<DeleteBusinessRuleDialogComponent>) {
    }

}

@Component({
    selector: 'app-business-rule-list',
    template: `
        <div style="position: relative;">
            <mat-table [dataSource]="dataSource" style="margin-right: 50px;">
                <ng-container cdkColumnDef="id">
                    <mat-header-cell *cdkHeaderCellDef>Id</mat-header-cell>
                    <mat-cell *cdkCellDef="let businessRule">{{businessRule.id}}</mat-cell>
                </ng-container>
                <ng-container cdkColumnDef="argumentType">
                    <mat-header-cell *cdkHeaderCellDef>Argument type</mat-header-cell>
                    <mat-cell *cdkCellDef="let businessRule">{{businessRule.argumentType}}</mat-cell>
                </ng-container>
                <ng-container cdkColumnDef="title">
                    <mat-header-cell *cdkHeaderCellDef>Title</mat-header-cell>
                    <mat-cell *cdkCellDef="let businessRule">{{businessRule.title}}</mat-cell>
                </ng-container>
                <ng-container cdkColumnDef="actions">
                    <mat-header-cell *cdkHeaderCellDef></mat-header-cell>
                    <mat-cell *cdkCellDef="let businessRule">
                        <!-- TODO fix <mat-row (click)> triggered when <button (click)> -->
                        <button mat-icon-button (click)="openDeleteDialog(businessRule)">
                            <mat-icon>delete</mat-icon>
                        </button>
                    </mat-cell>
                </ng-container>

                <mat-header-row *cdkHeaderRowDef="displayedColumns"></mat-header-row>
                <mat-row *cdkRowDef="let businessRule; columns: displayedColumns;" (click)="router.navigate(['businessRule', businessRule.id])"></mat-row>
            </mat-table>

            <button mat-mini-fab (click)="openCreateDialog()" style="position: absolute; bottom: 0; margin-bottom: 5px; right: 0; margin-right: 5px;">
                <mat-icon>add</mat-icon>
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
                private dialog: MatDialog,
                private businessRuleService: BusinessRuleService) {
        this.refresh();
    }

    refresh(): void {
        this.businessRuleService.getAll().subscribe(rs =>
            this.dataSource.observable.next(rs)
        );
    }

    openCreateDialog(): void {
        const createDialog: MatDialogRef<CreateBusinessRuleDialogComponent> = this.dialog.open(CreateBusinessRuleDialogComponent, this.getCommonDialogConfig());
        createDialog.afterClosed().subscribe((createdBusinessRule: BusinessRule) => {
            // Canceled
            if (createdBusinessRule == null) {
                return;
            }

            this.businessRuleService.create(createdBusinessRule).subscribe(x =>
                this.refresh()
            );
        });
    }

    openDeleteDialog(businessRule: BusinessRule): void {
        const deleteDialog: MatDialogRef<DeleteBusinessRuleDialogComponent> = this.dialog.open(DeleteBusinessRuleDialogComponent, this.getCommonDialogConfig());
        deleteDialog.afterClosed().subscribe((confirm: boolean) => {
            if (confirm) {
                this.businessRuleService.delete(businessRule).subscribe(x =>
                    this.refresh()
                );
            }
        });
    }

    getCommonDialogConfig(): MatDialogConfig {
        return {disableClose: true};
    }

}
