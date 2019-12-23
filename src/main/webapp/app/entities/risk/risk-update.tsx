import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {Translate, translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getEntities as getRiskResponses} from 'app/entities/risk-response/risk-response.reducer';
import {createEntity, getEntity, reset, updateEntity} from './risk.reducer';

export interface IRiskUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export interface IRiskUpdateState {
  isNew: boolean;
  idsriskResponse: any[];
}

export class RiskUpdate extends React.Component<IRiskUpdateProps, IRiskUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsriskResponse: [],
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getRiskResponses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const {riskEntity} = this.props;
      const entity = {
        ...riskEntity,
        ...values,
        // TODO: diese Zeile ist notwenig beim Schätzprozess um riskResponses von Array in Object zu konvertieren
        // riskResponses: mapIdList(values.riskResponses)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/risk');
  };

  render() {
    const {riskEntity, riskResponses, loading, updating} = this.props;
    const {isNew} = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="noRiskNoFunApp.risk.home.createOrEditLabel">
              <Translate contentKey="noRiskNoFunApp.risk.home.createOrEditLabel"/>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : riskEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="risk-id">
                      <Translate contentKey="global.field.id"/>
                    </Label>
                    <AvInput id="risk-id" type="text" className="form-control" name="id" required readOnly/>
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="risk-name">
                    <Translate contentKey="noRiskNoFunApp.risk.name"/>
                  </Label>
                  <AvField
                    id="risk-name"
                    type="text"
                    name="name"
                    validate={{
                      required: {value: true, errorMessage: translate('entity.validation.required')}
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="risk-description">
                    <Translate contentKey="noRiskNoFunApp.risk.description"/>
                  </Label>
                  <AvField
                    id="risk-description"
                    type="text"
                    name="description"
                    validate={{
                      required: {value: true, errorMessage: translate('entity.validation.required')}
                    }}
                  />
                </AvGroup>

                <Button tag={Link} id="cancel-save" to="/entity/risk" replace color="info">
                  <FontAwesomeIcon icon="arrow-left"/>
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back"/>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save"/>
                  &nbsp;
                  <Translate contentKey="entity.action.save"/>
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
  riskEntity: storeState.risk.entity,
  loading: storeState.risk.loading,
  updating: storeState.risk.updating,
  updateSuccess: storeState.risk.updateSuccess
});

const mapDispatchToProps = {
  getRiskResponses,
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
)(RiskUpdate);

/*
 <AvGroup>
                  <Label id="severityLabel" for="risk-severity">
                    <Translate contentKey="noRiskNoFunApp.risk.severity"/>
                  </Label>
                  <AvInput
                    id="risk-severity"
                    type="select"
                    className="form-control"
                    name="severity"
                    value={(!isNew && riskEntity.severity) || 'BAD'}
                  >
                    <option value="BAD">{translate('noRiskNoFunApp.SeverityType.BAD')}</option>
                    <option value="LESSBAD">{translate('noRiskNoFunApp.SeverityType.LESSBAD')}</option>
                    <option value="NEUTRAL">{translate('noRiskNoFunApp.SeverityType.NEUTRAL')}</option>
                    <option value="SOSO">{translate('noRiskNoFunApp.SeverityType.SOSO')}</option>
                    <option value="OK">{translate('noRiskNoFunApp.SeverityType.OK')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="probabilityLabel" for="risk-probability">
                    <Translate contentKey="noRiskNoFunApp.risk.probability"/>
                  </Label>
                  <AvInput
                    id="risk-probability"
                    type="select"
                    className="form-control"
                    name="probability"
                    value={(!isNew && riskEntity.probability) || 'SURE'}
                  >
                    <option value="SURE">{translate('noRiskNoFunApp.ProbabilityType.SURE')}</option>
                    <option value="PROBABLY">{translate('noRiskNoFunApp.ProbabilityType.PROBABLY')}</option>
                    <option value="MAYBE">{translate('noRiskNoFunApp.ProbabilityType.MAYBE')}</option>
                    <option value="NOTLIKELY">{translate('noRiskNoFunApp.ProbabilityType.NOTLIKELY')}</option>
                    <option value="NOTGONNAHAPPEN">{translate('noRiskNoFunApp.ProbabilityType.NOTGONNAHAPPEN')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="inRiskpoolLabel" check>
                    <AvInput id="risk-inRiskpool" type="checkbox" className="form-control" name="inRiskpool" />
                    <Translate contentKey="noRiskNoFunApp.risk.inRiskpool"/>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="risk-riskResponse">
                    <Translate contentKey="noRiskNoFunApp.risk.riskResponse"/>
                  </Label>
                  <AvInput
                    id="risk-riskResponse"
                    type="select"
                    multiple
                    className="form-control"
                    name="riskResponses"
                    value={riskEntity.riskResponses && riskEntity.riskResponses.map(e => e.id)}
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
*/
