import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {MD_DIALOG_DATA, MdDialog, MdDialogConfig, MdDialogRef} from '@angular/material';
import {NodeMovedEvent, TreeNode} from '../tree/tree-node';
import {RuleService} from './rule.service';
import {convert, RuleDataTreeNode} from './rule-tree-node';
import {Rule} from './rule';
import {RuleInformation} from './rule-information';
import {RuleInformationService} from './rule-information.service';

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

    constructor(public dialogRef: MdDialogRef<EditRuleDialog>,
                @Inject(MD_DIALOG_DATA) public data: any,
                ruleInformationService: RuleInformationService) {
        this.rule = <Rule> (data && data.rule || {}); // update or create
        this.availableKeys = ruleInformationService.getAll();
    }

}

@Component({
    selector: 'p62-rule-delete',
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
export class DeleteRuleDialog {

    constructor(public dialogRef: MdDialogRef<DeleteRuleDialog>) {
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

    constructor(protected dialogRef: MdDialogRef<EditRuleDialog>,
                @Inject(MD_DIALOG_DATA) public data: any) {
        this.rule = <Rule> data.rule;
    }

}

@Component({
    selector: 'p62-rule',
    template: `
        <p62-tree [value]="treeNode" (nodeMoved)="ruleMoved($event)">
            <ng-template #label let-node>
                <code>#{{node.data.rule.id}}</code> - <!--TODO test-->
                <b>{{ruleInformationService.getFromKey(node.data.rule.key)?.name}}</b>
                <small>{{node.data.rule.description}}</small>
            </ng-template>
            <ng-template #options let-node>
                <div style="display: inline-flex;">
                    <!-- Composite: accept sub-rules -->
                    <div style="width: 40px;">
                        <ng-template
                                [ngIf]="['fr.pinguet62.springruleengine.core.api.AndRule', 'fr.pinguet62.springruleengine.core.api.OrRule', 'fr.pinguet62.springruleengine.core.api.NotRule'].includes(node.data.rule.key)">
                            <button md-icon-button (click)="openCreateDialog(node.data.rule)">
                                <md-icon>add</md-icon>
                            </button>
                        </ng-template>
                    </div>
                    <button md-icon-button (click)="openUpdateDialog(node.data.rule)">
                        <md-icon>mode_edit</md-icon>
                    </button>
                    <!-- Custom: accept parameters -->
                    <div style="width: 40px;">
                        <ng-template
                                [ngIf]="!['fr.pinguet62.springruleengine.core.api.AndRule', 'fr.pinguet62.springruleengine.core.api.OrRule', 'fr.pinguet62.springruleengine.core.api.NotRule'].includes(node.data.rule.key)">
                            <button md-icon-button (click)="openSettingsDialog(node.data.rule)">
                                <md-icon>settings</md-icon>
                            </button>
                        </ng-template>
                    </div>
                    <button md-icon-button (click)="openDeleteDialog(node.data.rule)">
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
export class RuleComponent implements OnInit {

    private ruleId: number;
    treeNode: TreeNode<RuleDataTreeNode>;

    constructor(route: ActivatedRoute,
                private dialog: MdDialog,
                private ruleService: RuleService,
                private ruleInformationService: RuleInformationService) {
        route.params.subscribe((params: Params) => {
            this.ruleId = +params['id'];
            this.refresh();
        });
    }

    ngOnInit(): void {
        this.refresh();
    }

    refresh(): void {
        this.ruleService.get(this.ruleId).subscribe(r =>
            this.treeNode = convert(r)
        );
    }

    ruleMoved(event: NodeMovedEvent<RuleDataTreeNode>): void {
        let rule: Rule = {
            id: event.node.data.rule.id,
            parent: event.parent.data.rule.id,
            index: event.index
        };
        this.ruleService.update(rule).subscribe(x =>
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
        dialogConfig.data = {rule: Object.assign({}, rule)};
        let updateDialog: MdDialogRef<EditRuleDialog> = this.dialog.open(EditRuleDialog, dialogConfig);
        updateDialog.afterClosed().subscribe((updatedRule: Rule) => {
            // Canceled
            if (updatedRule == null)
                return;

            this.ruleService.update(updatedRule).subscribe(x =>
                this.refresh()
            );
        });
    }

    openSettingsDialog(rule: Rule): void {
        let dialogConfig: MdDialogConfig = this.getCommonDialogConfig();
        dialogConfig.data = {rule: Object.assign({}, rule)};
        dialogConfig.disableClose = false; // override
        //dialogConfig.width = '800px'; // custom
        let settingsDialog: MdDialogRef<SettingsRuleDialog> = this.dialog.open(SettingsRuleDialog, dialogConfig);
    }

    openDeleteDialog(rule: Rule): void {
        let deleteDialog: MdDialogRef<DeleteRuleDialog> = this.dialog.open(DeleteRuleDialog, this.getCommonDialogConfig());
        deleteDialog.afterClosed().subscribe((confirm: boolean) => {
            if (confirm)
                this.ruleService.delete(rule).subscribe(x =>
                    this.refresh()
                );
        });
    }

    getCommonDialogConfig(): MdDialogConfig {
        return {disableClose: true};
    }

}
