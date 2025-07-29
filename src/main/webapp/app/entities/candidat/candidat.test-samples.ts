import { ICandidat, NewCandidat } from './candidat.model';

export const sampleWithRequiredData: ICandidat = {
  id: 18880,
};

export const sampleWithPartialData: ICandidat = {
  id: 29576,
  cv: 'ugh woot',
  telephone: '1-300-276-5498 x6637',
  adresse: 'submitter yum neat',
};

export const sampleWithFullData: ICandidat = {
  id: 11710,
  cv: 'nor oddly because',
  telephone: '1-362-624-3391 x967',
  adresse: 'how phew airbrush',
};

export const sampleWithNewData: NewCandidat = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
