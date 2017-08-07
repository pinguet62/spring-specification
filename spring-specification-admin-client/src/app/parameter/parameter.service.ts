import {Injectable} from "@angular/core";
import {Http, RequestOptionsArgs, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import * as Rx from "rxjs/Rx";
import 'rxjs/Rx';
import {Rule} from "../rule/rule";
import {Parameter} from "./parameter";

@Injectable()
export class ParameterService {

    private baseUrl: string = window.location.href;
    private resourceUrl: string = this.baseUrl + '/parameter';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    constructor(private http: Http) {}

    getSupportedTypes(): Observable<string[]> {
        let url: string = this.resourceUrl + '/type';
        return this.http.get(url, this.options).map(res => res.json());
    }

    getByRule(rule: Rule): Observable<Parameter[]> {
        let url: string = this.resourceUrl + '?rule=' + rule.id;
        return this.http.get(url, this.options).map(res => res.json());
    }

    create(parameter: Parameter): Observable<Parameter> {
        let url: string = this.resourceUrl;
        let body: string = JSON.stringify(parameter);
        return this.http.put(url, body, this.options).map(res => res.json());
    }

    update(parameter: Parameter): Observable<Parameter> {
        let url: string = this.resourceUrl + '/' + parameter.id;
        let body: string = JSON.stringify(parameter);
        return this.http.post(url, body, this.options).map(res => res.json());
    }

    delete(parameter: Parameter): Observable<Parameter> {
        let url: string = this.resourceUrl + '/' + parameter.id;
        return this.http.delete(url, this.options).map(res => res.json());
    }
}