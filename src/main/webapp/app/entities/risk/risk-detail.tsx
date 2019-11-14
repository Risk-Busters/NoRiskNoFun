import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './risk.reducer';
import { IRisk } from 'app/shared/model/risk.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRiskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RiskDetail extends React.Component<IRiskDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { riskEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.risk.detail.title">Risk</Translate> [<b>{riskEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="noRiskNoFunApp.risk.name">Name</Translate>
              </span>
            </dt>
            <dd>{riskEntity.name}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="noRiskNoFunApp.risk.description">Description</Translate>
              </span>
            </dt>
            <dd>{riskEntity.description}</dd>
            <dt>
              <span id="severity">
                <Translate contentKey="noRiskNoFunApp.risk.severity">Severity</Translate>
              </span>
            </dt>
            <dd>{riskEntity.severity}</dd>
            <dt>
              <span id="probability">
                <Translate contentKey="noRiskNoFunApp.risk.probability">Probability</Translate>
              </span>
            </dt>
            <dd>{riskEntity.probability}</dd>
            <dt>
              <span id="inRiskpool">
                <Translate contentKey="noRiskNoFunApp.risk.inRiskpool">In Riskpool</Translate>
              </span>
            </dt>
            <dd>{riskEntity.inRiskpool ? 'true' : 'false'}</dd>
          </dl>
          <Button tag={Link} to="/entity/risk" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/risk/${riskEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ risk }: IRootState) => ({
  riskEntity: risk.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskDetail);
