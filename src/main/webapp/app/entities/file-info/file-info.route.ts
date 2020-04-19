import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFileInfo, FileInfo } from 'app/shared/model/file-info.model';
import { FileInfoService } from './file-info.service';
import { FileInfoComponent } from './file-info.component';
import { FileInfoDetailComponent } from './file-info-detail.component';
import { FileInfoUpdateComponent } from './file-info-update.component';

@Injectable({ providedIn: 'root' })
export class FileInfoResolve implements Resolve<IFileInfo> {
  constructor(private service: FileInfoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFileInfo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fileInfo: HttpResponse<FileInfo>) => {
          if (fileInfo.body) {
            return of(fileInfo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FileInfo());
  }
}

export const fileInfoRoute: Routes = [
  {
    path: '',
    component: FileInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'pdfEditorApp.fileInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FileInfoDetailComponent,
    resolve: {
      fileInfo: FileInfoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdfEditorApp.fileInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FileInfoUpdateComponent,
    resolve: {
      fileInfo: FileInfoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdfEditorApp.fileInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FileInfoUpdateComponent,
    resolve: {
      fileInfo: FileInfoResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'pdfEditorApp.fileInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
