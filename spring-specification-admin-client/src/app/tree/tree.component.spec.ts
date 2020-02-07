import {Component} from '@angular/core';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {TreeModule} from './tree.module';
import {TreeNode} from './tree-node';
import {By} from '@angular/platform-browser';

@Component({
    template: `
        <app-tree [value]="value">
            <ng-template #label></ng-template>
            <ng-template #options></ng-template>
        </app-tree>
    `
})
class TreeComponentTesterComponent {
    value: TreeNode<any> = {
        expanded: true,
        acceptChildren: true,
        data: {label: 'AND'},
        children: [
            {
                expanded: true,
                acceptChildren: true,
                data: {label: 'NOT'},
                children: [
                    {
                        expanded: true,
                        acceptChildren: true,
                        data: {label: 'Dangerous product'},
                        children: []
                    }
                ]
            },
            {
                expanded: true,
                acceptChildren: true,
                data: {label: 'OR'},
                children: [
                    {
                        expanded: true,
                        acceptChildren: true,
                        data: {label: 'Product type'},
                        children: []
                    },
                    {
                        expanded: true,
                        acceptChildren: true,
                        data: {label: 'Price less than'},
                        children: []
                    }
                ]
            }
        ]
    };
}

describe('TreeComponent', () => {
        let fixture: ComponentFixture<TreeComponentTesterComponent>;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TreeModule],
                declarations: [TreeComponentTesterComponent]
            });
            fixture = TestBed.createComponent(TreeComponentTesterComponent);
        });

        // Check than all <ul> tree are present
        it('is expanded by default', () => {
            fixture.detectChanges(); // @Input()
            expect(fixture.debugElement.queryAll(By.css('app-tree li ul li ul li')).length).not.toBe(0);
        });

        // Check than all <ul> tree are present
        it('collapse & expand', () => {
            const getButton = (f: ComponentFixture<TreeComponentTesterComponent>) => f.debugElement.queryAll(By.css('app-tree li ul li'))[0].query(By.css('.tree-content button[mat-icon-button]'));

            fixture.detectChanges(); // @Input()
            // expanded
            expect(fixture.debugElement.queryAll(By.css('app-tree li ul li')).length).toBe(5);

            getButton(fixture).nativeElement.click();
            fixture.detectChanges();
            // collapsed
            expect(fixture.debugElement.queryAll(By.css('app-tree li ul li')).length).toBe(4);

            getButton(fixture).nativeElement.click();
            fixture.detectChanges();
            // expanded
            expect(fixture.debugElement.queryAll(By.css('app-tree li ul li')).length).toBe(5);
        });
    }
);
