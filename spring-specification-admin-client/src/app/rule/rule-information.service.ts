import {Injectable} from "@angular/core";
import {Headers, Http, RequestOptionsArgs} from "@angular/http";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {RuleInformation} from "./rule-information";
import {isNullOrUndefined} from "util";

@Injectable()
export class RuleInformationService {

    private baseUrl: string = 'http://localhost:8080';
    private resourceUrl: string = this.baseUrl + '/ruleCatalog';

    private options: RequestOptionsArgs = { headers: new Headers({ 'Content-Type': 'application/json' }) };

    CACHE: RuleInformation[];

    constructor(protected http: Http) {}

    load(): Observable<RuleInformation[]> {
        let url: string = this.resourceUrl + '/';
        return this.http.get(url, this.options).map(res => res.json());
    }

    getAll(): RuleInformation[] {
        return this.CACHE;
    }

    /**
     * @return {@code null} if not found.
     * @throws
     */
    getFromKey(key: string): RuleInformation {
        let found: RuleInformation[] = this.CACHE.filter(ri => ri.key === key);
        if (found.length === 0)
            return null;
        else if (found.length > 1)
            throw new Error('More than 1 value found for key: ' + key);
        return found[0];
    }
}

/** {@link Resolve} used to load {@link RuleInformationService#CACHE} on startup. */
@Injectable()
export class RuleInformationServiceResolver implements Resolve<RuleInformation[]> {
    constructor(private ruleInformationService: RuleInformationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RuleInformation[]> | Promise<RuleInformation[]> | RuleInformation[] {
        return this.ruleInformationService.load().map(x =>
            this.ruleInformationService.CACHE = x
        );
    }
}