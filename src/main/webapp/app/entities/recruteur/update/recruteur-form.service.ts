import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IRecruteur, NewRecruteur } from '../recruteur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRecruteur for edit and NewRecruteurFormGroupInput for create.
 */
type RecruteurFormGroupInput = IRecruteur | PartialWithRequiredKeyOf<NewRecruteur>;

type RecruteurFormDefaults = Pick<NewRecruteur, 'id'>;

type RecruteurFormGroupContent = {
  id: FormControl<IRecruteur['id'] | NewRecruteur['id']>;
  entreprise: FormControl<IRecruteur['entreprise']>;
  secteur: FormControl<IRecruteur['secteur']>;
  logoPath: FormControl<IRecruteur['logoPath']>;
  utilisateur: FormControl<IRecruteur['utilisateur']>;
};

export type RecruteurFormGroup = FormGroup<RecruteurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RecruteurFormService {
  createRecruteurFormGroup(recruteur: RecruteurFormGroupInput = { id: null }): RecruteurFormGroup {
    const recruteurRawValue = {
      ...this.getFormDefaults(),
      ...recruteur,
    };
    return new FormGroup<RecruteurFormGroupContent>({
      id: new FormControl(
        { value: recruteurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      entreprise: new FormControl(recruteurRawValue.entreprise, {
        validators: [Validators.required],
      }),
      secteur: new FormControl(recruteurRawValue.secteur, {
        validators: [Validators.required],
      }),
      logoPath: new FormControl(recruteurRawValue.logoPath),
      utilisateur: new FormControl(recruteurRawValue.utilisateur),
    });
  }

  getRecruteur(form: RecruteurFormGroup): IRecruteur | NewRecruteur {
    return form.getRawValue() as IRecruteur | NewRecruteur;
  }

  resetForm(form: RecruteurFormGroup, recruteur: RecruteurFormGroupInput): void {
    const recruteurRawValue = { ...this.getFormDefaults(), ...recruteur };
    form.reset(
      {
        ...recruteurRawValue,
        id: { value: recruteurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RecruteurFormDefaults {
    return {
      id: null,
    };
  }
}
