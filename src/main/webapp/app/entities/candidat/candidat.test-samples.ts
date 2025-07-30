import { ICandidat, NewCandidat } from './candidat.model';

export const sampleWithRequiredData: ICandidat = {
  id: 18880,
};

export const sampleWithPartialData: ICandidat = {
  id: 11540,
  cv: 'corral quirkily aw',
  telephone: '986.637.8232 x791',
  adresse: 'yum neat enormously',
};

export const sampleWithFullData: ICandidat = {
  id: 11710,
  cv: 'nor oddly because',
  telephone: '1-362-624-3391 x967',
  adresse: 'how phew airbrush',
  photoPath: 'wasteful mean',
};

export const sampleWithNewData: NewCandidat = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
