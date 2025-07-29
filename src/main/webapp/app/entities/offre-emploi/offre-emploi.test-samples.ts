import dayjs from 'dayjs/esm';

import { IOffreEmploi, NewOffreEmploi } from './offre-emploi.model';

export const sampleWithRequiredData: IOffreEmploi = {
  id: 11534,
  titre: 'till',
  description: 'until',
};

export const sampleWithPartialData: IOffreEmploi = {
  id: 23706,
  titre: 'amnesty pleasure off',
  description: 'into',
  remuneration: 6957.41,
  datePublication: dayjs('2025-07-29T07:37'),
};

export const sampleWithFullData: IOffreEmploi = {
  id: 19852,
  titre: 'innovate gah',
  description: 'intrepid for',
  remuneration: 16229.21,
  datePublication: dayjs('2025-07-29T07:46'),
};

export const sampleWithNewData: NewOffreEmploi = {
  titre: 'yum in for',
  description: 'along although stormy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
