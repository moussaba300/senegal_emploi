export interface ILocalisation {
  id: number;
  region?: string | null;
  departement?: string | null;
}

export type NewLocalisation = Omit<ILocalisation, 'id'> & { id: null };
