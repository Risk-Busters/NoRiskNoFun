import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Activity from './activity';
import ActivityDetail from './activity-detail';
import ActivityDeleteDialog from './activity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ActivityDetail} />
      <ErrorBoundaryRoute path={match.url} component={Activity} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ActivityDeleteDialog} />
  </>
);

export default Routes;
