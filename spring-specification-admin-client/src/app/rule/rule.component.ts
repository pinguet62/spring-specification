import {Component, Inject} from '@angular/core';
import {MD_DIALOG_DATA, MdDialog, MdDialogConfig, MdDialogRef} from "@angular/material";
import {SelectItem, TreeNode} from "primeng/primeng";
import {RuleService} from "./rule.service";
import {Rule} from "./rule";
import {RuleInformation} from "./rule-information";

@Component({
    selector: 'p62-rule-update',
    template: `
        <h2 md-dialog-title>Rule</h2>
        <md-dialog-content>
            <form #form="ngForm">
                <md-select placeholder="Key" [(ngModel)]="rule.key" name="key" required style="width: 100%;">
                    <md-option *ngFor="let ruleInformation of availableKeys" [value]="ruleInformation.key">{{ruleInformation.name}}</md-option>
                </md-select>
                <br>
                <md-input-container>
                    <input mdInput placeholder="Description" [(ngModel)]="rule.description" name="description" required>
                </md-input-container>
            </form>
        </md-dialog-content>
        <md-dialog-actions>
            <button md-button (click)="dialogRef.close(undefined)">Cancel</button>
            <button md-button [disabled]="!form.form.valid" (click)="dialogRef.close(rule)">Apply</button>
        </md-dialog-actions>`
})
export class EditRuleDialog {
    rule: Rule;
    availableKeys: RuleInformation[];

    constructor(
        protected dialogRef: MdDialogRef<EditRuleDialog>,
        @Inject(MD_DIALOG_DATA) public data: any,
        ruleService: RuleService
    ) {
        this.rule = <Rule> (data && data.rule || {}); // update or create
        ruleService.getAvailableKeys().subscribe(infos =>
            this.availableKeys = infos
        );
    }
}

@Component({
    selector: 'p62-rule',
    template: `
        <p-tree [value]="rules">
            <ng-template let-node pTemplate="default">
                <div style="display: inline-flex;">
                    <span>{{node.label}}</span>
                    
                    <button md-icon-button><md-icon>arrow_upward</md-icon></button>
                    <button md-icon-button><md-icon>arrow_downward</md-icon></button>
                    <button md-icon-button (click)="openUpdateDialog(node.data)"><md-icon>mode_edit</md-icon></button>
                    
                    <!-- Composite: accept sub-rules -->
                    <div *ngIf="['andRule', 'orRule', 'notRule'].includes(node.label)">
                        <button md-icon-button (click)="openCreateDialog(node.data)"><md-icon>add</md-icon></button>
                    </div>
                    
                    <!-- Custom: accept parameters -->
                    <div *ngIf="node.label!=='and' && node.label!=='orRule'">
                        <!--<p62-parameter [rule]="node.data"></p62-parameter>-->
                    </div>
                </div>
            </ng-template>
        </p-tree>
        
        <p-dialog header="New rule" [(visible)]="displayDialog" [modal]="true">
            <div *ngIf="selectedRule" class="ui-grid ui-grid-responsive ui-fluid">
                <div class="ui-grid-row">
                    <div class="ui-grid-col-4"><label for="key">Key</label></div>
                    <div class="ui-grid-col-8"><p-dropdown id="key" [options]="availableKeys" [(ngModel)]="selectedRule.key" [style]="{'width':'100%'}"></p-dropdown></div>
                </div>
            </div>
            <p-footer>
                <div class="ui-dialog-buttonpane ui-helper-clearfix">
                    <button pButton (click)="cancel()" label="Cancel" icon="fa-close"></button>
                    <button pButton (click)="createAndAddNewRule()" label="Validate" icon="fa-check"></button>
                </div>
            </p-footer>
        </p-dialog>
    `, styles: [
        '::ng-deep .ui-tree .ui-treenode-label { vertical-align: top !important; }', // vertical align because of element with different height
        '::ng-deep .ui-tree { width: 100%; }', // all screen width
        '::ng-deep .ui-tree .ui-treenode-children { padding: 0 0 0 5em; }' // sub-nodes indentation size
    ]
})
export class RuleComponent {
    rules: TreeNode[];

    // ----- For dialog -----
    displayDialog: boolean;
    selectedRule: Rule;
    availableKeys: SelectItem[];

    constructor(
        private dialog: MdDialog,
        private ruleService: RuleService
    ) {
        ruleService.getAvailableKeys().subscribe(infos =>
            this.availableKeys = infos.map(info => <SelectItem> {label: info.name, value: info.key})
        );
        this.refresh();
    }

    refresh(): void {
        this.ruleService.getAny().subscribe(r =>
            this.rules = [RuleComponent.ruleToNode(r)]
        );
    }

    static ruleToNode(rule: Rule): TreeNode {
        return {
            expanded: true, // TODO test
            icon: 'fa-folder-open',
            label: rule.key,
            data: rule,
            children: rule.components == null ? null : rule.components.map(RuleComponent.ruleToNode)
        };
    }

    showDialogToAdd(rule: Rule): void {
        this.selectedRule = <Rule> {};
        this.selectedRule.parent = rule.id;

        this.displayDialog = true;
    }

    createAndAddNewRule(): void {
        this.ruleService.create(this.selectedRule).subscribe(x =>
            this.refresh()
        );
        this.displayDialog = false;
        this.selectedRule = null;
    }

    cancel(): void {
        this.displayDialog = false;
        this.selectedRule = null;
    }

    openCreateDialog(parentRule: Rule): void {
        let createDialog: MdDialogRef<EditRuleDialog> = this.dialog.open(EditRuleDialog, this.getCommonDialogConfig());
        createDialog.afterClosed().subscribe((createdRule: Rule) => {
            // Canceled
            if (createdRule == null)
                return;

            createdRule.parent = parentRule.id;
            this.ruleService.create(createdRule).subscribe(x =>
                this.refresh()
            );
        });
    }

    openUpdateDialog(rule: Rule): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = { rule: Object.assign({}, rule) };
        let createDialog: MdDialogRef<EditRuleDialog> = this.dialog.open(EditRuleDialog, dialogConfig);
        createDialog.afterClosed().subscribe((updatedRule: Rule) => {
            // Canceled
            if (updatedRule == null)
                return;

            updatedRule.parent = rule.id;
            this.ruleService.update(updatedRule).subscribe(x =>
                this.refresh()
            );
        });
    }

    getCommonDialogConfig(): MdDialogConfig {
        return { disableClose: true };
    };
}