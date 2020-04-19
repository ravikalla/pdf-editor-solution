import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IFileInfo, FileInfo } from 'app/shared/model/file-info.model';
import { FileInfoService } from './file-info.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-file-info-update',
  templateUrl: './file-info-update.component.html'
})
export class FileInfoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    file: [null, [Validators.required]],
    fileContentType: [],
    notes: [],
    fileType: [],
    uploaddate: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected fileInfoService: FileInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fileInfo }) => {
      if (!fileInfo.id) {
        const today = moment().startOf('day');
        fileInfo.uploaddate = today;
      }

      this.updateForm(fileInfo);
    });
  }

  updateForm(fileInfo: IFileInfo): void {
    this.editForm.patchValue({
      id: fileInfo.id,
      file: fileInfo.file,
      fileContentType: fileInfo.fileContentType,
      notes: fileInfo.notes,
      fileType: fileInfo.fileType,
      uploaddate: fileInfo.uploaddate ? fileInfo.uploaddate.format(DATE_TIME_FORMAT) : null
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('pdfEditorApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fileInfo = this.createFromForm();
    if (fileInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.fileInfoService.update(fileInfo));
    } else {
      this.subscribeToSaveResponse(this.fileInfoService.create(fileInfo));
    }
  }

  private createFromForm(): IFileInfo {
    return {
      ...new FileInfo(),
      id: this.editForm.get(['id'])!.value,
      fileContentType: this.editForm.get(['fileContentType'])!.value,
      file: this.editForm.get(['file'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      fileType: this.editForm.get(['fileType'])!.value,
      uploaddate: this.editForm.get(['uploaddate'])!.value ? moment(this.editForm.get(['uploaddate'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileInfo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
