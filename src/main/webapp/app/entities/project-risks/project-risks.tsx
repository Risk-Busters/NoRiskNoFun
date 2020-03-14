import React, {useEffect, useState} from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { updateEntity as updateProjectRisks } from './project-risks.reducer';
import { addLikeForProjectRisk as addLikeForProjectRisk } from './project-risks.reducer';
import { IRootState } from 'app/shared/reducers';
import {getEntities, getProposedProjectRisks, getToBeDiscussedProjectRisks} from "app/entities/project-risks/project-risks.reducer";
import {IProjectRisks} from "app/shared/model/project-risks.model";

type ProjectRisksProps = {
  riskDiscussionStatus: string
};

export interface IProjectRisksProps extends StateProps, DispatchProps, ProjectRisksProps, RouteComponentProps<{ id: string }> {}

function ProjectRisks(props: IProjectRisksProps) {

  const [riskList, setRiskList] = useState([]);
  const { match, loading } = props;

  const upvote = (projectRisk: IProjectRisks) => {
    props.addLikeForProjectRisk(projectRisk);
    // TODO: @Moe: brauchen wir das so, damit die UI die Änderung direkt anzeigt?
    projectRisk.likes ++;
  };

  const fetch = () => {
      props.getProposedProjectRisks();
      props.getToBeDiscussedProjectRisks();
      props.getEntities();
  };

  useEffect(() => {
    fetch();
  }, []);

  useEffect(() => {
    console.log(props.riskDiscussionStatus)
  }, [props.riskDiscussionStatus]);

  useEffect(() => {
    if(props.riskDiscussionStatus === "proposed") {
      setRiskList(Array.from(props.proposedProjectRiskEntities));
    } else if(props.riskDiscussionStatus.toLocaleUpperCase()  === "toBeDiscussed".toLocaleUpperCase()) {
      setRiskList(Array.from(props.toBeDiscussedProjectRiskEntities));
    } else if(props.riskDiscussionStatus === "final") {
      setRiskList(Array.from(props.projectRisksList));
    }
  }, [props.projectRisksList, props.proposedProjectRiskEntities, props.toBeDiscussedProjectRiskEntities]);

  const renderProposed = (): JSX.Element => {
    return (
      <Table responsive aria-describedby="project-risks-heading">
      <thead>
      <tr>
        <th>
          <Translate contentKey="noRiskNoFunApp.risk.name">Title</Translate>
        </th>
        <th>
          <Translate contentKey="noRiskNoFunApp.risk.description">Description</Translate>
        </th>
        <th />
      </tr>
      </thead>
      <tbody>
      {riskList.map((projectRisks, i) => (
          <tr key={`entity-${i}`}>
            <td>
              <p>{projectRisks.risk.name}</p>
            </td>
            <td>
              <p>{projectRisks.risk.description}</p>
            </td>
            <td className="text-right">
              <div className="btn-group flex-btn-group-container">
                <Button onClick={() => upvote(projectRisks)} color="primary" size="sm" style={{marginRight: '5px'}} disabled={loading}>
                  <span className="d-none d-md-inline">
                    {projectRisks.likes}
                  </span>
                  {' '}<FontAwesomeIcon icon="thumbs-up" />
                </Button>
                <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}/edit`} color="primary" size="sm">
                  <FontAwesomeIcon icon="pencil-alt" />{' '}
                  <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                </Button>
                <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}/delete`} color="danger" size="sm">
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
    )
  };

  const renderDiscuss = (): JSX.Element => {
    return (
      <Table responsive aria-describedby="project-risks-heading">
        <thead>
        <tr>
          <th>
            <Translate contentKey="noRiskNoFunApp.risk.name">Title</Translate>
          </th>
          <th>
            <Translate contentKey="noRiskNoFunApp.risk.description">Description</Translate>
          </th>
          <th />
        </tr>
        </thead>
        <tbody>
        {riskList.map((projectRisks, i) => (
          <tr key={`entity-${i}`}>
            <td>
              <p>{projectRisks.risk.name}</p>
            </td>
            <td>
              <p>{projectRisks.risk.description}</p>
            </td>
            <td className="text-right">
              <div className="btn-group flex-btn-group-container">
                {(projectRisks.discussions === null || projectRisks.discussions.length < 2) ? (
                  <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}/edit`} color="primary" size="sm">
                    <FontAwesomeIcon icon="pencil-alt" />{' '}
                    <span className="d-none d-md-inline">
                            <Translate contentKey="noRiskNoFunApp.projectRisks.actions.discuss">Discuss!</Translate>
                          </span>
                  </Button>
                ) : (
                  <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}`} color="primary" size="sm">
                    <FontAwesomeIcon icon="pencil-alt" />{' '}
                    <span className="d-none d-md-inline">
                            <Translate contentKey="noRiskNoFunApp.projectRisks.actions.finalize">Finalize it!</Translate>
                          </span>
                  </Button>
                )}
                <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}/delete`} color="danger" size="sm">
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
    )
  };

  const renderFinal = (): JSX.Element => {
    return (
      <Table responsive aria-describedby="project-risks-heading">
        <thead>
        <tr>
          <th>
            <Translate contentKey="noRiskNoFunApp.risk.name">Title</Translate>
          </th>
          <th>
            <Translate contentKey="noRiskNoFunApp.risk.description">Description</Translate>
          </th>
          <th>
            <Translate contentKey="noRiskNoFunApp.projectRisks.hasOccured">Has Occured</Translate>
          </th>
          <th />
        </tr>
        </thead>
        <tbody>
        {riskList.map((projectRisks, i) => (
          <tr key={`entity-${i}`}>
            <td>
              <p>{projectRisks.risk.name}</p>
            </td>
            <td>
              <p>{projectRisks.risk.description}</p>
            </td>
            <td><Translate contentKey={`noRiskNoFunApp.projectRisks.hasOccured${projectRisks.hasOccured ? 'Yes' : 'No'}`} /></td>
            <td className="text-right">
              <div className="btn-group flex-btn-group-container">
                <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}`} color="info" size="sm">
                  <FontAwesomeIcon icon="eye" />{' '}
                  <span className="d-none d-md-inline">
                            <Translate contentKey="noRiskNoFunApp.projectRisks.actions.seemore">See more!</Translate>
                          </span>
                </Button>
                <Button tag={Link} to={`${match.url}/project-risks/${projectRisks.id}/delete`} color="danger" size="sm">
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
    )
  };

  const renderProjectRisk = (): JSX.Element => {
    if(props.riskDiscussionStatus === "proposed") {
      return renderProposed();
    } else if(props.riskDiscussionStatus === "toBeDiscussed") {
      return renderDiscuss();
    } else {
      return renderFinal();
    }
  };

  return (
      <div>
        <h2 id="project-risks-heading">
          <Link to={`${match.url}/project-risks/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.createLabel">Propose a Risk!</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {riskList.length > 0 ? (
            renderProjectRisk()
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
  loading: projectRisks.updating,
  projectRisksList: projectRisks.projectRiskEntities,
  proposedProjectRiskEntities: projectRisks.proposedProjectRiskEntities,
  toBeDiscussedProjectRiskEntities: projectRisks.toBeDiscussedProjectRiskEntities
});

const mapDispatchToProps = {
  getEntities,
  getProposedProjectRisks,
  getToBeDiscussedProjectRisks,
  updateProjectRisks,
  addLikeForProjectRisk
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisks);
