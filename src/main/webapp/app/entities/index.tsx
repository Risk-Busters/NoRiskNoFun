import React from 'react';
import { Switch, Redirect } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Risk from './risk';
import RiskResponse from './risk-response';
import Project from './project';
import ProjectRisks from './project-risks';
import UserGamification from "./user-gamification";
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/risk`} component={Risk} />
      <ErrorBoundaryRoute path={`${match.url}/risk-response`} component={RiskResponse} />
      <ErrorBoundaryRoute path={`${match.url}/project`} component={Project} />
      <ErrorBoundaryRoute path={`${match.url}/project-risks`} component={ProjectRisks} />
      <ErrorBoundaryRoute path={`${match.url}/user-gamifications`} component={UserGamification} />

      <Redirect from="//*" to="/*" />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
