import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBox } from 'app/shared/model/box.model';
import { BoxService } from './box.service';

@Component({
  templateUrl: './box-delete-dialog.component.html'
})
export class BoxDeleteDialogComponent {
  box?: IBox;

  constructor(protected boxService: BoxService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.boxService.delete(id).subscribe(() => {
      this.eventManager.broadcast('boxListModification');
      this.activeModal.close();
    });
  }
}
