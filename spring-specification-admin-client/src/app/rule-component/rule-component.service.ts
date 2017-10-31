import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../../environments/environment';
import {RuleComponent} from './rule-component';

@Injectable()
export class RuleComponentService {

    private static readonly resourceUrl: string = environment.apiUrl + '/ruleComponent';

    constructor(protected http: HttpClient) {
    }

    get(id: number): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl + '/' + id;
        return this.http.get<RuleComponent>(url);
    }

    create(rule: RuleComponent): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl;
        let body: string = JSON.stringify(rule);
        return this.http.put<RuleComponent>(url, body);
    }

    update(rule: RuleComponent): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl + '/' + rule.id;
        let body: string = JSON.stringify(rule);
        return this.http.patch<RuleComponent>(url, body);
    }

    delete(rule: RuleComponent): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl + '/' + rule.id;
        return this.http.delete<RuleComponent>(url);
    }

}
