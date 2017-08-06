import {Component, Inject, Input, OnInit} from "@angular/core";
import {MD_DIALOG_DATA, MdDialog, MdDialogConfig, MdDialogRef} from "@angular/material";
import {NodeMovedEvent, TreeNode} from "../tree/tree-node";
import {RuleService} from "./rule.service";
import {convert, RuleDataTreeNode} from "./rule-tree-node";
import {Rule} from "./rule";
import {RuleInformation} from "./rule-information";
import {RuleCatalogService} from "./rule-information.service";

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
                    <input mdInput placeholder="Description" [(ngModel)]="rule.description" name="description">
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
        ruleInformationService: RuleCatalogService
    ) {
        this.rule = <Rule> (data && data.rule || {}); // update or create
        ruleInformationService.getAvailableKeys().subscribe(infos =>
            this.availableKeys = infos
        );
    }
}

@Component({
    selector: 'p62-rule-settings',
    template: `
        <h2 md-dialog-title>Settings</h2>
        <md-dialog-content>
            <h3>Parameters</h3>
            <p62-parameter [rule]="rule"></p62-parameter>
            <br><!--fix-->
        </md-dialog-content>`
})
export class SettingsRuleDialog {
    rule: Rule;

    constructor(
        protected dialogRef: MdDialogRef<EditRuleDialog>,
        @Inject(MD_DIALOG_DATA) public data: any
    ) {
        this.rule = <Rule> data.rule;
    }
}

@Component({
    selector: 'p62-rule',
    template: `
        <p62-tree [value]="treeNode" (nodeMoved)="ruleMoved($event)">
            <ng-template #label let-node>
                <b>#{{node.data.rule.id}}</b> - 
                <span>{{node.data.rule.name}}</span> - 
                <small>{{node.data.rule.key}}</small>
            </ng-template>
            <ng-template #options let-node>
                <div style="display: inline-flex;">
                    <button md-icon-button *ngIf="node.parent != null && !node.data.first" (click)="upwardIndexRule(node)"><md-icon>arrow_upward</md-icon></button>
                    <button md-icon-button *ngIf="node.parent != null && !node.data.last" (click)="downwardIndexRule(node)"><md-icon>arrow_downward</md-icon></button>
                    
                    <button md-icon-button (click)="openUpdateDialog(node.data.rule)"><md-icon>mode_edit</md-icon></button>
                    
                    <!-- Customer: accept parameters -->
                    <div *ngIf="!['fr.pinguet62.springruleengine.core.api.AndRule', 'fr.pinguet62.springruleengine.core.api.OrRule', 'fr.pinguet62.springruleengine.core.api.NotRule'].includes(node.data.rule.key)">
                        <button md-icon-button (click)="openSettingsDialog(node.data.rule)"><md-icon>settings</md-icon></button>
                    </div>
                    <!-- Composite: accept sub-rules -->
                    <div *ngIf="['fr.pinguet62.springruleengine.core.api.AndRule', 'fr.pinguet62.springruleengine.core.api.OrRule', 'fr.pinguet62.springruleengine.core.api.NotRule'].includes(node.data.rule.key)">
                        <button md-icon-button (click)="openCreateDialog(node.data.rule)"><md-icon>add</md-icon></button>
                    </div>
                </div>
            </ng-template>
        </p62-tree>`,
    styles: [
        '::ng-deep .ui-tree .ui-treenode-label { vertical-align: top !important; }', // vertical align because of element with different height
        '::ng-deep .ui-tree { width: 100%; }', // all screen width
        '::ng-deep .ui-tree .ui-treenode-children { padding: 0 0 0 5em; }' // sub-nodes indentation size
    ]
})
export class RuleComponent implements OnInit {
    @Input()
    rule: Rule;

    treeNode: TreeNode<RuleDataTreeNode>;

    constructor(
        private dialog: MdDialog,
        private ruleService: RuleService
    ) {}

    ngOnInit(): void {
        this.refresh();
    }

    refresh(): void {
        this.ruleService.get(this.rule.id).subscribe(r =>
            this.treeNode = convert(r)
        );
    }

    ruleMoved(event: NodeMovedEvent<RuleDataTreeNode>): void {
        let rule: Rule = {
            id: event.node.data.rule.id,
            parent: event.parent.data.rule.id,
            index: event.index
        };
        this.ruleService.update(rule).subscribe( x =>
            this.refresh()
        );
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

    openSettingsDialog(rule: Rule): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = { rule: Object.assign({}, rule) };
        dialogConfig.disableClose = false; // override
        //dialogConfig.width = '800px'; // custom
        let createDialog: MdDialogRef<SettingsRuleDialog> = this.dialog.open(SettingsRuleDialog, dialogConfig);
    }

    getCommonDialogConfig(): MdDialogConfig {
        return { disableClose: true };
    }
}