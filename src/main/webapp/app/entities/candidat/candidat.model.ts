import { IUtilisateur } from 'app/entities/utilisateur/utilisateur.model';

export interface ICandidat {
  id: number;
  cv?: string | null;
  telephone?: string | null;
  adresse?: string | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewCandidat = Omit<ICandidat, 'id'> & { id: null };
