import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
        <md-toolbar color="primary">
            <button (click)="sidenav.toggle()" md-icon-button>
                <md-icon>menu</md-icon>
            </button>

            <span>Admin</span>
        </md-toolbar>

        <md-sidenav-container>
            <md-sidenav #sidenav mode="side">
                <md-nav-list>
                    <md-list-item>
                        <md-icon md-list-icon>settings</md-icon>
                        <span md-line>Users</span>
                    </md-list-item>
                </md-nav-list>
            </md-sidenav>

            <router-outlet></router-outlet>

        </md-sidenav-container>`
})
export class AppComponent {
}
