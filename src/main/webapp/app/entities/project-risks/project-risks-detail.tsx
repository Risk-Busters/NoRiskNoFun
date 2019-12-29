import React, {useEffect} from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './project-risks.reducer';

export interface IProjectRisksDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

function ProjectRisksDetail(props) {

  const { projectRisksEntity } = props;

  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.projectRisks.detail.title">ProjectRisks</Translate> [<b>{projectRisksEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="projectSeverity">
                <Translate contentKey="noRiskNoFunApp.projectRisks.projectSeverity">Project Severity</Translate>
              </span>
            </dt>
            <dd>{projectRisksEntity.projectSeverity}</dd>
            <dt>
              <span id="projectProbability">
                <Translate contentKey="noRiskNoFunApp.projectRisks.projectProbability">Project Probability</Translate>
              </span>
            </dt>
            <dd>{projectRisksEntity.projectProbability}</dd>
            <dt>
              <span id="hasOccured">
                <Translate contentKey="noRiskNoFunApp.projectRisks.hasOccured">Has Occured</Translate>
              </span>
            </dt>
            <dd>{projectRisksEntity.hasOccured ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.projectRisks.riskResponse">Risk Response</Translate>
            </dt>
            <dd>
              {projectRisksEntity.riskResponses
                ? projectRisksEntity.riskResponses.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === projectRisksEntity.riskResponses.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.projectRisks.project">Project</Translate>
            </dt>
            <dd>{projectRisksEntity.project ? projectRisksEntity.project.id : ''}</dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.projectRisks.risk">Risk</Translate>
            </dt>
            <dd>{projectRisksEntity.risk ? projectRisksEntity.risk.id : ''}</dd>
          </dl>
          <Button tag={Link} to={`/entity/project/${projectRisksEntity.project ? projectRisksEntity.project.id : ''}`} replace color="info">
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

const mapStateToProps = ({ projectRisks }: IRootState) => ({
  projectRisksEntity: projectRisks.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisksDetail);
