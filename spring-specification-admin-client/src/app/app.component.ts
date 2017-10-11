import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
        <mat-toolbar color="primary" class="mat-elevation-z8">
            <button (click)="sidenav.toggle()" mat-icon-button>
                <mat-icon>menu</mat-icon>
            </button>

            <span>Spring Specification</span>

            <span style="flex: 1 1 auto"></span>

            <a mat-icon-button href="https://github.com/pinguet62/spring-specification" target="_blank">
                <mat-icon fontSet="fa" fontIcon="fa-github" class="fa-2x"></mat-icon>
            </a>
        </mat-toolbar>

        <mat-sidenav-container fullscreen>
            <mat-sidenav #sidenav mode="side" opened="true">
                <mat-nav-list>
                    <mat-list-item [routerLink]="['/rule']">
                        <mat-icon mat-list-icon>book</mat-icon>
                        <span matLine>Rules</span>
                    </mat-list-item>
                    <mat-list-item [routerLink]="['/businessRule']">
                        <mat-icon mat-list-icon>settings</mat-icon>
                        <span matLine>Business rules</span>
                    </mat-list-item>
                </mat-nav-list>
            </mat-sidenav>

            <div style="margin: 50px;">
                <router-outlet></router-outlet>
            </div>
        </mat-sidenav-container>`
})
export class AppComponent {
}
