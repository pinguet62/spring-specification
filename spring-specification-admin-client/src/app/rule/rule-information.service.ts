import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptionsArgs} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {RuleInformation} from "./rule-information";

@Injectable()
export class RuleCatalogService {

    private baseUrl: string = 'http://localhost:8080';
    private resourceUrl: string = this.baseUrl + '/ruleCatalog';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    constructor(protected http: Http) {}

    getAvailableKeys(): Observable<RuleInformation[]> {
        let url: string = this.resourceUrl + '/';
        return this.http.get(url, this.options).map(res => res.json());
    }
}