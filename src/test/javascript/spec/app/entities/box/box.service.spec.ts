import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BoxService } from 'app/entities/box/box.service';
import { IBox, Box } from 'app/shared/model/box.model';

describe('Service Tests', () => {
  describe('Box Service', () => {
    let injector: TestBed;
    let service: BoxService;
    let httpMock: HttpTestingController;
    let elemDefault: IBox;
    let expectedResult: IBox | IBox[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BoxService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Box(0, 'AAAAAAA', 0, 0, 0, 0, 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            uploaddate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Box', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            uploaddate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            uploaddate: currentDate
          },
          returnedFromService
        );

        service.create(new Box()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Box', () => {
        const returnedFromService = Object.assign(
          {
            alias: 'BBBBBB',
            x1: 1,
            y1: 1,
            x2: 1,
            y2: 1,
            pageNumber: 1,
            uploaddate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            uploaddate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Box', () => {
        const returnedFromService = Object.assign(
          {
            alias: 'BBBBBB',
            x1: 1,
            y1: 1,
            x2: 1,
            y2: 1,
            pageNumber: 1,
            uploaddate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            uploaddate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Box', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
