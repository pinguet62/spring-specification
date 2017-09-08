import {Component} from '@angular/core';

@Component({
    selector: 'p62-page-empty',
    template: ``
})
export class EmptyPageComponent {
}

@Component({
    selector: 'p62-page-content',
    template: `
        <router-outlet></router-outlet>
    `
})
export class ContentPageComponent {
}
