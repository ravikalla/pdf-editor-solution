import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BoxComponentsPage, BoxDeleteDialog, BoxUpdatePage } from './box.page-object';

const expect = chai.expect;

describe('Box e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let boxComponentsPage: BoxComponentsPage;
  let boxUpdatePage: BoxUpdatePage;
  let boxDeleteDialog: BoxDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Boxes', async () => {
    await navBarPage.goToEntity('box');
    boxComponentsPage = new BoxComponentsPage();
    await browser.wait(ec.visibilityOf(boxComponentsPage.title), 5000);
    expect(await boxComponentsPage.getTitle()).to.eq('pdfEditorApp.box.home.title');
    await browser.wait(ec.or(ec.visibilityOf(boxComponentsPage.entities), ec.visibilityOf(boxComponentsPage.noResult)), 1000);
  });

  it('should load create Box page', async () => {
    await boxComponentsPage.clickOnCreateButton();
    boxUpdatePage = new BoxUpdatePage();
    expect(await boxUpdatePage.getPageTitle()).to.eq('pdfEditorApp.box.home.createOrEditLabel');
    await boxUpdatePage.cancel();
  });

  it('should create and save Boxes', async () => {
    const nbButtonsBeforeCreate = await boxComponentsPage.countDeleteButtons();

    await boxComponentsPage.clickOnCreateButton();

    await promise.all([
      boxUpdatePage.setAliasInput('alias'),
      boxUpdatePage.setX1Input('5'),
      boxUpdatePage.setY1Input('5'),
      boxUpdatePage.setX2Input('5'),
      boxUpdatePage.setY2Input('5'),
      boxUpdatePage.setPageNumberInput('5'),
      boxUpdatePage.setUploaddateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      boxUpdatePage.fileInfoSelectLastOption()
    ]);

    expect(await boxUpdatePage.getAliasInput()).to.eq('alias', 'Expected Alias value to be equals to alias');
    expect(await boxUpdatePage.getX1Input()).to.eq('5', 'Expected x1 value to be equals to 5');
    expect(await boxUpdatePage.getY1Input()).to.eq('5', 'Expected y1 value to be equals to 5');
    expect(await boxUpdatePage.getX2Input()).to.eq('5', 'Expected x2 value to be equals to 5');
    expect(await boxUpdatePage.getY2Input()).to.eq('5', 'Expected y2 value to be equals to 5');
    expect(await boxUpdatePage.getPageNumberInput()).to.eq('5', 'Expected pageNumber value to be equals to 5');
    expect(await boxUpdatePage.getUploaddateInput()).to.contain('2001-01-01T02:30', 'Expected uploaddate value to be equals to 2000-12-31');

    await boxUpdatePage.save();
    expect(await boxUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await boxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Box', async () => {
    const nbButtonsBeforeDelete = await boxComponentsPage.countDeleteButtons();
    await boxComponentsPage.clickOnLastDeleteButton();

    boxDeleteDialog = new BoxDeleteDialog();
    expect(await boxDeleteDialog.getDialogTitle()).to.eq('pdfEditorApp.box.delete.question');
    await boxDeleteDialog.clickOnConfirmButton();

    expect(await boxComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
