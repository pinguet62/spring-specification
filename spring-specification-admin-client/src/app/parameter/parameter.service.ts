import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {environment} from '../../environments/environment';
import {RuleComponent} from '../rule-component/rule-component';
import {Parameter} from './parameter';

@Injectable()
export class ParameterService {

    private static readonly resourceUrl: string = environment.apiUrl + '/parameter';

    private options: RequestOptionsArgs = {headers: new Headers({'Content-Type': 'application/json'})};

    constructor(private http: Http) {
    }

    getSupportedTypes(): Observable<string[]> {
        let url: string = ParameterService.resourceUrl + '/type';
        return this.http.get(url, this.options).map(res => res.json());
    }

    getByRuleComponent(ruleComponent: RuleComponent): Observable<Parameter[]> {
        let url: string = ParameterService.resourceUrl + '?ruleComponent=' + ruleComponent.id;
        return this.http.get(url, this.options).map(res => res.json());
    }

    create(parameter: Parameter): Observable<Parameter> {
        let url: string = ParameterService.resourceUrl;
        let body: string = JSON.stringify(parameter);
        return this.http.put(url, body, this.options).map(res => res.json());
    }

    update(parameter: Parameter): Observable<Parameter> {
        let url: string = ParameterService.resourceUrl + '/' + parameter.id;
        let body: string = JSON.stringify(parameter);
        return this.http.post(url, body, this.options).map(res => res.json());
    }

    delete(parameter: Parameter): Observable<Parameter> {
        let url: string = ParameterService.resourceUrl + '/' + parameter.id;
        return this.http.delete(url, this.options).map(res => res.json());
    }

}
