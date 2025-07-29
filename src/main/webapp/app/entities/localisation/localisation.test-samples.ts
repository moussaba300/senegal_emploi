import { ILocalisation, NewLocalisation } from './localisation.model';

export const sampleWithRequiredData: ILocalisation = {
  id: 24192,
  region: 'um yet',
  departement: 'venture worth',
};

export const sampleWithPartialData: ILocalisation = {
  id: 20312,
  region: 'suburban energetically likewise',
  departement: 'gorgeous heating too',
};

export const sampleWithFullData: ILocalisation = {
  id: 26734,
  region: 'ack capitalize kiddingly',
  departement: 'hyena',
};

export const sampleWithNewData: NewLocalisation = {
  region: 'request',
  departement: 'pfft whenever buzzing',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
