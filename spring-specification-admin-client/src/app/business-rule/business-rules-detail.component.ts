import {Component} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

import {BusinessRule} from './business-rule';
import {BusinessRuleService} from './business-rule.service';

@Component({
    selector: 'p62-business-rule-detail',
    template: `
        <p62-rule-component *ngIf="businessRule" [ruleComponent]="businessRule.rootRuleComponent"></p62-rule-component>
    `
})
export class BusinessRuleDetailComponent {

    businessRule: BusinessRule;

    constructor(route: ActivatedRoute,
                private businessRuleService: BusinessRuleService) {
        route.params.subscribe((params: Params) =>
            this.businessRuleService.get(params['id']).subscribe(br =>
                this.businessRule = br
            )
        );
    }

}
