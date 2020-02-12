import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

import {environment} from '../../environments/environment';
import {RuleComponent} from './rule-component';

@Injectable()
export class RuleComponentService {

    public static readonly resourceUrl: string = environment.apiUrl + '/ruleComponent';

    constructor(protected http: HttpClient) {
    }

    get(id: number): Observable<RuleComponent> {
        const url: string = RuleComponentService.resourceUrl + '/' + id;
        return this.http.get<RuleComponent>(url);
    }

    create(rule: RuleComponent): Observable<RuleComponent> {
        const url: string = RuleComponentService.resourceUrl;
        const body: string = JSON.stringify(rule);
        return this.http.put<RuleComponent>(url, body);
    }

    update(rule: RuleComponent): Observable<RuleComponent> {
        const url: string = RuleComponentService.resourceUrl + '/' + rule.id;
        const body: string = JSON.stringify(rule);
        return this.http.patch<RuleComponent>(url, body);
    }

    delete(rule: RuleComponent): Observable<RuleComponent> {
        const url: string = RuleComponentService.resourceUrl + '/' + rule.id;
        return this.http.delete<RuleComponent>(url);
    }

}
