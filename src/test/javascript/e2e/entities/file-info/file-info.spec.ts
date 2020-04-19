import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FileInfoComponentsPage, FileInfoDeleteDialog, FileInfoUpdatePage } from './file-info.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('FileInfo e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fileInfoComponentsPage: FileInfoComponentsPage;
  let fileInfoUpdatePage: FileInfoUpdatePage;
  let fileInfoDeleteDialog: FileInfoDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FileInfos', async () => {
    await navBarPage.goToEntity('file-info');
    fileInfoComponentsPage = new FileInfoComponentsPage();
    await browser.wait(ec.visibilityOf(fileInfoComponentsPage.title), 5000);
    expect(await fileInfoComponentsPage.getTitle()).to.eq('pdfEditorApp.fileInfo.home.title');
    await browser.wait(ec.or(ec.visibilityOf(fileInfoComponentsPage.entities), ec.visibilityOf(fileInfoComponentsPage.noResult)), 1000);
  });

  it('should load create FileInfo page', async () => {
    await fileInfoComponentsPage.clickOnCreateButton();
    fileInfoUpdatePage = new FileInfoUpdatePage();
    expect(await fileInfoUpdatePage.getPageTitle()).to.eq('pdfEditorApp.fileInfo.home.createOrEditLabel');
    await fileInfoUpdatePage.cancel();
  });

  it('should create and save FileInfos', async () => {
    const nbButtonsBeforeCreate = await fileInfoComponentsPage.countDeleteButtons();

    await fileInfoComponentsPage.clickOnCreateButton();

    await promise.all([
      fileInfoUpdatePage.setFileInput(absolutePath),
      fileInfoUpdatePage.setNotesInput('notes'),
      fileInfoUpdatePage.fileTypeSelectLastOption(),
      fileInfoUpdatePage.setUploaddateInput('01/01/2001' + protractor.Key.TAB + '02:30AM')
    ]);

    expect(await fileInfoUpdatePage.getFileInput()).to.endsWith(fileNameToUpload, 'Expected File value to be end with ' + fileNameToUpload);
    expect(await fileInfoUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');
    expect(await fileInfoUpdatePage.getUploaddateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected uploaddate value to be equals to 2000-12-31'
    );

    await fileInfoUpdatePage.save();
    expect(await fileInfoUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fileInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FileInfo', async () => {
    const nbButtonsBeforeDelete = await fileInfoComponentsPage.countDeleteButtons();
    await fileInfoComponentsPage.clickOnLastDeleteButton();

    fileInfoDeleteDialog = new FileInfoDeleteDialog();
    expect(await fileInfoDeleteDialog.getDialogTitle()).to.eq('pdfEditorApp.fileInfo.delete.question');
    await fileInfoDeleteDialog.clickOnConfirmButton();

    expect(await fileInfoComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
