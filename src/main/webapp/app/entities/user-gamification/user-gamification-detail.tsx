import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Row} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './user-gamification.reducer';

export interface IUserGamificationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserGamificationDetail extends React.Component<IUserGamificationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userGamificationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="noRiskNoFunApp.userGamification.detail.title">UserGamification</Translate> [
            <b>{userGamificationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="pointsScore">
                <Translate contentKey="noRiskNoFunApp.userGamification.pointsScore">Points Score</Translate>
              </span>
            </dt>
            <dd>{userGamificationEntity.pointsScore}</dd>
            <dt>
              <Translate contentKey="noRiskNoFunApp.userGamification.achievementType">Achievement Type</Translate>
            </dt>
            <dd>
              {userGamificationEntity.achievements
                ? userGamificationEntity.achievements.map((val, i) => (
                    <span key={val}>
                      <a>{val}</a>
                      {i === userGamificationEntity.achievements.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/user-gamifications" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-gamifications/${userGamificationEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ userGamification }: IRootState) => ({
  userGamificationEntity: userGamification.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserGamificationDetail);
