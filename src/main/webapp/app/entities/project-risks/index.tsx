import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectRisks from './project-risks';
import ProjectRisksDetail from './project-risks-detail';
import ProjectRisksUpdate from './project-risks-update';
import ProjectRisksDeleteDialog from './project-risks-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectRisksUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectRisksUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectRisksDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectRisks} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProjectRisksDeleteDialog} />
  </>
);

export default Routes;
