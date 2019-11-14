import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './project-risks.reducer';
import { IProjectRisks } from 'app/shared/model/project-risks.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjectRisksDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProjectRisksDetail extends React.Component<IProjectRisksDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { projectRisksEntity } = this.props;
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
              <Translate contentKey="noRiskNoFunApp.projectRisks.project">Project</Translate>
            </dt>
            <dd>{projectRisksEntity.project ? projectRisksEntity.project.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/project-risks" replace color="info">
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
