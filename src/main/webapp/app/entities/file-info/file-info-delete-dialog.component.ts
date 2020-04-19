import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileInfo } from 'app/shared/model/file-info.model';
import { FileInfoService } from './file-info.service';

@Component({
  templateUrl: './file-info-delete-dialog.component.html'
})
export class FileInfoDeleteDialogComponent {
  fileInfo?: IFileInfo;

  constructor(protected fileInfoService: FileInfoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fileInfoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fileInfoListModification');
      this.activeModal.close();
    });
  }
}
