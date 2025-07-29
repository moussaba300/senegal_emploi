import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PosteService } from '../service/poste.service';
import { IPoste } from '../poste.model';
import { PosteFormService } from './poste-form.service';

import { PosteUpdateComponent } from './poste-update.component';

describe('Poste Management Update Component', () => {
  let comp: PosteUpdateComponent;
  let fixture: ComponentFixture<PosteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let posteFormService: PosteFormService;
  let posteService: PosteService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PosteUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PosteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PosteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    posteFormService = TestBed.inject(PosteFormService);
    posteService = TestBed.inject(PosteService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const poste: IPoste = { id: 12668 };

      activatedRoute.data = of({ poste });
      comp.ngOnInit();

      expect(comp.poste).toEqual(poste);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoste>>();
      const poste = { id: 16448 };
      jest.spyOn(posteFormService, 'getPoste').mockReturnValue(poste);
      jest.spyOn(posteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poste });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: poste }));
      saveSubject.complete();

      // THEN
      expect(posteFormService.getPoste).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(posteService.update).toHaveBeenCalledWith(expect.objectContaining(poste));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoste>>();
      const poste = { id: 16448 };
      jest.spyOn(posteFormService, 'getPoste').mockReturnValue({ id: null });
      jest.spyOn(posteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poste: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: poste }));
      saveSubject.complete();

      // THEN
      expect(posteFormService.getPoste).toHaveBeenCalled();
      expect(posteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPoste>>();
      const poste = { id: 16448 };
      jest.spyOn(posteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ poste });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(posteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
