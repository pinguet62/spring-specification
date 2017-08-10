import {
    AfterContentInit,
    Component,
    ContentChild,
    ElementRef,
    EventEmitter,
    Input,
    Output,
    QueryList,
    TemplateRef,
    ViewChild,
    ViewChildren,
    ViewContainerRef
} from '@angular/core';
import {NodeMovedEvent, TreeNode} from './tree-node';
import {DragulaService} from 'ng2-dragula';

@Component({
    selector: 'p62-tree-node',
    template: `
        <li>
            <div class="tree-content">
                <div style="width: 40px;">
                    <button md-icon-button *ngIf="node.children?.length > 0" (click)="node.expanded = !node.expanded">
                        <md-icon>{{node.expanded ? 'keyboard_arrow_down' : 'keyboard_arrow_right'}}</md-icon>
                    </button>
                </div>

                <ng-container #labelView></ng-container>

                <div style="flex-grow: 1;"></div>
                <div>
                    <ng-container #optionsView></ng-container>
                </div>
            </div>

            <ul #childrenUl *ngIf="node.expanded" [dragula]="dragulaBagId" [dragulaModel]="node.children">
                <p62-tree-node *ngFor="let child of node.children" [dragulaBagId]="dragulaBagId" [node]="child" [labelTemplate]="labelTemplate"
                               [optionsTemplate]="optionsTemplate"></p62-tree-node>
                <li *ngIf="showEmptyDropZones" class="tree-empty">-</li><!-- Workaround to drop into empty node -->
            </ul>
        </li>`,
    styleUrls: ['./tree.component.css']
})
export class TreeNodeComponent implements AfterContentInit {

    /** Used by Dragula to identify a <i>bag</i>, and so to restrict drag/drop only into current {@link TreeComponent}. */
    @Input() dragulaBagId: string;
    showEmptyDropZones: boolean = false;

    @Input() node: TreeNode<any>;

    @Input() labelTemplate: TemplateRef<any>;
    @ViewChild('labelView', {read: ViewContainerRef}) labelView: ViewContainerRef;

    @Input() optionsTemplate: TemplateRef<any>;
    @ViewChild('optionsView', {read: ViewContainerRef}) optionsView: ViewContainerRef;

    @ViewChildren(TreeNodeComponent) childrenTreeNodeComponents: QueryList<TreeNodeComponent>;
    @ViewChild('childrenUl', {read: ElementRef}) childrenUlElement: ElementRef;

    constructor(public elementRef: ElementRef, dragulaService: DragulaService) {
        dragulaService.drag.subscribe(x =>
            this.showEmptyDropZones = this.node.acceptChildren && this.node.children.length === 0
        );
        dragulaService.drop.subscribe(x =>
            this.showEmptyDropZones = false
        );
        dragulaService.cancel.subscribe(x =>
            this.showEmptyDropZones = false
        );
    }

    /** Fill each {@link ViewContainerRef} from corresponding {@link TemplateRef}. */
    ngAfterContentInit(): void {
        this.labelView.createEmbeddedView(this.labelTemplate, {'\$implicit': this.node});
        this.optionsView.createEmbeddedView(this.optionsTemplate, {'\$implicit': this.node});
    }

}

@Component({
    selector: 'p62-tree',
    template: `
        <div class="tree-container">
            <p62-tree-node *ngIf="value" #chil [dragulaBagId]="dragulaBagId" [node]="value" [labelTemplate]="labelTemplate"
                           [optionsTemplate]="optionsTemplate"></p62-tree-node>
        </div>`,
    styleUrls: ['./tree.component.css'],
    viewProviders: [DragulaService /* fix: multiple-event handling */]
})
export class TreeComponent {

    dragulaBagId: string = 'dagula-bag-' + new Date().getTime().toString();

    @Input() value: TreeNode<any>;

    @Output() nodeMoved: EventEmitter<NodeMovedEvent<any>> = new EventEmitter<NodeMovedEvent<any>>();

    @ContentChild('label', {read: TemplateRef}) labelTemplate: TemplateRef<any>;
    @ContentChild('options', {read: TemplateRef}) optionsTemplate: TemplateRef<any>;

    @ViewChildren(TreeNodeComponent) childrenTreeNodeComponents: QueryList<TreeNodeComponent>;

    constructor(dragulaService: DragulaService) {
        dragulaService.setOptions(this.dragulaBagId, /*options=*/{
            revertOnSpill: true,
            accepts: (el: Element, target: Element, source: Element, sibling: Element): boolean => this.canDropInto(el, target, source, sibling)
        });
        dragulaService.drop.subscribe(values => {
            let movedTreeNodeElement: Element = values[1];
            let tgtUlElement: Element = values[2];
            let found: { node: TreeNode<any>, parent: TreeNode<any>, index: number } = this.findParentTreeNodeComponent(movedTreeNodeElement, tgtUlElement);
            let event: NodeMovedEvent<any> = {
                node: found.node,
                parent: found.parent,
                index: found.index
            };
            this.nodeMoved.emit(event);
        });
    }

    /** @return {@code null} if not found. */
    findParentTreeNodeComponent(movedTreeNodeElement: Element, tgtUlElement: Element): { node: TreeNode<any>, parent: TreeNode<any>, index: number } {
        let result: any = {
            node: null,
            parent: null,
            index: null
        };

        let BreakException = {}; // workaround to use "break" inside "forEach"
        try {
            this.visitTreeNodes(treeNodeComponent => {
                if (treeNodeComponent.childrenUlElement && treeNodeComponent.childrenUlElement.nativeElement === tgtUlElement)
                    result.parent = treeNodeComponent.node;
                if (treeNodeComponent.elementRef.nativeElement === movedTreeNodeElement)
                    result.node = treeNodeComponent.node;
            });
        } catch (e) {
            if (e !== BreakException) throw e;
        }

        // find index
        for (let i = 0; i < tgtUlElement.children.length; i++)
            if (tgtUlElement.children[i] === movedTreeNodeElement) {
                result.index = i;
                break;
            }

        return result;
    }

    private visitTreeNodes(visitor: (x: TreeNodeComponent) => void) {
        this.childrenTreeNodeComponents.forEach(x => {
            this.visitTreeNodesRecursively(x, visitor);
        });
    }

    private visitTreeNodesRecursively(treeNodeComponent: TreeNodeComponent, visitor: (x: TreeNodeComponent) => void) {
        visitor(treeNodeComponent);
        treeNodeComponent.childrenTreeNodeComponents.forEach(x => {
            this.visitTreeNodesRecursively(x, visitor);
        });
    }

    /** A {@link TreeNode} cannot be dropped into its {@link TreeNode#children} (otherwise an error will occur). */
    canDropInto(el: Element, tgtUlElement: Element, srcUlElement: Element, sibling: Element): boolean {
        return !el.contains(tgtUlElement);
    }

}
