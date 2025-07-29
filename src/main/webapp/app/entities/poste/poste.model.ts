export interface IPoste {
  id: number;
  nom?: string | null;
}

export type NewPoste = Omit<IPoste, 'id'> & { id: null };
