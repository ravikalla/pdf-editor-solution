import { Moment } from 'moment';
import { IBox } from 'app/shared/model/box.model';
import { FileType } from 'app/shared/model/enumerations/file-type.model';

export interface IFileInfo {
  id?: number;
  fileContentType?: string;
  file?: any;
  notes?: string;
  fileType?: FileType;
  uploaddate?: Moment;
  boxes?: IBox[];
}

export class FileInfo implements IFileInfo {
  constructor(
    public id?: number,
    public fileContentType?: string,
    public file?: any,
    public notes?: string,
    public fileType?: FileType,
    public uploaddate?: Moment,
    public boxes?: IBox[]
  ) {}
}
