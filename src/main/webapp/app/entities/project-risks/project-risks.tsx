import React, {useEffect, useState} from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import {getEntities, getProposedProjectRisks, getToBeDiscussedProjectRisks} from "app/entities/project-risks/project-risks.reducer";

type ProjectRisksProps = {
  riskDiscussionStatus: string
};

export interface IProjectRisksProps extends StateProps, DispatchProps, ProjectRisksProps, RouteComponentProps<{ id: string }> {}

function ProjectRisks(props: IProjectRisksProps) {

  const [riskList, setRiskList] = useState([]);

  useEffect(() => {
    if(props.riskDiscussionStatus === "proposed") {
      props.getProposedProjectRisks();
    } else if(props.riskDiscussionStatus === "toBeDiscussed") {
      props.getToBeDiscussedProjectRisks();
    } else if(props.riskDiscussionStatus === "final") {
      props.getEntities();
    }
  }, []);

  useEffect(() => {
    if(props.riskDiscussionStatus === "proposed") {
      setRiskList(Array.from(props.proposedProjectRiskEntities));
    } else if(props.riskDiscussionStatus === "toBeDiscussed") {
      setRiskList(Array.from(props.toBeDiscussedProjectRiskEntities));
    } else if(props.riskDiscussionStatus === "final") {
      setRiskList(Array.from(props.projectRisksList));
    }
  }, [props.projectRisksList, props.proposedProjectRiskEntities, props.toBeDiscussedProjectRiskEntities]);

  const { match } = props;
  return (
      <div>
        <h2 id="project-risks-heading">
          <Translate contentKey="noRiskNoFunApp.projectRisks.home.title">Project Risks</Translate>
          <Link to={`${match.url}/project-risks/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.createLabel">Create a new Project Risks</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {riskList.length > 0 ? (
            <Table responsive aria-describedby="project-risks-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.projectRisks.projectSeverity">Project Severity</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.projectRisks.projectProbability">Project Probability</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.projectRisks.hasOccured">Has Occured</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.projectRisks.riskResponse">Risk Response</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.projectRisks.project">Project</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.projectRisks.risk">Risk</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {riskList.map((projectRisks, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}`} color="link" size="sm">
                        {projectRisks.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`noRiskNoFunApp.SeverityType.${projectRisks.projectSeverity}`} />
                    </td>
                    <td>
                      <Translate contentKey={`noRiskNoFunApp.ProbabilityType.${projectRisks.projectProbability}`} />
                    </td>
                    <td>{projectRisks.hasOccured ? 'true' : 'false'}</td>
                    <td>
                      {projectRisks.riskResponses
                        ? projectRisks.riskResponses.map((val, j) => (
                            <span key={j}>
                              <Link to={`risk-response/${val.id}`}>{val.id}</Link>
                              {j === projectRisks.riskResponses.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td>{projectRisks.project ? <Link to={`project/${projectRisks.project.id}`}>{projectRisks.project.id}</Link> : ''}</td>
                    <td>{projectRisks.risk ? <Link to={`risk/${projectRisks.risk.id}`}>{projectRisks.risk.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${projectRisks.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projectRisks.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${projectRisks.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="noRiskNoFunApp.projectRisks.home.notFound">No Project Risks found</Translate>
            </div>
          )}
        </div>
      </div>
    );
}

const mapStateToProps = ({ projectRisks }: IRootState) => ({
  projectRisksList: projectRisks.projectRiskEntities,
  proposedProjectRiskEntities: projectRisks.proposedProjectRiskEntities,
  toBeDiscussedProjectRiskEntities: projectRisks.toBeDiscussedProjectRiskEntities
});

const mapDispatchToProps = {
  getEntities,
  getProposedProjectRisks,
  getToBeDiscussedProjectRisks
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisks);
