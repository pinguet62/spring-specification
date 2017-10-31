import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../../environments/environment';
import {RuleComponent} from '../rule-component/rule-component';
import {Parameter} from './parameter';

@Injectable()
export class ParameterService {

    private static readonly resourceUrl: string = environment.apiUrl + '/parameter';

    constructor(protected http: HttpClient) {
    }

    getByRuleComponent(ruleComponent: RuleComponent): Observable<Parameter[]> {
        let url: string = ParameterService.resourceUrl + '?ruleComponent=' + ruleComponent.id;
        return this.http.get<Parameter[]>(url);
    }

    getKeyByRule(ruleKey: string): Observable<string[]> {
        let url: string = ParameterService.resourceUrl + '/key/' + ruleKey;
        return this.http.get<string[]>(url);
    }

    create(parameter: Parameter): Observable<Parameter> {
        let url: string = ParameterService.resourceUrl;
        let body: string = JSON.stringify(parameter);
        return this.http.put<Parameter>(url, body);
    }

    update(parameter: Parameter): Observable<Parameter> {
        let url: string = ParameterService.resourceUrl + '/' + parameter.id;
        let body: string = JSON.stringify(parameter);
        return this.http.post<Parameter>(url, body);
    }

    delete(parameter: Parameter): Observable<Parameter> {
        let url: string = ParameterService.resourceUrl + '/' + parameter.id;
        return this.http.delete<Parameter>(url);
    }

}
