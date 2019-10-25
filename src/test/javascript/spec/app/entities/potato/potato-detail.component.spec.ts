import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TesterTestModule } from '../../../test.module';
import { PotatoDetailComponent } from 'app/entities/potato/potato-detail.component';
import { Potato } from 'app/shared/model/potato.model';

describe('Component Tests', () => {
  describe('Potato Management Detail Component', () => {
    let comp: PotatoDetailComponent;
    let fixture: ComponentFixture<PotatoDetailComponent>;
    const route = ({ data: of({ potato: new Potato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TesterTestModule],
        declarations: [PotatoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PotatoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PotatoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.potato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
