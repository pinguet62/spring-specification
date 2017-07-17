import {Component, Input, OnInit} from '@angular/core';
import {SelectItem} from "primeng/primeng";
import {Rule} from "./Rule";
import {Parameter} from "./parameter";
import {ParameterService} from "./parameter.service";

@Component({
    selector: 'p62-parameter',
    template: `
        <p-dataTable [value]="parameters" selectionMode="single" [(selection)]="selectedParameter" (onRowSelect)="onRowSelect($event)" [style]="{'width':'725px'}">
            <p-header>Parameters</p-header>
            <p-column header="Key">
                <ng-template let-parameter="rowData" pTemplate="body">
                    <p-inplace closable="closable">
                        <span pInplaceDisplay>{{parameter.key}}</span>
                        <span pInplaceContent>
                            <input type="text" pInputText value="{{parameter.key}}">
                        </span>
                    </p-inplace>
                </ng-template>
            </p-column>
            <p-column header="Value">
                <ng-template let-parameter="rowData" pTemplate="body">
                    <p-inplace closable="closable">
                        <span pInplaceDisplay>{{parameter.value}}</span>
                        <span pInplaceContent>
                            <input type="text" pInputText value="{{parameter.value}}">
                        </span>
                    </p-inplace>
                </ng-template>
            </p-column>
            <p-column header="Type">
                <ng-template let-parameter="rowData" pTemplate="body">
                    {{parameter.type}}
                </ng-template>
            </p-column>
            <p-footer>
                <div class="ui-helper-clearfix" style="width: 100%">
                    <button type="button" pButton (click)="showDialogToAdd()" icon="fa-plus" style="float: right"></button>
                </div>
            </p-footer>
        </p-dataTable>

        <p-dialog header="Parameters" [(visible)]="displayDialog" [modal]="true">
            <div *ngIf="selectedParameter" class="ui-grid ui-grid-responsive ui-fluid">
                <div class="ui-grid-row">
                    <div class="ui-grid-col-4"><label for="key">Key</label></div>
                    <div class="ui-grid-col-8"><input pInputText id="key" [(ngModel)]="selectedParameter.key" /></div>
                </div>
                <div class="ui-grid-row">
                    <div class="ui-grid-col-4"><label for="value">Value</label></div>
                    <div class="ui-grid-col-8"><input pInputText id="value" [(ngModel)]="selectedParameter.value" /></div>
                </div>
                <div class="ui-grid-row">
                    <div class="ui-grid-col-4"><label for="type">Type</label></div>
                    <div class="ui-grid-col-8"><p-dropdown id="type" [options]="availableTypes" [(ngModel)]="selectedParameter.type" [style]="{'width':'100%'}"></p-dropdown></div>
                </div>
            </div>
            <p-footer>
                <div class="ui-dialog-buttonpane ui-helper-clearfix">
                    <button pButton (click)="cancel()" label="Cancel" icon="fa-close"></button>
                    <button pButton *ngIf="!newParameter" (click)="delete()" label="Delete" icon="fa-check"></button>
                    <button pButton *ngIf="!newParameter" (click)="update()" label="Update" icon="fa-check"></button>
                    <button pButton *ngIf="newParameter" (click)="create()" label="Create" icon="fa-check"></button>
                </div>
            </p-footer>
        </p-dialog>
    `
})
export class ParameterComponent implements OnInit {
    @Input()
    rule: Rule;

    parameters: Parameter[];

    // ----- For dialog -----
    displayDialog: boolean;
    selectedParameter: Parameter;
    newParameter: boolean;
    availableTypes: SelectItem[];

    constructor(private parameterService: ParameterService) {
        this.parameterService.getSupportedTypes().subscribe(types => this.availableTypes = types.map(type => <SelectItem> {label: type, value: type}));
    }

    ngOnInit(): void {
        this.parameters = this.rule.parameters;
    }

    refresh(): void {
        this.parameterService.getByRule(this.rule).subscribe(p =>  {
            this.parameters = p;
        });
    }

    onRowSelect(event: any): void {
        this.selectedParameter = Object.assign({}, event.data);
        this.selectedParameter.rule = this.rule.id;

        this.newParameter = false;
        this.displayDialog = true;
    }

    showDialogToAdd(): void {
        this.selectedParameter = {};
        this.selectedParameter.rule = this.rule.id;

        this.newParameter = true;
        this.displayDialog = true;
    }

    create(): void {
        this.parameterService.create(this.selectedParameter).subscribe(x => this.refresh());
        this.displayDialog = false;
        this.selectedParameter = null;
    }
    update(): void {
        this.parameterService.update(this.selectedParameter).subscribe(x => this.refresh());
        this.displayDialog = false;
        this.selectedParameter = null;
    }
    delete(): void {
        this.parameterService.delete(this.selectedParameter).subscribe(x => this.refresh());
        this.displayDialog = false;
        this.selectedParameter = null;
    }
    cancel(): void {
        this.displayDialog = false;
        this.selectedParameter = null;
    }
}