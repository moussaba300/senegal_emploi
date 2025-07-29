import dayjs from 'dayjs/esm';

import { ICandidature, NewCandidature } from './candidature.model';

export const sampleWithRequiredData: ICandidature = {
  id: 28558,
};

export const sampleWithPartialData: ICandidature = {
  id: 13073,
  dateDepot: dayjs('2025-07-28T22:59'),
};

export const sampleWithFullData: ICandidature = {
  id: 23153,
  dateDepot: dayjs('2025-07-29T16:29'),
  statut: 'gentle abaft actually',
};

export const sampleWithNewData: NewCandidature = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
