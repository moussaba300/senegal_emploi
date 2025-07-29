export interface IUtilisateur {
  id: number;
  nom?: string | null;
  prenom?: string | null;
  email?: string | null;
  motDePasse?: string | null;
  role?: string | null;
}

export type NewUtilisateur = Omit<IUtilisateur, 'id'> & { id: null };
