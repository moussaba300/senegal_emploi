import { ITypeContrat, NewTypeContrat } from './type-contrat.model';

export const sampleWithRequiredData: ITypeContrat = {
  id: 19173,
  libelle: 'whale wetly',
};

export const sampleWithPartialData: ITypeContrat = {
  id: 18016,
  libelle: 'kettledrum',
};

export const sampleWithFullData: ITypeContrat = {
  id: 1815,
  libelle: 'with trolley',
};

export const sampleWithNewData: NewTypeContrat = {
  libelle: 'incidentally large',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
