import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './project-risks.reducer';

export interface IProjectRisksProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProjectRisks extends React.Component<IProjectRisksProps> {
  componentDidMount() {
    this.props.getEntities();
    console.log("Match in Project risks");
    console.log(this.props.match);
    console.log("Porject risks (should be all");
    console.log(this.props.projectRisksList);
  }

  render() {
    const { projectRisksList, match } = this.props;
    return (
      <div>
        <h2 id="project-risks-heading">
          <Translate contentKey="noRiskNoFunApp.projectRisks.home.title">Project Risks</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.createLabel">Create a new Project Risks</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {projectRisksList && projectRisksList.length > 0 ? (
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
                {projectRisksList.map((projectRisks, i) => (
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
}

const mapStateToProps = ({ projectRisks }: IRootState) => ({
  projectRisksList: projectRisks.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisks);
