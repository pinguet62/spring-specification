import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptionsArgs} from "@angular/http";
import {Observable} from "rxjs/Observable";
import 'rxjs/add/operator/map';
import {environment} from "../../environments/environment";
import {Rule} from "./rule";

@Injectable()
export class RuleService {

    private static readonly resourceUrl: string = environment.apiUrl + '/rule';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    constructor(protected http: Http) {}

    getAllRoots(): Observable<Rule[]> {
        let url: string = RuleService.resourceUrl;
        return this.http.get(url, this.options).map(res => res.json());
    }

    get(id: number): Observable<Rule> {
        let url: string = RuleService.resourceUrl + '/' + id;
        return this.http.get(url, this.options).map(res => res.json());
    }

    create(rule: Rule): Observable<Rule> {
        let url: string = RuleService.resourceUrl;
        let body: string = JSON.stringify(rule);
        return this.http.put(url, body, this.options).map(res => res.json());
    }

    update(rule: Rule): Observable<Rule> {
        let url: string = RuleService.resourceUrl + '/' + rule.id;
        let body: string = JSON.stringify(rule);
        return this.http.patch(url, body, this.options).map(res => res.json());
    }
}