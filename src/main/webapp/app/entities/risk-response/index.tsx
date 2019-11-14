import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RiskResponse from './risk-response';
import RiskResponseDetail from './risk-response-detail';
import RiskResponseUpdate from './risk-response-update';
import RiskResponseDeleteDialog from './risk-response-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RiskResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RiskResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RiskResponseDetail} />
      <ErrorBoundaryRoute path={match.url} component={RiskResponse} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RiskResponseDeleteDialog} />
  </>
);

export default Routes;
