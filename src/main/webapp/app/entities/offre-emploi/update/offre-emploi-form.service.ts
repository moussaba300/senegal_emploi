import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOffreEmploi, NewOffreEmploi } from '../offre-emploi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOffreEmploi for edit and NewOffreEmploiFormGroupInput for create.
 */
type OffreEmploiFormGroupInput = IOffreEmploi | PartialWithRequiredKeyOf<NewOffreEmploi>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOffreEmploi | NewOffreEmploi> = Omit<T, 'datePublication'> & {
  datePublication?: string | null;
};

type OffreEmploiFormRawValue = FormValueOf<IOffreEmploi>;

type NewOffreEmploiFormRawValue = FormValueOf<NewOffreEmploi>;

type OffreEmploiFormDefaults = Pick<NewOffreEmploi, 'id' | 'datePublication'>;

type OffreEmploiFormGroupContent = {
  id: FormControl<OffreEmploiFormRawValue['id'] | NewOffreEmploi['id']>;
  titre: FormControl<OffreEmploiFormRawValue['titre']>;
  description: FormControl<OffreEmploiFormRawValue['description']>;
  remuneration: FormControl<OffreEmploiFormRawValue['remuneration']>;
  datePublication: FormControl<OffreEmploiFormRawValue['datePublication']>;
  imagePath: FormControl<OffreEmploiFormRawValue['imagePath']>;
  recruteur: FormControl<OffreEmploiFormRawValue['recruteur']>;
  typeContrat: FormControl<OffreEmploiFormRawValue['typeContrat']>;
  poste: FormControl<OffreEmploiFormRawValue['poste']>;
  localisation: FormControl<OffreEmploiFormRawValue['localisation']>;
};

export type OffreEmploiFormGroup = FormGroup<OffreEmploiFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OffreEmploiFormService {
  createOffreEmploiFormGroup(offreEmploi: OffreEmploiFormGroupInput = { id: null }): OffreEmploiFormGroup {
    const offreEmploiRawValue = this.convertOffreEmploiToOffreEmploiRawValue({
      ...this.getFormDefaults(),
      ...offreEmploi,
    });
    return new FormGroup<OffreEmploiFormGroupContent>({
      id: new FormControl(
        { value: offreEmploiRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titre: new FormControl(offreEmploiRawValue.titre, {
        validators: [Validators.required],
      }),
      description: new FormControl(offreEmploiRawValue.description, {
        validators: [Validators.required],
      }),
      remuneration: new FormControl(offreEmploiRawValue.remuneration),
      datePublication: new FormControl(offreEmploiRawValue.datePublication),
      imagePath: new FormControl(offreEmploiRawValue.imagePath),
      recruteur: new FormControl(offreEmploiRawValue.recruteur),
      typeContrat: new FormControl(offreEmploiRawValue.typeContrat),
      poste: new FormControl(offreEmploiRawValue.poste),
      localisation: new FormControl(offreEmploiRawValue.localisation),
    });
  }

  getOffreEmploi(form: OffreEmploiFormGroup): IOffreEmploi | NewOffreEmploi {
    return this.convertOffreEmploiRawValueToOffreEmploi(form.getRawValue() as OffreEmploiFormRawValue | NewOffreEmploiFormRawValue);
  }

  resetForm(form: OffreEmploiFormGroup, offreEmploi: OffreEmploiFormGroupInput): void {
    const offreEmploiRawValue = this.convertOffreEmploiToOffreEmploiRawValue({ ...this.getFormDefaults(), ...offreEmploi });
    form.reset(
      {
        ...offreEmploiRawValue,
        id: { value: offreEmploiRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OffreEmploiFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      datePublication: currentTime,
    };
  }

  private convertOffreEmploiRawValueToOffreEmploi(
    rawOffreEmploi: OffreEmploiFormRawValue | NewOffreEmploiFormRawValue,
  ): IOffreEmploi | NewOffreEmploi {
    return {
      ...rawOffreEmploi,
      datePublication: dayjs(rawOffreEmploi.datePublication, DATE_TIME_FORMAT),
    };
  }

  private convertOffreEmploiToOffreEmploiRawValue(
    offreEmploi: IOffreEmploi | (Partial<NewOffreEmploi> & OffreEmploiFormDefaults),
  ): OffreEmploiFormRawValue | PartialWithRequiredKeyOf<NewOffreEmploiFormRawValue> {
    return {
      ...offreEmploi,
      datePublication: offreEmploi.datePublication ? offreEmploi.datePublication.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
