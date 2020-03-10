import React from 'react';
import { Switch, Redirect } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Project from './project';
import ProjectDetail from './project-detail';
import ProjectUpdate from './project-update';
import ProjectDeleteDialog from './project-delete-dialog';
import ProjectRisksUpdate from "app/entities/project-risks/project-risks-update";
import ProjectRisksDetail from "app/entities/project-risks/project-risks-detail";
import ProjectRisks from "app/entities/project-risks/project-risks";
import ProjectRisksDeleteDialog from "app/entities/project-risks/project-risks-delete-dialog";
import RiskResponseUpdate from "app/entities/risk-response/risk-response-update";
import RiskResponseDetail from "app/entities/risk-response/risk-response-detail";
import RiskResponseDeleteDialog from "app/entities/risk-response/risk-response-delete-dialog";

const Routes = ({ match }) => (
  <>

    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjectDeleteDialog} />
      <ErrorBoundaryRoute path={`${match.url}/:id/:risktype?/project-risks/:riskId/delete`} component={ProjectRisksDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/edit`} component={ProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?`} component={ProjectDetail} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/project-risks/:riskId/response/new`} component={RiskResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/project-risks/:riskId/response/:responseId/edit`} component={RiskResponseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/project-risks/:riskId/response/:responseId`} component={RiskResponseDetail} />
      <ErrorBoundaryRoute path={`${match.url}/:id/:risktype?/project-risks/:riskId/response/:responseId/delete`} component={RiskResponseDeleteDialog} />

      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/project-risks/new`} component={ProjectRisksUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/project-risks/:riskId/edit`} component={ProjectRisksUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/:risktype?/project-risks/:riskId`} component={ProjectRisksDetail} />
      <ErrorBoundaryRoute path={`${match.url}/:id/:risktype?/project-risks/:riskId`} component={ProjectRisks} />
      <ErrorBoundaryRoute path={`${match.url}/:id/:risktype?/project-risks`} component={ProjectRisks} />
      <ErrorBoundaryRoute path={`${match.url}/:id/:risktype?/project-risks/:riskId/delete`} component={ProjectRisksDeleteDialog} />

      <ErrorBoundaryRoute path={match.url} component={Project} />
    </Switch>

  </>
);

export default Routes;
