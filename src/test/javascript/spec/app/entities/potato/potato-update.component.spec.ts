import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TesterTestModule } from '../../../test.module';
import { PotatoUpdateComponent } from 'app/entities/potato/potato-update.component';
import { PotatoService } from 'app/entities/potato/potato.service';
import { Potato } from 'app/shared/model/potato.model';

describe('Component Tests', () => {
  describe('Potato Management Update Component', () => {
    let comp: PotatoUpdateComponent;
    let fixture: ComponentFixture<PotatoUpdateComponent>;
    let service: PotatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TesterTestModule],
        declarations: [PotatoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PotatoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PotatoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PotatoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Potato(123);
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
        const entity = new Potato();
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
