import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MdButtonModule, MdIconModule} from "@angular/material";
import {DragulaModule} from "ng2-dragula";

import {TreeComponent, TreeNodeComponent} from "./tree.component";

@NgModule({
    imports: [
        CommonModule, BrowserAnimationsModule,
        MdButtonModule, MdIconModule,
        DragulaModule
    ],
    declarations: [TreeComponent, TreeNodeComponent],
    exports: [TreeComponent, TreeNodeComponent]
})
export class TreeModule {}
