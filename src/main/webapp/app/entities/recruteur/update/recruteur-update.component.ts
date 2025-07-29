import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUtilisateur } from 'app/entities/utilisateur/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur/service/utilisateur.service';
import { IRecruteur } from '../recruteur.model';
import { RecruteurService } from '../service/recruteur.service';
import { RecruteurFormGroup, RecruteurFormService } from './recruteur-form.service';

@Component({
  selector: 'jhi-recruteur-update',
  templateUrl: './recruteur-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RecruteurUpdateComponent implements OnInit {
  isSaving = false;
  recruteur: IRecruteur | null = null;

  utilisateursCollection: IUtilisateur[] = [];

  protected recruteurService = inject(RecruteurService);
  protected recruteurFormService = inject(RecruteurFormService);
  protected utilisateurService = inject(UtilisateurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RecruteurFormGroup = this.recruteurFormService.createRecruteurFormGroup();

  compareUtilisateur = (o1: IUtilisateur | null, o2: IUtilisateur | null): boolean => this.utilisateurService.compareUtilisateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ recruteur }) => {
      this.recruteur = recruteur;
      if (recruteur) {
        this.updateForm(recruteur);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const recruteur = this.recruteurFormService.getRecruteur(this.editForm);
    if (recruteur.id !== null) {
      this.subscribeToSaveResponse(this.recruteurService.update(recruteur));
    } else {
      this.subscribeToSaveResponse(this.recruteurService.create(recruteur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecruteur>>): void {
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

  protected updateForm(recruteur: IRecruteur): void {
    this.recruteur = recruteur;
    this.recruteurFormService.resetForm(this.editForm, recruteur);

    this.utilisateursCollection = this.utilisateurService.addUtilisateurToCollectionIfMissing<IUtilisateur>(
      this.utilisateursCollection,
      recruteur.utilisateur,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.utilisateurService
      .query({ filter: 'recruteur-is-null' })
      .pipe(map((res: HttpResponse<IUtilisateur[]>) => res.body ?? []))
      .pipe(
        map((utilisateurs: IUtilisateur[]) =>
          this.utilisateurService.addUtilisateurToCollectionIfMissing<IUtilisateur>(utilisateurs, this.recruteur?.utilisateur),
        ),
      )
      .subscribe((utilisateurs: IUtilisateur[]) => (this.utilisateursCollection = utilisateurs));
  }
}
