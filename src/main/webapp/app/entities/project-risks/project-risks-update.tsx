import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRiskResponse } from 'app/shared/model/risk-response.model';
import { getEntities as getRiskResponses } from 'app/entities/risk-response/risk-response.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IRisk } from 'app/shared/model/risk.model';
import { getEntities as getRisks } from 'app/entities/risk/risk.reducer';
import { getEntity, updateEntity, createEntity, reset } from './project-risks.reducer';
import { IProjectRisks } from 'app/shared/model/project-risks.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProjectRisksUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ riskId: string, id: string, risktype: string }> {}

export interface IProjectRisksUpdateState {
  isNew: boolean;
  idsriskResponse: any[];
  projectId: string;
  riskId: string;
}

export class ProjectRisksUpdate extends React.Component<IProjectRisksUpdateProps, IProjectRisksUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsriskResponse: [],
      projectId: this.props.match.params.id || '-1',
      riskId: '0',
      isNew: !this.props.match.params || !this.props.match.params.riskId
    };
  }

  UNSAFE_componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.riskId);
    }

    this.props.getRiskResponses();
    this.props.getProjects();
    this.props.getRisks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length > 0) return;

      if (this.state.isNew) {
        const { project } = values;
        const entity = {...project, projectId:  this.state.projectId};
        this.props.createEntity(entity);
      } else {
        const { projectRisksEntity } = this.props;
        const entity = {
          ...projectRisksEntity,
          ...values,
          riskResponses: mapIdList(values.riskResponses)};
        this.props.updateEntity(entity);
      }
    };

  handleClose = () => {
    this.state.isNew ? this.props.history.push(`/entity/project/${this.state.projectId}/proposed`) : this.props.history.push(`/entity/project/${this.state.projectId}`);
  };

  render() {
    const { projectRisksEntity, riskResponses, projects, risks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="noRiskNoFunApp.projectRisks.home.createOrEditLabel">
              {this.state.isNew ? (
                <Translate contentKey="noRiskNoFunApp.projectRisks.home.proposeLabel"/>
              ) : (
                <>
                  <Translate contentKey="noRiskNoFunApp.projectRisks.home.discussLabel"/>
                </>
              )}
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : projectRisksEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <>
                  <AvGroup>
                    <Label for="project-risks-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="project-risks-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                    <AvGroup>
                      <Label id="projectSeverityLabel" for="project-risks-projectSeverity">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.projectSeverity">Project Severity</Translate>
                      </Label>
                      <AvInput
                        id="project-risks-projectSeverity"
                        type="select"
                        className="form-control"
                        name="projectSeverity"
                        value={(!isNew && projectRisksEntity.projectSeverity) || 'BAD'}
                      >
                        <option value="BAD">{translate('noRiskNoFunApp.SeverityType.BAD')}</option>
                        <option value="LESSBAD">{translate('noRiskNoFunApp.SeverityType.LESSBAD')}</option>
                        <option value="NEUTRAL">{translate('noRiskNoFunApp.SeverityType.NEUTRAL')}</option>
                        <option value="SOSO">{translate('noRiskNoFunApp.SeverityType.SOSO')}</option>
                        <option value="OK">{translate('noRiskNoFunApp.SeverityType.OK')}</option>
                      </AvInput>
                    </AvGroup>
                    <AvGroup>
                      <Label id="projectProbabilityLabel" for="project-risks-projectProbability">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.projectProbability">Project Probability</Translate>
                      </Label>
                      <AvInput
                        id="project-risks-projectProbability"
                        type="select"
                        className="form-control"
                        name="projectProbability"
                        value={(!isNew && projectRisksEntity.projectProbability) || 'SURE'}
                      >
                        <option value="SURE">{translate('noRiskNoFunApp.ProbabilityType.SURE')}</option>
                        <option value="PROBABLY">{translate('noRiskNoFunApp.ProbabilityType.PROBABLY')}</option>
                        <option value="MAYBE">{translate('noRiskNoFunApp.ProbabilityType.MAYBE')}</option>
                        <option value="NOTLIKELY">{translate('noRiskNoFunApp.ProbabilityType.NOTLIKELY')}</option>
                        <option value="NOTGONNAHAPPEN">{translate('noRiskNoFunApp.ProbabilityType.NOTGONNAHAPPEN')}</option>
                      </AvInput>
                    </AvGroup>
                    <AvGroup>
                      <Label id="hasOccuredLabel" check>
                        <AvInput id="project-risks-hasOccured" type="checkbox" className="form-control" name="hasOccured" />
                        <Translate contentKey="noRiskNoFunApp.projectRisks.hasOccured">Has Occured</Translate>
                      </Label>
                    </AvGroup>
                    <AvGroup>
                      <Label for="project-risks-riskResponse">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.riskResponse">Risk Response</Translate>
                      </Label>
                      <AvInput
                        id="project-risks-riskResponse"
                        type="select"
                        multiple
                        className="form-control"
                        name="riskResponses"
                        value={projectRisksEntity.riskResponses && projectRisksEntity.riskResponses.map(e => e.id)}
                      >
                        <option value="" key="0" />
                        {riskResponses
                          ? riskResponses.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.id}
                            </option>
                          ))
                          : null}
                      </AvInput>
                    </AvGroup>
                    <AvGroup>
                      <Label for="project-risks-project">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.project">Project</Translate>
                      </Label>
                      <AvInput id="project-risks-project" type="select" className="form-control" name="project.id">
                        <option value="" key="0" />
                        {projects
                          ? projects.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.id}
                            </option>
                          ))
                          : null}
                      </AvInput>
                    </AvGroup>
                    <AvGroup>
                      <Label for="project-risks-risk">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.risk">Risk</Translate>
                      </Label>
                      <AvInput id="project-risks-risk" type="select" className="form-control" name="risk.id">
                        <option value="" key="0" />
                        {risks
                          ? risks.map(otherEntity => (
                            <option value={otherEntity.id} key={otherEntity.id}>
                              {otherEntity.id}
                            </option>
                          ))
                          : null}
                      </AvInput>
                    </AvGroup>
                  </>
                ) : (
                  <>
                    <AvGroup>
                      <Label for="project-risks-title">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.title">Risk Title</Translate>
                      </Label>
                      <AvInput id="project-risks-title" type="text" className="form-control" name="project.title" />
                    </AvGroup>
                    <AvGroup>
                      <Label for="project-risks-description">
                        <Translate contentKey="noRiskNoFunApp.projectRisks.description">Risk Description</Translate>
                      </Label>
                      <AvInput id="project-risks-description" type="text" className="form-control" name="project.description" />
                    </AvGroup>
                  </>
                )}

                <Button tag={Link} id="cancel-save" to={`/entity/project/${this.state.projectId}/${this.props.match.params.risktype}`} replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  riskResponses: storeState.riskResponse.entities,
  projects: storeState.project.entities,
  risks: storeState.risk.entities,
  projectRisksEntity: storeState.projectRisks.entity,
  loading: storeState.projectRisks.loading,
  updating: storeState.projectRisks.updating,
  updateSuccess: storeState.projectRisks.updateSuccess
});

const mapDispatchToProps = {
  getRiskResponses,
  getProjects,
  getRisks,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisksUpdate);
