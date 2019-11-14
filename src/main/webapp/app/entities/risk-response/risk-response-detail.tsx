import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './risk-response.reducer';
import { IRiskResponse } from 'app/shared/model/risk-response.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRiskResponseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RiskResponseDetail extends React.Component<IRiskResponseDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { riskResponseEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.riskResponse.detail.title">RiskResponse</Translate> [<b>{riskResponseEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">
                <Translate contentKey="noRiskNoFunApp.riskResponse.type">Type</Translate>
              </span>
            </dt>
            <dd>{riskResponseEntity.type}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="noRiskNoFunApp.riskResponse.description">Description</Translate>
              </span>
            </dt>
            <dd>{riskResponseEntity.description}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="noRiskNoFunApp.riskResponse.status">Status</Translate>
              </span>
            </dt>
            <dd>{riskResponseEntity.status}</dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.riskResponse.risk">Risk</Translate>
            </dt>
            <dd>{riskResponseEntity.risk ? riskResponseEntity.risk.id : ''}</dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.riskResponse.projectRisks">Project Risks</Translate>
            </dt>
            <dd>{riskResponseEntity.projectRisks ? riskResponseEntity.projectRisks.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/risk-response" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/risk-response/${riskResponseEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ riskResponse }: IRootState) => ({
  riskResponseEntity: riskResponse.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskResponseDetail);
