import { element, by, ElementFinder } from 'protractor';

export class BoxComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-box div table .btn-danger'));
  title = element.all(by.css('jhi-box div h2#page-heading span')).first();
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

export class BoxUpdatePage {
  pageTitle = element(by.id('jhi-box-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  aliasInput = element(by.id('field_alias'));
  x1Input = element(by.id('field_x1'));
  y1Input = element(by.id('field_y1'));
  x2Input = element(by.id('field_x2'));
  y2Input = element(by.id('field_y2'));
  pageNumberInput = element(by.id('field_pageNumber'));
  uploaddateInput = element(by.id('field_uploaddate'));

  fileInfoSelect = element(by.id('field_fileInfo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAliasInput(alias: string): Promise<void> {
    await this.aliasInput.sendKeys(alias);
  }

  async getAliasInput(): Promise<string> {
    return await this.aliasInput.getAttribute('value');
  }

  async setX1Input(x1: string): Promise<void> {
    await this.x1Input.sendKeys(x1);
  }

  async getX1Input(): Promise<string> {
    return await this.x1Input.getAttribute('value');
  }

  async setY1Input(y1: string): Promise<void> {
    await this.y1Input.sendKeys(y1);
  }

  async getY1Input(): Promise<string> {
    return await this.y1Input.getAttribute('value');
  }

  async setX2Input(x2: string): Promise<void> {
    await this.x2Input.sendKeys(x2);
  }

  async getX2Input(): Promise<string> {
    return await this.x2Input.getAttribute('value');
  }

  async setY2Input(y2: string): Promise<void> {
    await this.y2Input.sendKeys(y2);
  }

  async getY2Input(): Promise<string> {
    return await this.y2Input.getAttribute('value');
  }

  async setPageNumberInput(pageNumber: string): Promise<void> {
    await this.pageNumberInput.sendKeys(pageNumber);
  }

  async getPageNumberInput(): Promise<string> {
    return await this.pageNumberInput.getAttribute('value');
  }

  async setUploaddateInput(uploaddate: string): Promise<void> {
    await this.uploaddateInput.sendKeys(uploaddate);
  }

  async getUploaddateInput(): Promise<string> {
    return await this.uploaddateInput.getAttribute('value');
  }

  async fileInfoSelectLastOption(): Promise<void> {
    await this.fileInfoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async fileInfoSelectOption(option: string): Promise<void> {
    await this.fileInfoSelect.sendKeys(option);
  }

  getFileInfoSelect(): ElementFinder {
    return this.fileInfoSelect;
  }

  async getFileInfoSelectedOption(): Promise<string> {
    return await this.fileInfoSelect.element(by.css('option:checked')).getText();
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

export class BoxDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-box-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-box'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
