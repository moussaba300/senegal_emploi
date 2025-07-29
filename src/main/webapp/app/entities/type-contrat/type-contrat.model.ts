export interface ITypeContrat {
  id: number;
  libelle?: string | null;
}

export type NewTypeContrat = Omit<ITypeContrat, 'id'> & { id: null };
