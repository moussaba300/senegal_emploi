import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPoste } from '../poste.model';
import { PosteService } from '../service/poste.service';
import { PosteFormGroup, PosteFormService } from './poste-form.service';

@Component({
  selector: 'jhi-poste-update',
  templateUrl: './poste-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PosteUpdateComponent implements OnInit {
  isSaving = false;
  poste: IPoste | null = null;

  protected posteService = inject(PosteService);
  protected posteFormService = inject(PosteFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PosteFormGroup = this.posteFormService.createPosteFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ poste }) => {
      this.poste = poste;
      if (poste) {
        this.updateForm(poste);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const poste = this.posteFormService.getPoste(this.editForm);
    if (poste.id !== null) {
      this.subscribeToSaveResponse(this.posteService.update(poste));
    } else {
      this.subscribeToSaveResponse(this.posteService.create(poste));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoste>>): void {
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

  protected updateForm(poste: IPoste): void {
    this.poste = poste;
    this.posteFormService.resetForm(this.editForm, poste);
  }
}
