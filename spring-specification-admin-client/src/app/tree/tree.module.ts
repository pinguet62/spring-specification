import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {DragulaModule} from 'ng2-dragula';

import {TreeComponent, TreeNodeComponent} from './tree.component';

@NgModule({
    imports: [
        CommonModule, BrowserAnimationsModule,
        MatButtonModule, MatIconModule,
        DragulaModule.forRoot()
    ],
    declarations: [TreeComponent, TreeNodeComponent],
    exports: [TreeComponent, TreeNodeComponent]
})
export class TreeModule {
}
