import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './risk.reducer';

export interface IRiskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export class RiskDetail extends React.Component<IRiskDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const {riskEntity} = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.risk.detail.title"/> [<b>{riskEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="noRiskNoFunApp.risk.name"/>
              </span>
            </dt>
            <dd>{riskEntity.name}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="noRiskNoFunApp.risk.description"/>
              </span>
            </dt>
            <dd>{riskEntity.description}</dd>
            <dt>
              <span id="severity">
                <Translate contentKey="noRiskNoFunApp.risk.severity"/>
              </span>
            </dt>
            <dd>
              {riskEntity.severity == null ? (
                <Translate contentKey={`noRiskNoFunApp.risk.notSet`}/>
              ) : (<Translate contentKey="noRiskNoFunApp.risk.severity"/>)}
            </dd>
            <dt>
              <span id="probability">
                <Translate contentKey="noRiskNoFunApp.risk.probability"/>
              </span>
            </dt>
            <dd>
              {riskEntity.probability == null ? (
                <Translate contentKey={`noRiskNoFunApp.risk.notSet`}/>
              ) : (<Translate contentKey="noRiskNoFunApp.risk.probability"/>)}
            </dd>
            <dt>
              <span id="inRiskpool">
                <Translate contentKey="noRiskNoFunApp.risk.inRiskpool"/>
              </span>
            </dt>
            <dd>{riskEntity.inRiskpool ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.risk.riskResponse"/>
            </dt>
            <dd>
              {riskEntity.riskResponses.length !== 0
                ? riskEntity.riskResponses.map((val, i) => (
                  <span key={val.id}>
                      <a>{val.id}</a>
                    {i === riskEntity.riskResponses.length - 1 ? '' : ', '}
                    </span>
                ))
                : <Translate contentKey={`noRiskNoFunApp.risk.notSet`}/>}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/risk" replace color="info">
            <FontAwesomeIcon icon="arrow-left"/>{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back"/>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/risk/${riskEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt"/>{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit"/>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({risk}: IRootState) => ({
  riskEntity: risk.entity
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RiskDetail);
