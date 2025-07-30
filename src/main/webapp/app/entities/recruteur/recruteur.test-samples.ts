import { IRecruteur, NewRecruteur } from './recruteur.model';

export const sampleWithRequiredData: IRecruteur = {
  id: 7612,
  entreprise: 'coin',
  secteur: 'rusty while woot',
};

export const sampleWithPartialData: IRecruteur = {
  id: 17427,
  entreprise: 'yippee',
  secteur: 'solemnly diver unrealistic',
};

export const sampleWithFullData: IRecruteur = {
  id: 22999,
  entreprise: 'into circumnavigate',
  secteur: 'inasmuch really',
  logoPath: 'transcend arrogantly',
};

export const sampleWithNewData: NewRecruteur = {
  entreprise: 'whoa',
  secteur: 'utterly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
