import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../../environments/environment';
import {RuleComponent} from './rule-component';

@Injectable()
export class RuleComponentService {

    private static readonly resourceUrl: string = environment.apiUrl + '/ruleComponent';

    private options: RequestOptionsArgs = {headers: new Headers({'Content-Type': 'application/json'})};

    constructor(protected http: Http) {
    }

    get(id: number): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl + '/' + id;
        return this.http.get(url, this.options).map(res => res.json());
    }

    create(rule: RuleComponent): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl;
        let body: string = JSON.stringify(rule);
        return this.http.put(url, body, this.options).map(res => res.json());
    }

    update(rule: RuleComponent): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl + '/' + rule.id;
        let body: string = JSON.stringify(rule);
        return this.http.patch(url, body, this.options).map(res => res.json());
    }

    delete(rule: RuleComponent): Observable<RuleComponent> {
        let url: string = RuleComponentService.resourceUrl + '/' + rule.id;
        return this.http.delete(url, this.options).map(res => res.json());
    }

}
