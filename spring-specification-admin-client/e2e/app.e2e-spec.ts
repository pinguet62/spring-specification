import {browser, by, element} from 'protractor';

describe('/', () => {
    beforeEach(() => browser.get('/'));

    it('should display top application name', () => {
        expect(element(by.css('mat-toolbar')).getText()).toContain('Spring Specification');
    });

    it('should display left menu items', () => {
        expect(element.all(by.css('mat-sidenav-container mat-sidenav mat-nav-list mat-list-item .mat-list-text')).map(e => e.getText())).toEqual(['Rules', 'Business rules']);
    });
});
