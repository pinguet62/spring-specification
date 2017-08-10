import {Component} from '@angular/core';
import {RuleService} from './rule.service';
import {Rule} from './rule';

@Component({
    selector: 'app-root',
    template: `
        <div *ngFor="let rule of rules" class="mat-elevation-z8" style="width: 800px; margin: 50px;">
            <p62-rule [rule]="rule"></p62-rule>
        </div>`
})
export class RulesComponent {

    rules: Rule[];

    constructor(private ruleService: RuleService) {
        this.ruleService.getAllRoots().subscribe(rs =>
            this.rules = rs
        );
    }

}
