import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {RuleService} from '../rule/rule.service';
import {RuleComponentService} from '../rule-component/rule-component.service';
import {RuleComponent} from '../rule-component/rule-component';
import {Parameter} from './parameter';

@Injectable()
export class ParameterService {

    constructor(protected http: HttpClient) {
    }

    getByRuleComponent(ruleComponent: RuleComponent): Observable<Parameter[]> {
        const url: string = RuleComponentService.resourceUrl + '/' + ruleComponent.id + '/parameter';
        return this.http.get<Parameter[]>(url);
    }

    getKeyByRule(ruleKey: string): Observable<string[]> {
        const url: string = RuleService.resourceUrl + '/' + ruleKey + '/parameter/key';
        return this.http.get<string[]>(url);
    }

    create(ruleComponent: RuleComponent, parameter: Parameter): Observable<Parameter> {
        const url: string = RuleComponentService.resourceUrl + '/' + ruleComponent.id + '/parameter';
        const body: string = JSON.stringify(parameter);
        return this.http.put<Parameter>(url, body);
    }

    update(ruleComponent: RuleComponent, parameter: Parameter): Observable<Parameter> {
        const url: string = RuleComponentService.resourceUrl + '/' + ruleComponent.id + '/parameter' + '/' + parameter.id;
        const body: string = JSON.stringify(parameter);
        return this.http.post<Parameter>(url, body);
    }

    delete(ruleComponent: RuleComponent, parameter: Parameter): Observable<Parameter> {
        const url: string = RuleComponentService.resourceUrl + '/' + ruleComponent.id + '/parameter' + '/' + parameter.id;
        return this.http.delete<Parameter>(url);
    }

}
