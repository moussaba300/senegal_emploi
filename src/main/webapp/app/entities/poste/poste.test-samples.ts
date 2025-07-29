import { IPoste, NewPoste } from './poste.model';

export const sampleWithRequiredData: IPoste = {
  id: 21776,
  nom: 'consequently splash',
};

export const sampleWithPartialData: IPoste = {
  id: 1948,
  nom: 'kookily',
};

export const sampleWithFullData: IPoste = {
  id: 17229,
  nom: 'phooey concrete',
};

export const sampleWithNewData: NewPoste = {
  nom: 'convalesce',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
