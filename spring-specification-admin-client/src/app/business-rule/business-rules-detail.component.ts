import {Component} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';

import {BusinessRule} from './business-rule';
import {BusinessRuleService} from './business-rule.service';

@Component({
    selector: 'app-business-rule-detail',
    template: `
        <app-rule-component *ngIf="businessRule" [businessRule]="businessRule" [ruleComponent]="businessRule.rootRuleComponent"></app-rule-component>
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
