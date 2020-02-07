import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../../environments/environment';
import {BusinessRule} from './business-rule';

@Injectable()
export class BusinessRuleService {

    private static readonly resourceUrl: string = environment.apiUrl + '/businessRule';

    constructor(protected http: HttpClient) {
    }

    getAll(): Observable<BusinessRule[]> {
        const url: string = BusinessRuleService.resourceUrl;
        return this.http.get<BusinessRule[]>(url);
    }

    get(key: string): Observable<BusinessRule> {
        const url: string = BusinessRuleService.resourceUrl + '/' + key;
        return this.http.get<BusinessRule>(url);
    }

    create(businessRule: BusinessRule): Observable<BusinessRule> {
        const url: string = BusinessRuleService.resourceUrl;
        const body: string = JSON.stringify(businessRule);
        return this.http.put<BusinessRule>(url, body);
    }

    delete(businessRule: BusinessRule): Observable<BusinessRule> {
        const url: string = BusinessRuleService.resourceUrl + '/' + businessRule.id;
        return this.http.delete<BusinessRule>(url);
    }

}
