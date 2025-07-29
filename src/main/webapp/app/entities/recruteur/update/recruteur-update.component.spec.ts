import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IUtilisateur } from 'app/entities/utilisateur/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur/service/utilisateur.service';
import { RecruteurService } from '../service/recruteur.service';
import { IRecruteur } from '../recruteur.model';
import { RecruteurFormService } from './recruteur-form.service';

import { RecruteurUpdateComponent } from './recruteur-update.component';

describe('Recruteur Management Update Component', () => {
  let comp: RecruteurUpdateComponent;
  let fixture: ComponentFixture<RecruteurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let recruteurFormService: RecruteurFormService;
  let recruteurService: RecruteurService;
  let utilisateurService: UtilisateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RecruteurUpdateComponent],
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
      .overrideTemplate(RecruteurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RecruteurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    recruteurFormService = TestBed.inject(RecruteurFormService);
    recruteurService = TestBed.inject(RecruteurService);
    utilisateurService = TestBed.inject(UtilisateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call utilisateur query and add missing value', () => {
      const recruteur: IRecruteur = { id: 10028 };
      const utilisateur: IUtilisateur = { id: 2179 };
      recruteur.utilisateur = utilisateur;

      const utilisateurCollection: IUtilisateur[] = [{ id: 2179 }];
      jest.spyOn(utilisateurService, 'query').mockReturnValue(of(new HttpResponse({ body: utilisateurCollection })));
      const expectedCollection: IUtilisateur[] = [utilisateur, ...utilisateurCollection];
      jest.spyOn(utilisateurService, 'addUtilisateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ recruteur });
      comp.ngOnInit();

      expect(utilisateurService.query).toHaveBeenCalled();
      expect(utilisateurService.addUtilisateurToCollectionIfMissing).toHaveBeenCalledWith(utilisateurCollection, utilisateur);
      expect(comp.utilisateursCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const recruteur: IRecruteur = { id: 10028 };
      const utilisateur: IUtilisateur = { id: 2179 };
      recruteur.utilisateur = utilisateur;

      activatedRoute.data = of({ recruteur });
      comp.ngOnInit();

      expect(comp.utilisateursCollection).toContainEqual(utilisateur);
      expect(comp.recruteur).toEqual(recruteur);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecruteur>>();
      const recruteur = { id: 4268 };
      jest.spyOn(recruteurFormService, 'getRecruteur').mockReturnValue(recruteur);
      jest.spyOn(recruteurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recruteur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recruteur }));
      saveSubject.complete();

      // THEN
      expect(recruteurFormService.getRecruteur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(recruteurService.update).toHaveBeenCalledWith(expect.objectContaining(recruteur));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecruteur>>();
      const recruteur = { id: 4268 };
      jest.spyOn(recruteurFormService, 'getRecruteur').mockReturnValue({ id: null });
      jest.spyOn(recruteurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recruteur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: recruteur }));
      saveSubject.complete();

      // THEN
      expect(recruteurFormService.getRecruteur).toHaveBeenCalled();
      expect(recruteurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRecruteur>>();
      const recruteur = { id: 4268 };
      jest.spyOn(recruteurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ recruteur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(recruteurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUtilisateur', () => {
      it('should forward to utilisateurService', () => {
        const entity = { id: 2179 };
        const entity2 = { id: 31928 };
        jest.spyOn(utilisateurService, 'compareUtilisateur');
        comp.compareUtilisateur(entity, entity2);
        expect(utilisateurService.compareUtilisateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
