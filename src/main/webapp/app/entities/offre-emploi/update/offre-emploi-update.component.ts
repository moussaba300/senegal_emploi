import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRecruteur } from 'app/entities/recruteur/recruteur.model';
import { RecruteurService } from 'app/entities/recruteur/service/recruteur.service';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { TypeContratService } from 'app/entities/type-contrat/service/type-contrat.service';
import { IPoste } from 'app/entities/poste/poste.model';
import { PosteService } from 'app/entities/poste/service/poste.service';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { LocalisationService } from 'app/entities/localisation/service/localisation.service';
import { OffreEmploiService } from '../service/offre-emploi.service';
import { IOffreEmploi } from '../offre-emploi.model';
import { OffreEmploiFormGroup, OffreEmploiFormService } from './offre-emploi-form.service';

@Component({
  selector: 'jhi-offre-emploi-update',
  templateUrl: './offre-emploi-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class OffreEmploiUpdateComponent implements OnInit {
  isSaving = false;
  offreEmploi: IOffreEmploi | null = null;

  recruteursSharedCollection: IRecruteur[] = [];
  typeContratsSharedCollection: ITypeContrat[] = [];
  postesSharedCollection: IPoste[] = [];
  localisationsSharedCollection: ILocalisation[] = [];

  protected offreEmploiService = inject(OffreEmploiService);
  protected offreEmploiFormService = inject(OffreEmploiFormService);
  protected recruteurService = inject(RecruteurService);
  protected typeContratService = inject(TypeContratService);
  protected posteService = inject(PosteService);
  protected localisationService = inject(LocalisationService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: OffreEmploiFormGroup = this.offreEmploiFormService.createOffreEmploiFormGroup();

  compareRecruteur = (o1: IRecruteur | null, o2: IRecruteur | null): boolean => this.recruteurService.compareRecruteur(o1, o2);

  compareTypeContrat = (o1: ITypeContrat | null, o2: ITypeContrat | null): boolean => this.typeContratService.compareTypeContrat(o1, o2);

  comparePoste = (o1: IPoste | null, o2: IPoste | null): boolean => this.posteService.comparePoste(o1, o2);

  compareLocalisation = (o1: ILocalisation | null, o2: ILocalisation | null): boolean =>
    this.localisationService.compareLocalisation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offreEmploi }) => {
      this.offreEmploi = offreEmploi;
      if (offreEmploi) {
        this.updateForm(offreEmploi);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offreEmploi = this.offreEmploiFormService.getOffreEmploi(this.editForm);
    if (offreEmploi.id !== null) {
      this.subscribeToSaveResponse(this.offreEmploiService.update(offreEmploi));
    } else {
      this.subscribeToSaveResponse(this.offreEmploiService.create(offreEmploi));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffreEmploi>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(offreEmploi: IOffreEmploi): void {
    this.offreEmploi = offreEmploi;
    this.offreEmploiFormService.resetForm(this.editForm, offreEmploi);

    this.recruteursSharedCollection = this.recruteurService.addRecruteurToCollectionIfMissing<IRecruteur>(
      this.recruteursSharedCollection,
      offreEmploi.recruteur,
    );
    this.typeContratsSharedCollection = this.typeContratService.addTypeContratToCollectionIfMissing<ITypeContrat>(
      this.typeContratsSharedCollection,
      offreEmploi.typeContrat,
    );
    this.postesSharedCollection = this.posteService.addPosteToCollectionIfMissing<IPoste>(this.postesSharedCollection, offreEmploi.poste);
    this.localisationsSharedCollection = this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(
      this.localisationsSharedCollection,
      offreEmploi.localisation,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.recruteurService
      .query()
      .pipe(map((res: HttpResponse<IRecruteur[]>) => res.body ?? []))
      .pipe(
        map((recruteurs: IRecruteur[]) =>
          this.recruteurService.addRecruteurToCollectionIfMissing<IRecruteur>(recruteurs, this.offreEmploi?.recruteur),
        ),
      )
      .subscribe((recruteurs: IRecruteur[]) => (this.recruteursSharedCollection = recruteurs));

    this.typeContratService
      .query()
      .pipe(map((res: HttpResponse<ITypeContrat[]>) => res.body ?? []))
      .pipe(
        map((typeContrats: ITypeContrat[]) =>
          this.typeContratService.addTypeContratToCollectionIfMissing<ITypeContrat>(typeContrats, this.offreEmploi?.typeContrat),
        ),
      )
      .subscribe((typeContrats: ITypeContrat[]) => (this.typeContratsSharedCollection = typeContrats));

    this.posteService
      .query()
      .pipe(map((res: HttpResponse<IPoste[]>) => res.body ?? []))
      .pipe(map((postes: IPoste[]) => this.posteService.addPosteToCollectionIfMissing<IPoste>(postes, this.offreEmploi?.poste)))
      .subscribe((postes: IPoste[]) => (this.postesSharedCollection = postes));

    this.localisationService
      .query()
      .pipe(map((res: HttpResponse<ILocalisation[]>) => res.body ?? []))
      .pipe(
        map((localisations: ILocalisation[]) =>
          this.localisationService.addLocalisationToCollectionIfMissing<ILocalisation>(localisations, this.offreEmploi?.localisation),
        ),
      )
      .subscribe((localisations: ILocalisation[]) => (this.localisationsSharedCollection = localisations));
  }
}
