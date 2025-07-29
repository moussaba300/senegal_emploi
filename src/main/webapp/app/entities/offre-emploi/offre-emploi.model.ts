import dayjs from 'dayjs/esm';
import { IRecruteur } from 'app/entities/recruteur/recruteur.model';
import { ITypeContrat } from 'app/entities/type-contrat/type-contrat.model';
import { IPoste } from 'app/entities/poste/poste.model';
import { ILocalisation } from 'app/entities/localisation/localisation.model';

export interface IOffreEmploi {
  id: number;
  titre?: string | null;
  description?: string | null;
  remuneration?: number | null;
  datePublication?: dayjs.Dayjs | null;
  recruteur?: Pick<IRecruteur, 'id'> | null;
  typeContrat?: Pick<ITypeContrat, 'id'> | null;
  poste?: Pick<IPoste, 'id'> | null;
  localisation?: Pick<ILocalisation, 'id'> | null;
}

export type NewOffreEmploi = Omit<IOffreEmploi, 'id'> & { id: null };
