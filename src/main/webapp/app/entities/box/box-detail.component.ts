import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBox } from 'app/shared/model/box.model';

@Component({
  selector: 'jhi-box-detail',
  templateUrl: './box-detail.component.html'
})
export class BoxDetailComponent implements OnInit {
  box: IBox | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ box }) => (this.box = box));
  }

  previousState(): void {
    window.history.back();
  }
}
