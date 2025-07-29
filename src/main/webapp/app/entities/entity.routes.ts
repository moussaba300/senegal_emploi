import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'senegalEmploiApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'utilisateur',
    data: { pageTitle: 'senegalEmploiApp.utilisateur.home.title' },
    loadChildren: () => import('./utilisateur/utilisateur.routes'),
  },
  {
    path: 'candidat',
    data: { pageTitle: 'senegalEmploiApp.candidat.home.title' },
    loadChildren: () => import('./candidat/candidat.routes'),
  },
  {
    path: 'recruteur',
    data: { pageTitle: 'senegalEmploiApp.recruteur.home.title' },
    loadChildren: () => import('./recruteur/recruteur.routes'),
  },
  {
    path: 'type-contrat',
    data: { pageTitle: 'senegalEmploiApp.typeContrat.home.title' },
    loadChildren: () => import('./type-contrat/type-contrat.routes'),
  },
  {
    path: 'poste',
    data: { pageTitle: 'senegalEmploiApp.poste.home.title' },
    loadChildren: () => import('./poste/poste.routes'),
  },
  {
    path: 'localisation',
    data: { pageTitle: 'senegalEmploiApp.localisation.home.title' },
    loadChildren: () => import('./localisation/localisation.routes'),
  },
  {
    path: 'offre-emploi',
    data: { pageTitle: 'senegalEmploiApp.offreEmploi.home.title' },
    loadChildren: () => import('./offre-emploi/offre-emploi.routes'),
  },
  {
    path: 'candidature',
    data: { pageTitle: 'senegalEmploiApp.candidature.home.title' },
    loadChildren: () => import('./candidature/candidature.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
