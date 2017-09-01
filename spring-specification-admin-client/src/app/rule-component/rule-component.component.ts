import {Component, Inject, Input, OnInit} from '@angular/core';
import {MD_DIALOG_DATA, MdDialog, MdDialogConfig, MdDialogRef} from '@angular/material';

import {NodeMovedEvent, TreeNode} from '../tree/tree-node';
import {RuleComponentService} from './rule-component.service';
import {convert, RuleComponentDataTreeNode} from './rule-component-tree-node';
import {RuleComponent} from './rule-component';
import {BusinessRule} from '../business-rule/business-rule';
import {Rule} from '../rule/rule';
import {RuleService} from '../rule/rule.service';

@Component({
    selector: 'p62-rule-component-update',
    template: `
        <h2 md-dialog-title>Rule</h2>
        <md-dialog-content>
            <form #form="ngForm">
                <md-select placeholder="Key" [(ngModel)]="ruleComponent.key" name="key" required style="width: 100%;">
                    <md-option *ngFor="let rule of availableKeys" [value]="rule.key">{{rule.name}}</md-option>
                </md-select>
                <br>
                <md-input-container>
                    <input mdInput placeholder="Description" [(ngModel)]="ruleComponent.description" name="description">
                </md-input-container>
            </form>
        </md-dialog-content>
        <md-dialog-actions>
            <button md-button (click)="dialogRef.close(undefined)">Cancel</button>
            <button md-button [disabled]="!form.form.valid" (click)="dialogRef.close(ruleComponent)">Apply</button>
        </md-dialog-actions>`
})
export class EditRuleComponentDialog {

    ruleComponent: RuleComponent;
    availableKeys: Rule[];

    constructor(public dialogRef: MdDialogRef<EditRuleComponentDialog>,
                @Inject(MD_DIALOG_DATA) public data: any,
                ruleService: RuleService) {
        // Dialog data
        let businessRuleArgumentType: string = data.businessRuleArgumentType;
        this.ruleComponent = <RuleComponent> (data && data.ruleComponent || {}); // update or create

        ruleService.getAssociableRules(businessRuleArgumentType).subscribe(rs =>
            this.availableKeys = rs
        );
    }

}

@Component({
    selector: 'p62-rule-component-delete',
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
export class DeleteRuleComponentDialog {

    constructor(public dialogRef: MdDialogRef<DeleteRuleComponentDialog>) {
    }

}

@Component({
    selector: 'p62-rule-component-settings',
    template: `
        <h2 md-dialog-title>Settings</h2>
        <md-dialog-content>
            <h3>Parameters</h3>
            <p62-parameter [ruleComponent]="ruleComponent"></p62-parameter>
            <br><!--fix-->
        </md-dialog-content>`
})
export class SettingsRuleComponentDialog {

    ruleComponent: RuleComponent;

    constructor(protected dialogRef: MdDialogRef<SettingsRuleComponentDialog>,
                @Inject(MD_DIALOG_DATA) public data: any) {
        this.ruleComponent = <RuleComponent> data;
    }

}

@Component({
    selector: 'p62-rule-component',
    template: `
        <p62-tree [value]="treeNode" (nodeMoved)="ruleMoved($event)">
            <ng-template #label let-node>
                <code>#{{node.data.ruleComponent.id}}</code> - <!--TODO test-->
                <b>{{ruleService.getFromKey(node.data.ruleComponent.key)?.name}}</b>
                <small>{{node.data.ruleComponent.description}}</small>
            </ng-template>
            <ng-template #options let-node>
                <div style="display: inline-flex;">
                    <!-- Composite: accept sub-rules -->
                    <div style="width: 40px;">
                        <ng-template
                                [ngIf]="['fr.pinguet62.springruleengine.core.api.AndRule', 'fr.pinguet62.springruleengine.core.api.OrRule', 'fr.pinguet62.springruleengine.core.api.NotRule'].includes(node.data.ruleComponent.key)">
                            <button md-icon-button (click)="openCreateDialog(node.data.ruleComponent)">
                                <md-icon>add</md-icon>
                            </button>
                        </ng-template>
                    </div>
                    <button md-icon-button (click)="openUpdateDialog(node.data.ruleComponent)">
                        <md-icon>mode_edit</md-icon>
                    </button>
                    <!-- Custom: accept parameters -->
                    <div style="width: 40px;">
                        <ng-template
                                [ngIf]="!['fr.pinguet62.springruleengine.core.api.AndRule', 'fr.pinguet62.springruleengine.core.api.OrRule', 'fr.pinguet62.springruleengine.core.api.NotRule'].includes(node.data.ruleComponent.key)">
                            <button md-icon-button (click)="openSettingsDialog(node.data.ruleComponent)">
                                <md-icon>settings</md-icon>
                            </button>
                        </ng-template>
                    </div>
                    <button md-icon-button (click)="openDeleteDialog(node.data.ruleComponent)">
                        <md-icon>delete</md-icon>
                    </button>
                </div>
            </ng-template>
        </p62-tree>`,
    styles: [
        '::ng-deep .ui-tree .ui-treenode-label { vertical-align: top !important; }', // vertical align because of element with different height
        '::ng-deep .ui-tree { width: 100%; }', // all screen width
        '::ng-deep .ui-tree .ui-treenode-children { padding: 0 0 0 5em; }' // sub-nodes indentation size
    ]
})
export class RuleComponentComponent implements OnInit {

    @Input()
    businessRule: BusinessRule;

    @Input()
    ruleComponent: RuleComponent;

    treeNode: TreeNode<RuleComponentDataTreeNode>;

    constructor(private dialog: MdDialog,
                private ruleComponentService: RuleComponentService,
                public ruleService: RuleService) {
    }

    ngOnInit(): void {
        this.refresh();
    }

    refresh(): void {
        this.ruleComponentService.get(this.ruleComponent.id).subscribe(r =>
            this.treeNode = convert(r)
        );
    }

    ruleMoved(event: NodeMovedEvent<RuleComponentDataTreeNode>): void {
        let ruleComponent: RuleComponent = {
            id: event.node.data.ruleComponent.id,
            parent: event.parent.data.ruleComponent.id,
            index: event.index
        };
        this.ruleComponentService.update(ruleComponent).subscribe(x =>
            this.refresh()
        );
    }

    openCreateDialog(parentRuleComponent: RuleComponent): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = {
            businessRuleArgumentType: this.businessRule.argumentType,
            ruleComponent: null
        };
        let createDialog: MdDialogRef<EditRuleComponentDialog> = this.dialog.open(EditRuleComponentDialog, dialogConfig);
        createDialog.afterClosed().subscribe((createdRuleComponent: RuleComponent) => {
            // Canceled
            if (createdRuleComponent == null)
                return;

            createdRuleComponent.parent = parentRuleComponent.id;
            this.ruleComponentService.create(createdRuleComponent).subscribe(x =>
                this.refresh()
            );
        });
    }

    openUpdateDialog(ruleComponent: RuleComponent): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = {
            businessRuleArgumentType: this.businessRule.argumentType,
            ruleComponent: Object.assign({}, ruleComponent)
        };
        let updateDialog: MdDialogRef<EditRuleComponentDialog> = this.dialog.open(EditRuleComponentDialog, dialogConfig);
        updateDialog.afterClosed().subscribe((updatedRule: RuleComponent) => {
            // Canceled
            if (updatedRule == null)
                return;

            this.ruleComponentService.update(updatedRule).subscribe(x =>
                this.refresh()
            );
        });
    }

    openSettingsDialog(ruleComponent: RuleComponent): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = Object.assign({}, ruleComponent);
        dialogConfig.disableClose = false; // override
        //dialogConfig.width = '800px'; // custom
        let settingsDialog: MdDialogRef<SettingsRuleComponentDialog> = this.dialog.open(SettingsRuleComponentDialog, dialogConfig);
    }

    openDeleteDialog(ruleComponent: RuleComponent): void {
        let deleteDialog: MdDialogRef<DeleteRuleComponentDialog> = this.dialog.open(DeleteRuleComponentDialog, this.getCommonDialogConfig());
        deleteDialog.afterClosed().subscribe((confirm: boolean) => {
            if (confirm)
                this.ruleComponentService.delete(ruleComponent).subscribe(x =>
                    this.refresh()
                );
        });
    }

    getCommonDialogConfig(): MdDialogConfig {
        return {disableClose: true};
    }

}
