import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserGamification from './user-gamification';
import UserGamificationDetail from './user-gamification-detail';
import UserGamificationUpdate from './user-gamification-update';
import UserGamificationDeleteDialog from './user-gamification-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserGamificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserGamificationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserGamificationDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserGamification} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserGamificationDeleteDialog} />
  </>
);

export default Routes;
