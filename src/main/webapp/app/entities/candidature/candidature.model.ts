import dayjs from 'dayjs/esm';
import { IOffreEmploi } from 'app/entities/offre-emploi/offre-emploi.model';
import { ICandidat } from 'app/entities/candidat/candidat.model';

export interface ICandidature {
  id: number;
  dateDepot?: dayjs.Dayjs | null;
  statut?: string | null;
  offre?: Pick<IOffreEmploi, 'id'> | null;
  candidat?: Pick<ICandidat, 'id'> | null;
}

export type NewCandidature = Omit<ICandidature, 'id'> & { id: null };
