import { element, by, ElementFinder } from 'protractor';

export class FileInfoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-file-info div table .btn-danger'));
  title = element.all(by.css('jhi-file-info div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class FileInfoUpdatePage {
  pageTitle = element(by.id('jhi-file-info-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fileInput = element(by.id('file_file'));
  notesInput = element(by.id('field_notes'));
  fileTypeSelect = element(by.id('field_fileType'));
  uploaddateInput = element(by.id('field_uploaddate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFileInput(file: string): Promise<void> {
    await this.fileInput.sendKeys(file);
  }

  async getFileInput(): Promise<string> {
    return await this.fileInput.getAttribute('value');
  }

  async setNotesInput(notes: string): Promise<void> {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput(): Promise<string> {
    return await this.notesInput.getAttribute('value');
  }

  async setFileTypeSelect(fileType: string): Promise<void> {
    await this.fileTypeSelect.sendKeys(fileType);
  }

  async getFileTypeSelect(): Promise<string> {
    return await this.fileTypeSelect.element(by.css('option:checked')).getText();
  }

  async fileTypeSelectLastOption(): Promise<void> {
    await this.fileTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setUploaddateInput(uploaddate: string): Promise<void> {
    await this.uploaddateInput.sendKeys(uploaddate);
  }

  async getUploaddateInput(): Promise<string> {
    return await this.uploaddateInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class FileInfoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-fileInfo-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-fileInfo'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
