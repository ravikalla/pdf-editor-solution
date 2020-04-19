import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFileInfo } from 'app/shared/model/file-info.model';

type EntityResponseType = HttpResponse<IFileInfo>;
type EntityArrayResponseType = HttpResponse<IFileInfo[]>;

@Injectable({ providedIn: 'root' })
export class FileInfoService {
  public resourceUrl = SERVER_API_URL + 'api/file-infos';

  constructor(protected http: HttpClient) {}

  create(fileInfo: IFileInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileInfo);
    return this.http
      .post<IFileInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fileInfo: IFileInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(fileInfo);
    return this.http
      .put<IFileInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFileInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFileInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(fileInfo: IFileInfo): IFileInfo {
    const copy: IFileInfo = Object.assign({}, fileInfo, {
      uploaddate: fileInfo.uploaddate && fileInfo.uploaddate.isValid() ? fileInfo.uploaddate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.uploaddate = res.body.uploaddate ? moment(res.body.uploaddate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((fileInfo: IFileInfo) => {
        fileInfo.uploaddate = fileInfo.uploaddate ? moment(fileInfo.uploaddate) : undefined;
      });
    }
    return res;
  }
}
