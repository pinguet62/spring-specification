import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
        <md-toolbar color="primary">
            <button (click)="sidenav.toggle()" md-icon-button>
                <md-icon>menu</md-icon>
            </button>

            <span>Spring Specification</span>

            <span style="flex: 1 1 auto"></span>

            <a md-icon-button href="https://github.com/pinguet62/spring-specification" target="_blank">
                <md-icon fontSet="fa" fontIcon="fa-github" class="fa-2x"></md-icon>
            </a>
        </md-toolbar>

        <md-sidenav-container fullscreen>
            <md-sidenav #sidenav mode="side">
                <md-nav-list>
                    <md-list-item>
                        <md-icon md-list-icon>settings</md-icon>
                        <span md-line>Rules</span>
                    </md-list-item>
                </md-nav-list>
            </md-sidenav>

            <router-outlet></router-outlet>
        </md-sidenav-container>`
})
export class AppComponent {
}
