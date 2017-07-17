import { SpringRuleengineUiPage } from './app.po';

describe('spring-ruleengine-admin-client App', () => {
  let page: SpringRuleengineUiPage;

  beforeEach(() => {
    page = new SpringRuleengineUiPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
