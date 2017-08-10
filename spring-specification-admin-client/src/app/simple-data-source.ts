import {CollectionViewer, DataSource} from '@angular/cdk';
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

export class ObservableDataSource<T> extends DataSource<T> {

    constructor(public observable: BehaviorSubject<T[]>) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<T[]> {
        return this.observable;
    }

    disconnect(collectionViewer: CollectionViewer): void {
    }

}
