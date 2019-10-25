import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TesterTestModule } from '../../../test.module';
import { PotatoComponent } from 'app/entities/potato/potato.component';
import { PotatoService } from 'app/entities/potato/potato.service';
import { Potato } from 'app/shared/model/potato.model';

describe('Component Tests', () => {
  describe('Potato Management Component', () => {
    let comp: PotatoComponent;
    let fixture: ComponentFixture<PotatoComponent>;
    let service: PotatoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TesterTestModule],
        declarations: [PotatoComponent],
        providers: []
      })
        .overrideTemplate(PotatoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PotatoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PotatoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Potato(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.potatoes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
