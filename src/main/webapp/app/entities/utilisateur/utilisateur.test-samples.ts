import { IUtilisateur, NewUtilisateur } from './utilisateur.model';

export const sampleWithRequiredData: IUtilisateur = {
  id: 7635,
  nom: 'whirlwind optimistically',
  prenom: 'inwardly',
  email: 'Elisha.Veum@gmail.com',
  motDePasse: 'snappy blank toward',
  role: 'whenever tinderbox',
};

export const sampleWithPartialData: IUtilisateur = {
  id: 24666,
  nom: 'ack without',
  prenom: 'gurn splosh',
  email: 'Kayden.Hoppe@hotmail.com',
  motDePasse: 'cork major beside',
  role: 'agitated whoever',
};

export const sampleWithFullData: IUtilisateur = {
  id: 18250,
  nom: 'since across',
  prenom: 'sizzle',
  email: 'Violette.Schiller@hotmail.com',
  motDePasse: 'woot',
  role: 'wisely quit',
};

export const sampleWithNewData: NewUtilisateur = {
  nom: 'hot',
  prenom: 'even coarse',
  email: 'Mary_Prohaska@yahoo.com',
  motDePasse: 'recent regal',
  role: 'past which searchingly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
