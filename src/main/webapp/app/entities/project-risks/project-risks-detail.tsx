import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps, useParams} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity, updateEntity as updateProjectRisk} from './project-risks.reducer';
import RiskResponse from "app/entities/risk-response/risk-response";
import "./project-risks.scss"

export interface IProjectRisksDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

function ProjectRisksDetail(props: IProjectRisksDetailProps) {

  const { projectRisksEntity, user } = props;
  const { riskId, risktype } = useParams();

  useEffect(() => {
    props.getEntity(riskId);
  }, []);

  const beInCharge = () => {
    const newPersonInCharge = Object.assign(projectRisksEntity);
    newPersonInCharge.personInCharge = user;
    props.updateProjectRisk(projectRisksEntity);
    props.getEntity(riskId);
  };

  return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.projectRisks.detail.title">ProjectRisks</Translate>
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="personInCharge">
                <Translate contentKey="noRiskNoFunApp.projectRisks.personInCharge" />
              </span>
            </dt>
            {projectRisksEntity.personInCharge == null ? (
              <>
                <Translate contentKey="noRiskNoFunApp.projectRisks.actions.beInChargeNone" />{' '}
                <Button onClick={() => beInCharge()} color="primary" size="sm">
                <span className="d-none d-md-inline">
                              <Translate contentKey="noRiskNoFunApp.projectRisks.actions.beInCharge" />
                            </span>
                </Button>
              </>

            ) : (
              <dd>{projectRisksEntity.personInCharge.firstName} {projectRisksEntity.personInCharge.lastName}</dd>
            )}

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
            <dd><Translate contentKey={`noRiskNoFunApp.projectRisks.hasOccured${projectRisksEntity.hasOccured ? 'Yes' : 'No'}`} /></dd>
          </dl>
          <RiskResponse history={props.history} location={props.location} match={props.match} />
          <Button tag={Link} to={`/entity/project/${projectRisksEntity.project ? `${projectRisksEntity.project.id}/${risktype}` : ''}`} replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/project-risks/${projectRisksEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
}

const mapStateToProps = ({ projectRisks, authentication }: IRootState) => ({
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
