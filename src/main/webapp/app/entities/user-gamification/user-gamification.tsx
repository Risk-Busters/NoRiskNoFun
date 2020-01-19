import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-gamification.reducer';
import { IUserGamification } from 'app/shared/model/user-gamification.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserGamificationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class UserGamification extends React.Component<IUserGamificationProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { userGamificationList, match } = this.props;
    return (
      <div>
        <h2 id="user-gamification-heading">
          <Translate contentKey="noRiskNoFunApp.userGamification.home.title">User Gamifications</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="noRiskNoFunApp.userGamification.home.createLabel">Create a new User Gamification</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          {userGamificationList && userGamificationList.length > 0 ? (
            <Table responsive aria-describedby="user-gamification-heading">
              <thead>
                <tr>
                  <th>
                    <Translate contentKey="global.field.id">ID</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.userGamification.pointsScore">Points Score</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.userGamification.user">User</Translate>
                  </th>
                  <th>
                    <Translate contentKey="noRiskNoFunApp.userGamification.achievementType">Achievement Type</Translate>
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {userGamificationList.map((userGamification, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${userGamification.id}`} color="link" size="sm">
                        {userGamification.id}
                      </Button>
                    </td>
                    <td>{userGamification.pointsScore}</td>
                    <td>{userGamification.userLogin ? userGamification.userLogin : ''}</td>
                    <td>
                      {userGamification.achievements
                        ? userGamification.achievements.map((val, j) => (
                            <span key={j}>
                              <Link to={`achievement-type/${val}`}></Link>
                              {j === userGamification.achievements.length - 1 ? '' : ', '}
                            </span>
                          ))
                        : null}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${userGamification.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${userGamification.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${userGamification.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">
              <Translate contentKey="noRiskNoFunApp.userGamification.home.notFound">No User Gamifications found</Translate>
            </div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ userGamification }: IRootState) => ({
  userGamificationList: userGamification.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserGamification);
