import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptionsArgs} from "@angular/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/Rx';
import {Rule} from "./rule";

@Injectable()
export class RuleService {

    private baseUrl: string = window.location.href;
    private resourceUrl: string = this.baseUrl + '/rule';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    constructor(protected http: Http) {}

    getAllRoots(): Observable<Rule[]> {
        let url: string = this.resourceUrl;
        return this.http.get(url, this.options).map(res => res.json());
    }

    get(id: number): Observable<Rule> {
        let url: string = this.resourceUrl + '/' + id;
        return this.http.get(url, this.options).map(res => res.json());
    }

    create(rule: Rule): Observable<Rule> {
        let url: string = this.resourceUrl;
        let body: string = JSON.stringify(rule);
        return this.http.put(url, body, this.options).map(res => res.json());
    }

    update(rule: Rule): Observable<Rule> {
        let url: string = this.resourceUrl + '/' + rule.id;
        let body: string = JSON.stringify(rule);
        return this.http.patch(url, body, this.options).map(res => res.json());
    }
}