import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUtilisateur } from 'app/entities/utilisateur/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur/service/utilisateur.service';
import { ICandidat } from '../candidat.model';
import { CandidatService } from '../service/candidat.service';
import { CandidatFormGroup, CandidatFormService } from './candidat-form.service';

@Component({
  selector: 'jhi-candidat-update',
  templateUrl: './candidat-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CandidatUpdateComponent implements OnInit {
  isSaving = false;
  candidat: ICandidat | null = null;

  utilisateursCollection: IUtilisateur[] = [];

  protected candidatService = inject(CandidatService);
  protected candidatFormService = inject(CandidatFormService);
  protected utilisateurService = inject(UtilisateurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CandidatFormGroup = this.candidatFormService.createCandidatFormGroup();

  compareUtilisateur = (o1: IUtilisateur | null, o2: IUtilisateur | null): boolean => this.utilisateurService.compareUtilisateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ candidat }) => {
      this.candidat = candidat;
      if (candidat) {
        this.updateForm(candidat);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const candidat = this.candidatFormService.getCandidat(this.editForm);
    if (candidat.id !== null) {
      this.subscribeToSaveResponse(this.candidatService.update(candidat));
    } else {
      this.subscribeToSaveResponse(this.candidatService.create(candidat));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidat>>): void {
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

  protected updateForm(candidat: ICandidat): void {
    this.candidat = candidat;
    this.candidatFormService.resetForm(this.editForm, candidat);

    this.utilisateursCollection = this.utilisateurService.addUtilisateurToCollectionIfMissing<IUtilisateur>(
      this.utilisateursCollection,
      candidat.utilisateur,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.utilisateurService
      .query({ filter: 'candidat-is-null' })
      .pipe(map((res: HttpResponse<IUtilisateur[]>) => res.body ?? []))
      .pipe(
        map((utilisateurs: IUtilisateur[]) =>
          this.utilisateurService.addUtilisateurToCollectionIfMissing<IUtilisateur>(utilisateurs, this.candidat?.utilisateur),
        ),
      )
      .subscribe((utilisateurs: IUtilisateur[]) => (this.utilisateursCollection = utilisateurs));
  }
}
