import { IUtilisateur } from 'app/entities/utilisateur/utilisateur.model';

export interface IRecruteur {
  id: number;
  entreprise?: string | null;
  secteur?: string | null;
  logoPath?: string | null;
  utilisateur?: Pick<IUtilisateur, 'id'> | null;
}

export type NewRecruteur = Omit<IRecruteur, 'id'> & { id: null };
