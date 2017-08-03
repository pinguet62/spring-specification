import {Injectable} from "@angular/core";
import {Http, RequestOptionsArgs, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import * as Rx from "rxjs/Rx";
import 'rxjs/Rx';
import {Rule} from "./rule";
import {RuleInformation} from "./rule-information";

@Injectable()
export class RuleService {

    private baseUrl: string = 'http://localhost:8080';
    private resourceUrl: string = this.baseUrl + '/rule';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    constructor(protected http: Http) {}

    getAllRoots(): Observable<Rule[]> {
        let targetUrl: string = this.resourceUrl;
        return this.http.get(targetUrl, this.options).map(res => res.json());
    }

    getAvailableKeys(): Observable<RuleInformation[]> {
        let targetUrl: string = this.resourceUrl + '/key';
        return this.http.get(targetUrl, this.options).map(res => res.json());
    }

    create(rule: Rule): Observable<Rule> {
        let url: string = this.resourceUrl;
        let body: string = JSON.stringify(rule);
        return this.http.put(url, body, this.options).map(res => res.json());
    }

    update(rule: Rule): Observable<Rule> {
        let url: string = this.resourceUrl + '/' + rule.id;
        let body: string = JSON.stringify(rule);
        return this.http.post(url, body, this.options).map(res => res.json());
    }

    changeIndex(rule: Rule, index: number): Observable<Rule> {
        let url: string = this.resourceUrl + '/' + rule.id + '/index/' + index;
        return this.http.post(url, null, this.options).map(res => res.json());
    }
}