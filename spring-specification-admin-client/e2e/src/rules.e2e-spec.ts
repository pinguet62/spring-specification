import {browser, by, element} from 'protractor';

describe('/rule', () => {
    beforeEach(() => browser.get('/rule'));

    it('should display values into table', async () => {
        expect(await element.all(by.css('mat-table mat-row mat-cell')).map(cell => cell.getText())).not.toBeNaN();
    });
});
