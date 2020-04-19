import { Moment } from 'moment';

export interface IBox {
  id?: number;
  alias?: string;
  x1?: number;
  y1?: number;
  x2?: number;
  y2?: number;
  pageNumber?: number;
  uploaddate?: Moment;
  fileInfoId?: number;
}

export class Box implements IBox {
  constructor(
    public id?: number,
    public alias?: string,
    public x1?: number,
    public y1?: number,
    public x2?: number,
    public y2?: number,
    public pageNumber?: number,
    public uploaddate?: Moment,
    public fileInfoId?: number
  ) {}
}
