import React from 'react';
import {connect} from 'react-redux';
import {RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {Translate, translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';

import {getEntities as getRisks} from 'app/entities/risk/risk.reducer';
import {getEntities as getProjectRisks} from 'app/entities/project-risks/project-risks.reducer';
import {createEntity, createEntityForProject, getEntity, reset, updateEntity} from './risk-response.reducer';

export interface IRiskResponseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ riskId: string; responseId: string }> {}

export interface IRiskResponseUpdateState {
  isNew: boolean;
  riskId: string;
  projectRisksId: string;
}

export class RiskResponseUpdate extends React.Component<IRiskResponseUpdateProps, IRiskResponseUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      riskId: '0',
      projectRisksId: this.props.match.params.riskId,
      isNew: !this.props.match.params || !this.props.match.params.responseId
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
      this.props.getEntity(this.props.match.params.responseId);
    }

    this.props.getRisks();
    this.props.getProjectRisks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { riskResponseEntity } = this.props;
      const entity = {
        ...riskResponseEntity,
        ...values
      };

      if (this.state.isNew) {
        const createRiskResponse = {
          projectRiskId: parseInt(this.state.projectRisksId, 10),
          riskResponse: entity
        };

        this.props.createEntityForProject(createRiskResponse);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.goBack();
  };

  render() {
    const { riskResponseEntity, risks, projectRisks, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="noRiskNoFunApp.riskResponse.home.createOrEditLabel">
              <Translate contentKey="noRiskNoFunApp.riskResponse.home.createOrEditLabel">Create or edit a RiskResponse</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : riskResponseEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="risk-response-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="risk-response-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel" for="risk-response-type">
                    <Translate contentKey="noRiskNoFunApp.riskResponse.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="risk-response-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && riskResponseEntity.type) || 'PREVENTION'}
                  >
                    <option value="PREVENTION">{translate('noRiskNoFunApp.RiskResponseType.PREVENTION')}</option>
                    <option value="CONTINGENCY">{translate('noRiskNoFunApp.RiskResponseType.CONTINGENCY')}</option>
                    <option value="MITIGATION">{translate('noRiskNoFunApp.RiskResponseType.MITIGATION')}</option>
                    <option value="TOLERANCE">{translate('noRiskNoFunApp.RiskResponseType.TOLERANCE')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="risk-response-description">
                    <Translate contentKey="noRiskNoFunApp.riskResponse.description">Description</Translate>
                  </Label>
                  <AvField
                    id="risk-response-description"
                    type="text"
                    name="description"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="risk-response-status">
                    <Translate contentKey="noRiskNoFunApp.riskResponse.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="risk-response-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && riskResponseEntity.status) || 'TODO'}
                  >
                    <option value="TODO">{translate('noRiskNoFunApp.StatusType.TODO')}</option>
                    <option value="WIP">{translate('noRiskNoFunApp.StatusType.WIP')}</option>
                    <option value="DONE">{translate('noRiskNoFunApp.StatusType.DONE')}</option>
                  </AvInput>
                </AvGroup>
                <Button onClick={() => this.props.history.goBack()} replace color="info">
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
  risks: storeState.risk.entities,
  projectRisks: storeState.riskResponse.entities,
  riskResponseEntity: storeState.riskResponse.entity,
  loading: storeState.riskResponse.loading,
  updating: storeState.riskResponse.updating,
  updateSuccess: storeState.riskResponse.updateSuccess
});

const mapDispatchToProps = {
  getRisks,
  getProjectRisks,
  getEntity,
  updateEntity,
  createEntity,
  createEntityForProject,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskResponseUpdate);
