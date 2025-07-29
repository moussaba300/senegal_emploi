import { IRecruteur, NewRecruteur } from './recruteur.model';

export const sampleWithRequiredData: IRecruteur = {
  id: 7612,
};

export const sampleWithPartialData: IRecruteur = {
  id: 5697,
};

export const sampleWithFullData: IRecruteur = {
  id: 22999,
  entreprise: 'into circumnavigate',
  secteur: 'inasmuch really',
};

export const sampleWithNewData: NewRecruteur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
