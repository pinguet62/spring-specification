import {CollectionViewer, DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';

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
