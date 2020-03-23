import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {Translate, translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getRiskResponses} from 'app/entities/risk-response/risk-response.reducer';
import {getEntities as getProjects} from 'app/entities/project/project.reducer';
import {getEntities as getRisks} from 'app/entities/risk/risk.reducer';
import {createDiscussion, createEntity, getEntity, reset, updateEntity} from './project-risks.reducer';
import {IProjectRisks, IRiskDiscussionVM} from 'app/shared/model/project-risks.model';

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
      riskId: this.props.match.params.riskId || '0',
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
        const { projectRisksEntity, currentUser } = this.props;
        const deepCopyEntity: IProjectRisks = Object.assign(projectRisksEntity);

        if (deepCopyEntity.riskDiscussionStatus === "proposed") {

          // If not set then its a proposed risk so inRiskpool should
          // default to false to avoid null constraint checks.
          if (values.risk !== undefined) {
            values.risk.inRiskpool = false;
          }

          const risk = Object.assign(deepCopyEntity.risk, values.risk || {});

          deepCopyEntity.hasOccured = values.hasOccured;
          deepCopyEntity.risk = risk;

          this.props.updateEntity(deepCopyEntity);
        } else {
          const riskDiscussion: IRiskDiscussionVM = { projectRiskId: deepCopyEntity.id, projectSeverity: values.projectSeverity, projectProbability: values.projectProbability};

          this.props.createDiscussion(riskDiscussion);
        }
      }
    };

  handleClose = () => {
    this.state.isNew ? this.props.history.push(`/entity/project/${this.state.projectId}/proposed/project-risks/taskqueue`) : this.props.history.push(`/entity/project/${this.state.projectId}`);
  };

  renderProjectRiskForm = () => {
    const { projectRisksEntity } = this.props;

    switch (this.props.projectRisksEntity.riskDiscussionStatus) {
      case "toBeDiscussed":
        return (
          <>
            <AvGroup>
              <Label id="projectSeverityLabel" for="project-risks-projectSeverity">
                <Translate contentKey="noRiskNoFunApp.projectRisks.projectSeverity">Project Severity</Translate>
              </Label>
              <AvInput
                id="project-risks-projectSeverity"
                type="select"
                className="form-control"
                name="projectSeverity"
                value="BAD"
                required
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
                value="SURE"
                required
              >
                <option value="SURE">{translate('noRiskNoFunApp.ProbabilityType.SURE')}</option>
                <option value="PROBABLY">{translate('noRiskNoFunApp.ProbabilityType.PROBABLY')}</option>
                <option value="MAYBE">{translate('noRiskNoFunApp.ProbabilityType.MAYBE')}</option>
                <option value="NOTLIKELY">{translate('noRiskNoFunApp.ProbabilityType.NOTLIKELY')}</option>
                <option value="NOTGONNAHAPPEN">{translate('noRiskNoFunApp.ProbabilityType.NOTGONNAHAPPEN')}</option>
              </AvInput>
            </AvGroup>
          </>
        );
      case "proposed":
        return (
          <>
            <AvGroup>
              <Label for="project-risks-title">
                <Translate contentKey="noRiskNoFunApp.projectRisks.title">Risk Title</Translate>
              </Label>
              <AvInput id="project-risks-title" type="text" className="form-control" name="risk.name" value={projectRisksEntity.risk.name}/>
            </AvGroup>
            <AvGroup>
              <Label for="project-risks-description">
                <Translate contentKey="noRiskNoFunApp.projectRisks.description">Risk Description</Translate>
              </Label>
              <AvInput id="project-risks-description" type="text" className="form-control" name="risk.description" value={projectRisksEntity.risk.description}/>
            </AvGroup>
          </>
        );
      case "final":
        return (
          <p>Im final, what do you want?</p>
        );
      default:
        return <span />;
    }
  };

  renderTitle = () => {
    const { projectRisksEntity} = this.props;

    switch (projectRisksEntity.riskDiscussionStatus) {
      case "proposed":
        return (
          <>
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.editProposeLabel"/>
          </>
        );
      case "toBeDiscussed":
        return (
          <>
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.discussLabel"/>
          </>
        );
      case "final":
        return (
          <>
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.finalLabel"/>
          </>
        );
      default:
        return (
          <>
            <Translate contentKey="noRiskNoFunApp.projectRisks.home.proposeLabel"/>
          </>
        )
    }
  };

  render() {
    const { projectRisksEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="noRiskNoFunApp.projectRisks.home.createOrEditLabel">
              { this.renderTitle() }
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : projectRisksEntity} onSubmit={this.saveEntity}>
                {!isNew ? this.renderProjectRiskForm() : (
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
  updateSuccess: storeState.projectRisks.updateSuccess,
  currentUser: storeState.authentication.account
});

const mapDispatchToProps = {
  getRiskResponses,
  getProjects,
  getRisks,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  createDiscussion
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProjectRisksUpdate);
