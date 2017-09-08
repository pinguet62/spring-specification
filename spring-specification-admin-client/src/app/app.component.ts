import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
        <md-toolbar color="primary" class="mat-elevation-z8">
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
            <md-sidenav #sidenav mode="side" opened="true">
                <md-nav-list>
                    <md-list-item [routerLink]="['/rule']">
                        <md-icon md-list-icon>book</md-icon>
                        <span md-line>Rules</span>
                    </md-list-item>
                    <md-list-item [routerLink]="['/businessRule']">
                        <md-icon md-list-icon>settings</md-icon>
                        <span md-line>Business rules</span>
                    </md-list-item>
                </md-nav-list>
            </md-sidenav>

            <div style="margin: 50px;">
                <router-outlet></router-outlet>
            </div>
        </md-sidenav-container>`
})
export class AppComponent {
}
