import {Component} from '@angular/core';
import {SelectItem, TreeNode} from "primeng/primeng";
import {RuleService} from "./rule.service";
import {Rule} from "./Rule";

@Component({
    selector: 'p62-rule',
    template: `
        <p-tree [value]="rules">
            <ng-template let-node pTemplate="default">
                <div style="display: inline-flex;">
                    <span>{{node.label}}</span>
                    
                    <!-- Composite: accept sub-rules -->
                    <div *ngIf="node.label==='and' || node.label==='or'">
                        <button pButton (click)="showDialogToAdd(node.data)" icon="fa-plus"></button>
                    </div>
                    
                    <!-- Custom: accept parameters -->
                    <div *ngIf="node.label!=='and' && node.label!=='or'">
                        <p62-parameter [rule]="node.data"></p62-parameter>
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
        '::ng-deep .ui-tree .ui-treenode-label { vertical-align: top !important; }',
        '::ng-deep .ui-tree { width: 100%; }'
    ]
})
export class RuleComponent {
    rules: TreeNode[];

    // ----- For dialog -----
    displayDialog: boolean;
    selectedRule: Rule;
    availableKeys: SelectItem[];

    constructor(private ruleService: RuleService) {
        ruleService.getAvailableKeys().subscribe(infos => this.availableKeys = infos.map(info => <SelectItem> {label: info.name, value: info.key}));
        this.refresh();
    }

    refresh(): void {
        this.ruleService.getAny().subscribe(r => {
            this.rules = [RuleComponent.ruleToNode(r)];
        });
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
        this.ruleService.create(this.selectedRule).subscribe(x => this.refresh());
        this.displayDialog = false;
        this.selectedRule = null;
    }

    cancel(): void {
        this.displayDialog = false;
        this.selectedRule = null;
    }
}