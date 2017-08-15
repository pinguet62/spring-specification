import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../../environments/environment';
import {BusinessRule} from './business-rule';

@Injectable()
export class BusinessRuleService {

    private static readonly resourceUrl: string = environment.apiUrl + '/businessRule';

    private options: RequestOptionsArgs = {headers: new Headers({'Content-Type': 'application/json'})};

    constructor(protected http: Http) {
    }

    getAll(): Observable<BusinessRule[]> {
        let url: string = BusinessRuleService.resourceUrl;
        return this.http.get(url, this.options).map(res => res.json());
    }

    get(key: string): Observable<BusinessRule> {
        let url: string = BusinessRuleService.resourceUrl + '/' + key;
        return this.http.get(url, this.options).map(res => res.json());
    }

}
