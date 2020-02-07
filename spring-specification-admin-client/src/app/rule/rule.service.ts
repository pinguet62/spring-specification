import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import {environment} from '../../environments/environment';
import {Rule} from './rule';

@Injectable()
export class RuleService {

    public static readonly resourceUrl: string = environment.apiUrl + '/rule';

    CACHE: Rule[];

    constructor(protected http: HttpClient) {
    }

    load(): Observable<Rule[]> {
        const url: string = RuleService.resourceUrl + '/';
        return this.http.get<Rule[]>(url);
    }

    getAll(): Rule[] {
        return this.CACHE;
    }

    /**
     * @return {@code null} if not found.
     * @throws
     */
    getFromKey(key: string): Rule {
        if (this.CACHE == null) {
            return null;
        }
        const found: Rule[] = this.CACHE.filter(ri => ri.key === key);
        if (found.length === 0) {
            return null;
        } else if (found.length > 1) {
            throw new Error('More than 1 value found for key: ' + key);
        }
        return found[0];
    }

    getAssociableRules(argumentType: string): Observable<Rule[]> {
        const url: string = RuleService.resourceUrl + '?argumentType=' + argumentType;
        return this.http.get<Rule[]>(url);
    }

}

/** {@link Resolve} used to load {@link RuleService#CACHE} on startup. */
@Injectable()
export class RuleServiceResolver implements Resolve<Rule[]> {

    constructor(private ruleService: RuleService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Rule[]> | Promise<Rule[]> | Rule[] {
        if (this.ruleService.CACHE != null) {
            return this.ruleService.CACHE;
        }

        return this.ruleService.load().map(x =>
            this.ruleService.CACHE = x
        );
    }

}
