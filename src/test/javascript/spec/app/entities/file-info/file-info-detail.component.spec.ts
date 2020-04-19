import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PdfEditorTestModule } from '../../../test.module';
import { FileInfoDetailComponent } from 'app/entities/file-info/file-info-detail.component';
import { FileInfo } from 'app/shared/model/file-info.model';

describe('Component Tests', () => {
  describe('FileInfo Management Detail Component', () => {
    let comp: FileInfoDetailComponent;
    let fixture: ComponentFixture<FileInfoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ fileInfo: new FileInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdfEditorTestModule],
        declarations: [FileInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FileInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FileInfoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load fileInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fileInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
