import {Component, Inject, Input, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogConfig, MatDialogRef} from '@angular/material';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

import {ObservableDataSource} from '../simple-data-source';
import {Parameter} from './parameter';
import {ParameterService} from './parameter.service';
import {RuleComponent} from '../rule-component/rule-component';

@Component({
    selector: 'p62-parameter-edit',
    template: `
        <h2 mat-dialog-title>Parameter</h2>
        <mat-dialog-content>
            <form #form="ngForm">
                <mat-form-field>
                    <input matInput placeholder="Key" [(ngModel)]="parameter.key" name="key" required [matAutocomplete]="keys">
                    <mat-autocomplete #keys="matAutocomplete">
                        <mat-option *ngFor="let key of requiredKeys" [value]="key">{{key}}</mat-option>
                    </mat-autocomplete>
                </mat-form-field>
                <br>
                <mat-form-field>
                    <input matInput placeholder="Value" [(ngModel)]="parameter.value" name="value" required>
                </mat-form-field>
            </form>
        </mat-dialog-content>
        <mat-dialog-actions>
            <button mat-button (click)="dialogRef.close(undefined)">Cancel</button>
            <button mat-button [disabled]="!form.form.valid" (click)="dialogRef.close(parameter)">Apply</button>
        </mat-dialog-actions>`
})
export class EditParameterDialog {

    ruleComponent: RuleComponent;
    parameter: Parameter;

    requiredKeys: string[];

    constructor(public dialogRef: MatDialogRef<EditParameterDialog>,
                @Inject(MAT_DIALOG_DATA) public data: any,
                parameterService: ParameterService) {
        this.ruleComponent = data.ruleComponent;
        this.parameter = <Parameter> (data && data.parameter || {}); // update or create

        parameterService.getKeyByRule(this.ruleComponent.key).subscribe(keys => {
            let alreadyAssociatedKey: string[] = this.ruleComponent.parameters.map(p => p.key);
            this.requiredKeys = keys.filter(k => !alreadyAssociatedKey.includes(k));
        });
    }

}

@Component({
    selector: 'p62-parameter-delete',
    template: `
        <h2 mat-dialog-title>Delete</h2>
        <mat-dialog-content>
            Delete this parameter?
        </mat-dialog-content>
        <mat-dialog-actions>
            <button mat-button (click)="dialogRef.close(false)">Cancel</button>
            <button mat-button (click)="dialogRef.close(true)">Discard</button>
        </mat-dialog-actions>`
})
export class DeleteParameterDialog {

    constructor(public dialogRef: MatDialogRef<DeleteParameterDialog>) {
    }

}

@Component({
    selector: 'p62-parameter',
    template: `
        <div class="mat-elevation-z8" style="position: relative;">
            <mat-table [dataSource]="dataSource" style="margin-right: 50px;">
                <ng-container cdkColumnDef="key">
                    <mat-header-cell *cdkHeaderCellDef>Key</mat-header-cell>
                    <mat-cell *cdkCellDef="let parameter">{{parameter.key}}</mat-cell>
                </ng-container>
                <ng-container cdkColumnDef="value">
                    <mat-header-cell *cdkHeaderCellDef>Value</mat-header-cell>
                    <mat-cell *cdkCellDef="let parameter">{{parameter.value}}</mat-cell>
                </ng-container>
                <ng-container cdkColumnDef="actions">
                    <mat-header-cell *cdkHeaderCellDef></mat-header-cell>
                    <mat-cell *cdkCellDef="let parameter">
                        <div style="display: flex;"><!--flex: no line break-->
                            <button mat-icon-button (click)="openUpdateDialog(parameter)">
                                <mat-icon>mode_edit</mat-icon>
                            </button>
                            <button mat-icon-button (click)="openDeleteDialog(parameter)">
                                <mat-icon>delete</mat-icon>
                            </button>
                        </div>
                    </mat-cell>
                </ng-container>

                <mat-header-row *cdkHeaderRowDef="displayedColumns"></mat-header-row>
                <mat-row *cdkRowDef="let parameter; columns: displayedColumns;"></mat-row>
            </mat-table>

            <button (click)="openCreateDialog()" mat-mini-fab style="position: absolute; bottom: 0; margin-bottom: 5px; right: 0; margin-right: 5px;">
                <mat-icon>add</mat-icon>
            </button>
        </div>`,
    styles: [
            `.mat-table {
            overflow: auto;
        }`
    ]
})
export class ParameterComponent implements OnInit {

    @Input()
    ruleComponent: RuleComponent;

    // Table
    displayedColumns = ['key', 'value', 'actions'];
    dataSource: ObservableDataSource<Parameter> = new ObservableDataSource<Parameter>(new BehaviorSubject<Parameter[]>([]));

    constructor(private dialog: MatDialog,
                private parameterService: ParameterService) {
    }

    ngOnInit(): void {
        this.dataSource.observable.next(this.ruleComponent.parameters);
    }

    refresh(): void {
        this.parameterService.getByRuleComponent(this.ruleComponent).subscribe(p =>
            this.dataSource.observable.next(p)
        );
    }

    openCreateDialog(): void {
        let dialogConfig: MatDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = {ruleComponent: this.ruleComponent};
        let createDialog: MatDialogRef<EditParameterDialog> = this.dialog.open(EditParameterDialog, dialogConfig);
        createDialog.afterClosed().subscribe((createdParameter: Parameter) => {
            // Canceled
            if (createdParameter == null)
                return;

            createdParameter.ruleComponent = this.ruleComponent.id;
            this.parameterService.create(createdParameter).subscribe(x =>
                this.refresh()
            );
        });
    }

    openUpdateDialog(parameter: Parameter): void {
        let dialogConfig: MatDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = {
            ruleComponent: this.ruleComponent,
            parameter: Object.assign({}, parameter)
        };
        let createDialog: MatDialogRef<EditParameterDialog> = this.dialog.open(EditParameterDialog, dialogConfig);
        createDialog.afterClosed().subscribe((updatedParameter: Parameter) => {
            // Canceled
            if (updatedParameter == null)
                return;

            updatedParameter.ruleComponent = this.ruleComponent.id;
            this.parameterService.update(updatedParameter).subscribe(x =>
                this.refresh()
            );
        });
    }

    openDeleteDialog(parameter: Parameter): void {
        let deleteDialog: MatDialogRef<DeleteParameterDialog> = this.dialog.open(DeleteParameterDialog, this.getCommonDialogConfig());
        deleteDialog.afterClosed().subscribe((confirm: boolean) => {
            if (confirm)
                this.parameterService.delete(parameter).subscribe(x =>
                    this.refresh()
                );
        });
    }

    getCommonDialogConfig(): MatDialogConfig {
        return {disableClose: true};
    }

}
