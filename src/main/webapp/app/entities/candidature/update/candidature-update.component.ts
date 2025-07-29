import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IOffreEmploi } from 'app/entities/offre-emploi/offre-emploi.model';
import { OffreEmploiService } from 'app/entities/offre-emploi/service/offre-emploi.service';
import { ICandidat } from 'app/entities/candidat/candidat.model';
import { CandidatService } from 'app/entities/candidat/service/candidat.service';
import { CandidatureService } from '../service/candidature.service';
import { ICandidature } from '../candidature.model';
import { CandidatureFormGroup, CandidatureFormService } from './candidature-form.service';

@Component({
  selector: 'jhi-candidature-update',
  templateUrl: './candidature-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CandidatureUpdateComponent implements OnInit {
  isSaving = false;
  candidature: ICandidature | null = null;

  offreEmploisSharedCollection: IOffreEmploi[] = [];
  candidatsSharedCollection: ICandidat[] = [];

  protected candidatureService = inject(CandidatureService);
  protected candidatureFormService = inject(CandidatureFormService);
  protected offreEmploiService = inject(OffreEmploiService);
  protected candidatService = inject(CandidatService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CandidatureFormGroup = this.candidatureFormService.createCandidatureFormGroup();

  compareOffreEmploi = (o1: IOffreEmploi | null, o2: IOffreEmploi | null): boolean => this.offreEmploiService.compareOffreEmploi(o1, o2);

  compareCandidat = (o1: ICandidat | null, o2: ICandidat | null): boolean => this.candidatService.compareCandidat(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidature }) => {
      this.candidature = candidature;
      if (candidature) {
        this.updateForm(candidature);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidature = this.candidatureFormService.getCandidature(this.editForm);
    if (candidature.id !== null) {
      this.subscribeToSaveResponse(this.candidatureService.update(candidature));
    } else {
      this.subscribeToSaveResponse(this.candidatureService.create(candidature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidature>>): void {
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

  protected updateForm(candidature: ICandidature): void {
    this.candidature = candidature;
    this.candidatureFormService.resetForm(this.editForm, candidature);

    this.offreEmploisSharedCollection = this.offreEmploiService.addOffreEmploiToCollectionIfMissing<IOffreEmploi>(
      this.offreEmploisSharedCollection,
      candidature.offre,
    );
    this.candidatsSharedCollection = this.candidatService.addCandidatToCollectionIfMissing<ICandidat>(
      this.candidatsSharedCollection,
      candidature.candidat,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.offreEmploiService
      .query()
      .pipe(map((res: HttpResponse<IOffreEmploi[]>) => res.body ?? []))
      .pipe(
        map((offreEmplois: IOffreEmploi[]) =>
          this.offreEmploiService.addOffreEmploiToCollectionIfMissing<IOffreEmploi>(offreEmplois, this.candidature?.offre),
        ),
      )
      .subscribe((offreEmplois: IOffreEmploi[]) => (this.offreEmploisSharedCollection = offreEmplois));

    this.candidatService
      .query()
      .pipe(map((res: HttpResponse<ICandidat[]>) => res.body ?? []))
      .pipe(
        map((candidats: ICandidat[]) =>
          this.candidatService.addCandidatToCollectionIfMissing<ICandidat>(candidats, this.candidature?.candidat),
        ),
      )
      .subscribe((candidats: ICandidat[]) => (this.candidatsSharedCollection = candidats));
  }
}
