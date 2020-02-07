import {CommonModule} from '@angular/common';
import {By} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatDialogModule, MatDialogRef, MatInputModule} from '@angular/material';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';
import {CreateBusinessRuleDialogComponent} from './business-rules-list.component';

describe('CreateBusinessRuleDialog', () => {
        let fixture: ComponentFixture<CreateBusinessRuleDialogComponent>;

        beforeEach(() => {
            TestBed.configureTestingModule({
                declarations: [CreateBusinessRuleDialogComponent],
                imports: [CommonModule, BrowserAnimationsModule, FormsModule, ReactiveFormsModule, MatDialogModule, MatInputModule],
                providers: [{provide: MatDialogRef, useValue: null}]
            }).overrideModule(BrowserDynamicTestingModule, {
                set: {
                    entryComponents: [CreateBusinessRuleDialogComponent]
                }
            });
            fixture = TestBed.createComponent(CreateBusinessRuleDialogComponent);
        });

        it('has 2 buttons "Cancel" and "Apply"', () => {
            expect(fixture.debugElement.queryAll(By.css('mat-dialog-actions button')).length).toEqual(2);
        });

        it('has "Apply" button "disabled" at startup', async(() => {
            fixture.detectChanges();
            fixture.whenStable().then(() => {
                fixture.detectChanges();
                expect(fixture.debugElement.query(By.css('mat-dialog-actions button[disabled]'))).not.toBeNull();
            });
        }));
    }
);
