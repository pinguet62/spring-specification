import {Component} from '@angular/core';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {TreeModule} from './tree.module';
import {TreeNode} from './tree-node';
import {By} from '@angular/platform-browser';

@Component({
    template: `
        <p62-tree [value]="value">
            <ng-template #label></ng-template>
            <ng-template #options></ng-template>
        </p62-tree>
    `
})
class TreeComponentTester {
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
        let fixture: ComponentFixture<TreeComponentTester>;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TreeModule],
                declarations: [TreeComponentTester]
            });
            fixture = TestBed.createComponent(TreeComponentTester);
        });

        // Check than all <ul> tree are present
        it('is expanded by default', () => {
            fixture.detectChanges(); // @Input()
            expect(fixture.debugElement.queryAll(By.css('p62-tree li ul li ul li')).length).not.toBe(0);
        });

        // Check than all <ul> tree are present
        it('collapse & expand', () => {
            let getButton = (fixture: ComponentFixture<TreeComponentTester>) => fixture.debugElement.queryAll(By.css('p62-tree li ul li'))[0].query(By.css('.tree-content button[md-icon-button]'));

            fixture.detectChanges(); // @Input()
            // expanded
            expect(fixture.debugElement.queryAll(By.css('p62-tree li ul li')).length).toBe(5);

            getButton(fixture).nativeElement.click();
            fixture.detectChanges();
            // collapsed
            expect(fixture.debugElement.queryAll(By.css('p62-tree li ul li')).length).toBe(4);

            getButton(fixture).nativeElement.click();
            fixture.detectChanges();
            // expanded
            expect(fixture.debugElement.queryAll(By.css('p62-tree li ul li')).length).toBe(5);
        });
    }
);
