import React from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Risk from './risk';
import Project from './project';
import UserGamification from "./user-gamification";
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <Route
        path="/:url*(/+)"
        exact
        strict
        render={({ location }) => (
          <Redirect to={location.pathname.replace(/\/+$/, "")} />
        )}
      />
      {/* Removes duplicate slashes in the middle of the URL */}
      <Route
        path="/:url(.*//+.*)"
        exact
        strict
        render={() => (
          <Redirect to={`/${match.params.url.replace(/\/\/+/, "/")}`} />
        )}
      />
      <ErrorBoundaryRoute path={`${match.url}/risk`} component={Risk} />
      <ErrorBoundaryRoute path={`${match.url}/project`} component={Project} />
      <ErrorBoundaryRoute path={`${match.url}/user-gamifications`} component={UserGamification} />

      <Redirect from="//*" to="/*" />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
