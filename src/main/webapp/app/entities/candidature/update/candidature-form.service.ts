import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICandidature, NewCandidature } from '../candidature.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICandidature for edit and NewCandidatureFormGroupInput for create.
 */
type CandidatureFormGroupInput = ICandidature | PartialWithRequiredKeyOf<NewCandidature>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICandidature | NewCandidature> = Omit<T, 'dateDepot'> & {
  dateDepot?: string | null;
};

type CandidatureFormRawValue = FormValueOf<ICandidature>;

type NewCandidatureFormRawValue = FormValueOf<NewCandidature>;

type CandidatureFormDefaults = Pick<NewCandidature, 'id' | 'dateDepot'>;

type CandidatureFormGroupContent = {
  id: FormControl<CandidatureFormRawValue['id'] | NewCandidature['id']>;
  dateDepot: FormControl<CandidatureFormRawValue['dateDepot']>;
  statut: FormControl<CandidatureFormRawValue['statut']>;
  offre: FormControl<CandidatureFormRawValue['offre']>;
  candidat: FormControl<CandidatureFormRawValue['candidat']>;
};

export type CandidatureFormGroup = FormGroup<CandidatureFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CandidatureFormService {
  createCandidatureFormGroup(candidature: CandidatureFormGroupInput = { id: null }): CandidatureFormGroup {
    const candidatureRawValue = this.convertCandidatureToCandidatureRawValue({
      ...this.getFormDefaults(),
      ...candidature,
    });
    return new FormGroup<CandidatureFormGroupContent>({
      id: new FormControl(
        { value: candidatureRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dateDepot: new FormControl(candidatureRawValue.dateDepot),
      statut: new FormControl(candidatureRawValue.statut),
      offre: new FormControl(candidatureRawValue.offre),
      candidat: new FormControl(candidatureRawValue.candidat),
    });
  }

  getCandidature(form: CandidatureFormGroup): ICandidature | NewCandidature {
    return this.convertCandidatureRawValueToCandidature(form.getRawValue() as CandidatureFormRawValue | NewCandidatureFormRawValue);
  }

  resetForm(form: CandidatureFormGroup, candidature: CandidatureFormGroupInput): void {
    const candidatureRawValue = this.convertCandidatureToCandidatureRawValue({ ...this.getFormDefaults(), ...candidature });
    form.reset(
      {
        ...candidatureRawValue,
        id: { value: candidatureRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CandidatureFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dateDepot: currentTime,
    };
  }

  private convertCandidatureRawValueToCandidature(
    rawCandidature: CandidatureFormRawValue | NewCandidatureFormRawValue,
  ): ICandidature | NewCandidature {
    return {
      ...rawCandidature,
      dateDepot: dayjs(rawCandidature.dateDepot, DATE_TIME_FORMAT),
    };
  }

  private convertCandidatureToCandidatureRawValue(
    candidature: ICandidature | (Partial<NewCandidature> & CandidatureFormDefaults),
  ): CandidatureFormRawValue | PartialWithRequiredKeyOf<NewCandidatureFormRawValue> {
    return {
      ...candidature,
      dateDepot: candidature.dateDepot ? candidature.dateDepot.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
