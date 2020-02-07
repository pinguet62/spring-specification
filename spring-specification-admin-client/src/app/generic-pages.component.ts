import {Component} from '@angular/core';

@Component({
    selector: 'app-page-empty',
    template: ``
})
export class EmptyPageComponent {
}

@Component({
    selector: 'app-page-content',
    template: `
        <router-outlet></router-outlet>
    `
})
export class ContentPageComponent {
}
