import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PdfEditorTestModule } from '../../../test.module';
import { FileInfoUpdateComponent } from 'app/entities/file-info/file-info-update.component';
import { FileInfoService } from 'app/entities/file-info/file-info.service';
import { FileInfo } from 'app/shared/model/file-info.model';

describe('Component Tests', () => {
  describe('FileInfo Management Update Component', () => {
    let comp: FileInfoUpdateComponent;
    let fixture: ComponentFixture<FileInfoUpdateComponent>;
    let service: FileInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PdfEditorTestModule],
        declarations: [FileInfoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FileInfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FileInfoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FileInfoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileInfo(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new FileInfo();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
