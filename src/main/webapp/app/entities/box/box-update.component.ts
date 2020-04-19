import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBox, Box } from 'app/shared/model/box.model';
import { BoxService } from './box.service';
import { IFileInfo } from 'app/shared/model/file-info.model';
import { FileInfoService } from 'app/entities/file-info/file-info.service';

@Component({
  selector: 'jhi-box-update',
  templateUrl: './box-update.component.html'
})
export class BoxUpdateComponent implements OnInit {
  isSaving = false;
  fileinfos: IFileInfo[] = [];

  editForm = this.fb.group({
    id: [],
    alias: [],
    x1: [null, [Validators.required, Validators.min(0), Validators.max(10000)]],
    y1: [null, [Validators.required, Validators.min(0), Validators.max(10000)]],
    x2: [null, [Validators.min(0), Validators.max(10000)]],
    y2: [null, [Validators.min(0), Validators.max(10000)]],
    pageNumber: [null, [Validators.required]],
    uploaddate: [],
    fileInfoId: []
  });

  constructor(
    protected boxService: BoxService,
    protected fileInfoService: FileInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ box }) => {
      if (!box.id) {
        const today = moment().startOf('day');
        box.uploaddate = today;
      }

      this.updateForm(box);

      this.fileInfoService.query().subscribe((res: HttpResponse<IFileInfo[]>) => (this.fileinfos = res.body || []));
    });
  }

  updateForm(box: IBox): void {
    this.editForm.patchValue({
      id: box.id,
      alias: box.alias,
      x1: box.x1,
      y1: box.y1,
      x2: box.x2,
      y2: box.y2,
      pageNumber: box.pageNumber,
      uploaddate: box.uploaddate ? box.uploaddate.format(DATE_TIME_FORMAT) : null,
      fileInfoId: box.fileInfoId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const box = this.createFromForm();
    if (box.id !== undefined) {
      this.subscribeToSaveResponse(this.boxService.update(box));
    } else {
      this.subscribeToSaveResponse(this.boxService.create(box));
    }
  }

  private createFromForm(): IBox {
    return {
      ...new Box(),
      id: this.editForm.get(['id'])!.value,
      alias: this.editForm.get(['alias'])!.value,
      x1: this.editForm.get(['x1'])!.value,
      y1: this.editForm.get(['y1'])!.value,
      x2: this.editForm.get(['x2'])!.value,
      y2: this.editForm.get(['y2'])!.value,
      pageNumber: this.editForm.get(['pageNumber'])!.value,
      uploaddate: this.editForm.get(['uploaddate'])!.value ? moment(this.editForm.get(['uploaddate'])!.value, DATE_TIME_FORMAT) : undefined,
      fileInfoId: this.editForm.get(['fileInfoId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBox>>): void {
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

  trackById(index: number, item: IFileInfo): any {
    return item.id;
  }
}
