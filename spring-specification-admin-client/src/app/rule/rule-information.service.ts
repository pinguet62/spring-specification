import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptionsArgs} from '@angular/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {environment} from '../../environments/environment';
import {RuleInformation} from './rule-information';

@Injectable()
export class RuleInformationService {

    private static readonly resourceUrl: string = environment.apiUrl + '/ruleCatalog';

    private options: RequestOptionsArgs = {headers: new Headers({'Content-Type': 'application/json'})};

    CACHE: RuleInformation[];

    constructor(protected http: Http) {
    }

    load(): Observable<RuleInformation[]> {
        let url: string = RuleInformationService.resourceUrl + '/';
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
        if (this.CACHE == null)
            return null;
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

    constructor(private ruleInformationService: RuleInformationService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RuleInformation[]> | Promise<RuleInformation[]> | RuleInformation[] {
        if (this.ruleInformationService.CACHE != null)
            return this.ruleInformationService.CACHE;

        return this.ruleInformationService.load().map(x =>
            this.ruleInformationService.CACHE = x
        );
    }

}
