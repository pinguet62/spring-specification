import {Component, Inject, Input, OnInit} from "@angular/core";
import {MD_DIALOG_DATA, MdDialog, MdDialogConfig, MdDialogRef} from "@angular/material";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {ObservableDataSource} from "../simple-data-source";
import {Parameter} from "./parameter";
import {ParameterService} from "./parameter.service";
import {Rule} from "../rule/rule";

@Component({
    selector: 'p62-parameter-edit',
    template: `
        <h2 md-dialog-title>Parameter</h2>
        <md-dialog-content>
            <form #form="ngForm">
                <md-input-container>
                    <input mdInput placeholder="Key" [(ngModel)]="parameter.key" name="key" required>
                </md-input-container>
                <br>
                <md-input-container>
                    <input mdInput placeholder="Value" [(ngModel)]="parameter.value" name="value" required>
                </md-input-container>
                <br>
                <md-select placeholder="Type" [(ngModel)]="parameter.type" name="type" required style="width: 100%;">
                    <md-option *ngFor="let type of availableTypes" [value]="type">{{type}}</md-option>
                </md-select>
            </form>
        </md-dialog-content>
        <md-dialog-actions>
            <button md-button (click)="dialogRef.close(undefined)">Cancel</button>
            <button md-button [disabled]="!form.form.valid" (click)="dialogRef.close(parameter)">Apply</button>
        </md-dialog-actions>`
})
export class EditParameterDialog {
    parameter: Parameter;
    availableTypes: string[];

    constructor(
        protected dialogRef: MdDialogRef<EditParameterDialog>,
        @Inject(MD_DIALOG_DATA) public data: any,
        parameterService: ParameterService
    ) {
        this.parameter = <Parameter> (data && data.parameter || {}); // update or create
        parameterService.getSupportedTypes().subscribe(types =>
            this.availableTypes = types
        );
    }
}

@Component({
    selector: 'p62-parameter-create',
    template: `
        <h2 md-dialog-title>Delete</h2>
        <md-dialog-content>
            Delete this parameter?
        </md-dialog-content>
        <md-dialog-actions>
            <button md-button (click)="dialogRef.close(false)">Cancel</button>
            <button md-button (click)="dialogRef.close(true)">Discard</button>
        </md-dialog-actions>`
})
export class DeleteParameterDialog {
    constructor(protected dialogRef: MdDialogRef<EditParameterDialog>) {}
}

@Component({
    selector: 'p62-parameter',
    template: `
        <div class="example-container mat-elevation-z8" style="position: relative;">
            <md-table [dataSource]="dataSource" style="margin-right: 50px;">
                <ng-container cdkColumnDef="key">
                    <md-header-cell *cdkHeaderCellDef>Key</md-header-cell>
                    <md-cell *cdkCellDef="let parameter">{{parameter.key}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="value">
                    <md-header-cell *cdkHeaderCellDef>Value</md-header-cell>
                    <md-cell *cdkCellDef="let parameter">{{parameter.value}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="type">
                    <md-header-cell *cdkHeaderCellDef>Type</md-header-cell>
                    <md-cell *cdkCellDef="let parameter">{{parameter.type}}</md-cell>
                </ng-container>
                <ng-container cdkColumnDef="options">
                    <md-header-cell *cdkHeaderCellDef></md-header-cell>
                    <md-cell *cdkCellDef="let parameter">
                        <div style="display: flex;"><!--flex: no line break-->
                            <button md-icon-button (click)="openUpdateDialog(parameter)"><md-icon>mode_edit</md-icon></button>
                            <button md-icon-button (click)="deleteParameter(parameter)"><md-icon>delete</md-icon></button>
                        </div>
                    </md-cell>
                </ng-container>
                
                <md-header-row *cdkHeaderRowDef="displayedColumns"></md-header-row>
                <md-row *cdkRowDef="let parameter; columns: displayedColumns;"></md-row>
            </md-table>

            <button (click)="openCreateDialog()" md-mini-fab style="position: absolute; bottom: 0; margin-bottom: 5px; right: 0; margin-right: 5px;"><md-icon>add</md-icon></button>
        </div>`,
    styles: [
        `.example-container {
            display: flex;
            flex-direction: column;
            max-height: 500px;
            min-width: 300px;
        }
        .mat-table {
            overflow: auto;
        }`
    ]
})
export class ParameterComponent implements OnInit {

    @Input()
    rule: Rule;

    // Table
    displayedColumns = ['key', 'value', 'type', 'options'];
    dataSource: ObservableDataSource<Parameter> = new ObservableDataSource<Parameter>(new BehaviorSubject<Parameter[]>([]));

    constructor(
        private dialog: MdDialog,
        private parameterService: ParameterService
    ) {}

    ngOnInit(): void {
        this.dataSource.observable.next(this.rule.parameters);
    }

    refresh(): void {
        this.parameterService.getByRule(this.rule).subscribe(p =>
            this.dataSource.observable.next(p)
        );
    }

    openCreateDialog(): void {
        let createDialog: MdDialogRef<EditParameterDialog> = this.dialog.open(EditParameterDialog, this.getCommonDialogConfig());
        createDialog.afterClosed().subscribe((createdParameter: Parameter) => {
            // Canceled
            if (createdParameter == null)
                return;

            createdParameter.rule = this.rule.id;
            this.parameterService.create(createdParameter).subscribe(x =>
                this.refresh()
            );
        });
    }

    openUpdateDialog(parameter: Parameter): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = { parameter: Object.assign({}, parameter) };
        let createDialog: MdDialogRef<EditParameterDialog> = this.dialog.open(EditParameterDialog, dialogConfig);
        createDialog.afterClosed().subscribe((updatedParameter: Parameter) => {
            // Canceled
            if (updatedParameter == null)
                return;

            updatedParameter.rule = this.rule.id;
            this.parameterService.update(updatedParameter).subscribe(x =>
                this.refresh()
            );
        });
    }

    deleteParameter(parameter: Parameter): void {
        let createDialog: MdDialogRef<DeleteParameterDialog> = this.dialog.open(DeleteParameterDialog, this.getCommonDialogConfig());
        createDialog.afterClosed().subscribe((confirm: boolean) => {
            if (confirm)
                this.parameterService.delete(parameter).subscribe(x =>
                    this.refresh()
                );
        });
    }

    getCommonDialogConfig(): MdDialogConfig {
        return { disableClose: true };
    };
}