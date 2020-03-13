import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps, useParams} from 'react-router-dom';
import {Button, Col, Row, Input} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity, updateEntity as updateProjectRisk} from './project-risks.reducer';
import RiskResponse from "app/entities/risk-response/risk-response";
import "./project-risks.scss"
import {IProjectRisks} from "app/shared/model/project-risks.model";

export interface IProjectRisksDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

function ProjectRisksDetail(props: IProjectRisksDetailProps) {

  const { projectRisksEntity, user, loading } = props;
  const { riskId, risktype } = useParams();

  useEffect(() => {
    props.getEntity(riskId);
  }, []);

  const beInCharge = () => {
    const newPersonInCharge: IProjectRisks = Object.assign(projectRisksEntity);
    newPersonInCharge.personInCharge = user;
    props.updateProjectRisk(newPersonInCharge);
    props.getEntity(riskId);
  };

  const toggleHasOccured = () => {
    const newHasOccured: IProjectRisks = Object.assign(projectRisksEntity);
    newHasOccured.hasOccured = !newHasOccured.hasOccured;
    props.updateProjectRisk(newHasOccured);
    props.getEntity(riskId);
  };

  const renderInChargeButton = () => {
    return (
      <Button onClick={() => beInCharge()} color="primary" size="sm" disabled={loading}>
                <span className="d-none d-md-inline">
                              <Translate contentKey="noRiskNoFunApp.projectRisks.actions.beInCharge" />
                            </span>
      </Button>
    )
  };

  const renderToggleOccured = () => {
    return (
      <input onChange={() => toggleHasOccured()} type="checkbox" checked={projectRisksEntity.hasOccured} disabled={loading}/>
    )
  };

  return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.projectRisks.detail.title"/>
          </h2>
          {projectRisksEntity.risk ? (
            <>
              <h5><Translate contentKey="noRiskNoFunApp.projectRisks.detail.description" /></h5>
              <p>{`${projectRisksEntity.risk.name}: ${projectRisksEntity.risk.description}`}</p>
            </>
          ) : (
            <Translate contentKey="noRiskNoFunApp.projectRisks.detail.noDescription" />
          )}
          <dl className="jh-entity-details">
            <dt>
              <span id="personInCharge">
                <Translate contentKey="noRiskNoFunApp.projectRisks.personInCharge" />
              </span>
            </dt>
            {projectRisksEntity.personInCharge == null ? (
              <>
                <Translate contentKey="noRiskNoFunApp.projectRisks.actions.beInChargeNone" />{' '}
                {renderInChargeButton()}
              </>

            ) : (
              <dd>
                {projectRisksEntity.personInCharge.firstName} {projectRisksEntity.personInCharge.lastName}{' '}
              </dd>
            )}

            <dt>
              <span id="numberDiscussions">
                  <Translate contentKey="noRiskNoFunApp.projectRisks.detail.discussions" />
              </span>
            </dt>
            <dd><Translate contentKey="noRiskNoFunApp.projectRisks.detail.numberDiscussions" interpolate={{amount: projectRisksEntity.discussions ? projectRisksEntity.discussions.length : 0}} /></dd>
            <dt>
              <span id="projectSeverity">
                  <Translate contentKey="noRiskNoFunApp.projectRisks.projectSeverity" />
              </span>
            </dt>
            <dd ><span className={`serv-${projectRisksEntity.averageSeverity}`}><b>&#8226;</b></span>{' '}<Translate contentKey={`noRiskNoFunApp.SeverityType.${projectRisksEntity.averageSeverity}`} /></dd>
            <dt>
              <span id="projectProbability">
                <Translate contentKey="noRiskNoFunApp.projectRisks.projectProbability">Project Probability</Translate>
              </span>
            </dt>
            <dd><span className={`prob-${projectRisksEntity.averageProbability}`}><b>&#8226;</b></span>{' '}<Translate contentKey={`noRiskNoFunApp.ProbabilityType.${projectRisksEntity.averageProbability}`} /></dd>
            <dt>
              <span id="hasOccured">
                <Translate contentKey="noRiskNoFunApp.projectRisks.hasOccured">Has Occured</Translate>
              </span>
            </dt>
            <dd>
              <Translate contentKey={`noRiskNoFunApp.projectRisks.hasOccured${projectRisksEntity.hasOccured ? 'Yes' : 'No'}`} />{' '}{ renderToggleOccured() }
            </dd>
          </dl>
          <RiskResponse history={props.history} location={props.location} match={props.match} />
          <Button tag={Link} to={`/entity/project/${projectRisksEntity.project ? `${projectRisksEntity.project.id}/${risktype}` : ''}`} replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
}

const mapStateToProps = ({ projectRisks, authentication }: IRootState) => ({
  loading: projectRisks.updating,
  projectRisksEntity: projectRisks.entity,
  user: authentication.account
});

const mapDispatchToProps = { getEntity, updateProjectRisk };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisksDetail);
