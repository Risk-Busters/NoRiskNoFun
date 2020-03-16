import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getUserGamification } from 'app/entities/user-gamification/user-gamification.reducer';

export interface IUserGamificationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

function UserGamification(props: IUserGamificationProps) {

  useEffect(() => {
    props.getUserGamification();
  }, []);

  const {match} = props;
  return (
    <div>
      <h2 id="user-gamification-heading">
        <Translate contentKey="noRiskNoFunApp.userGamification.home.title">User Gamifications</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity"
              id="jh-create-entity">
          <FontAwesomeIcon icon="plus"/>
          &nbsp;
          <Translate contentKey="noRiskNoFunApp.userGamification.home.createLabel">Create a new User
            Gamification</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {props.userGamification ? (
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
              <th>
                <Translate contentKey="noRiskNoFunApp.userGamification.pointsOverTime" />
              </th>
            </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                  <Button tag={Link} to={`${match.url}/${props.userGamification.id}`} color="link" size="sm">
                    {props.userGamification.id}
                  </Button>
                </td>
                <td>{props.userGamification.activityScoreBasedOnPoints}</td>
                <td>Its always you, dummy.</td>
                <td>
                  {props.userGamification.userAchievements
                    ? props.userGamification.userAchievements.map((val, j) => (
                      <span key={j}>
                              <Link to={`achievement-type/${val}`} />
                        {val.name}
                        {j === props.userGamification.userAchievements.length - 1 ? '' : ', '}
                            </span>
                    ))
                    : null}
                </td>
                <td>
                  {props.userGamification.pointsOverTime
                    ? props.userGamification.pointsOverTime.map((val, j) => (
                      <span key={j}>
                        {val.date + ': ' + val.pointsScore + ', '}
                      </span>
                    ))
                    : null}
                </td>
                <td className="text-right">
                  <div className="btn-group flex-btn-group-container">
                    <Button tag={Link} to={`${match.url}/${props.userGamification.id}`} color="info" size="sm">
                      <FontAwesomeIcon icon="eye"/>{' '}
                      <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                    </Button>
                    <Button tag={Link} to={`${match.url}/${props.userGamification.id}/edit`} color="primary" size="sm">
                      <FontAwesomeIcon icon="pencil-alt"/>{' '}
                      <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                    </Button>
                    <Button tag={Link} to={`${match.url}/${props.userGamification.id}/delete`} color="danger" size="sm">
                      <FontAwesomeIcon icon="trash"/>{' '}
                      <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                    </Button>
                  </div>
                </td>
              </tr>
            </tbody>
          </Table>
        ) : (
          <div className="alert alert-warning">
            <Translate contentKey="noRiskNoFunApp.userGamification.home.notFound">No User Gamifications
              found</Translate>
          </div>
        )}
      </div>
    </div>
  );
}

const mapStateToProps = ({userGamification}: IRootState) => ({
  userGamification: userGamification.entity
});

const mapDispatchToProps = {
  getUserGamification
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserGamification);
