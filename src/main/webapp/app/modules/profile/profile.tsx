import React, {useEffect, useState} from 'react';
import {getUser} from '../administration/user-management/user-management.reducer';
import {IRootState} from 'app/shared/reducers';
import {RouteComponentProps} from 'react-router-dom';
import {connect} from 'react-redux';
import {Translate} from 'react-jhipster';
import {Badge, Container, Media, Row} from 'reactstrap';
import './profile.scss';
import {languages} from 'app/config/translation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faLanguage} from '@fortawesome/free-solid-svg-icons/faLanguage';
import {faCalendarAlt} from '@fortawesome/free-solid-svg-icons/faCalendarAlt';
import {convertDateTimeFromServer} from 'app/shared/util/date-utils';
import {getUserGamification} from "app/entities/user-gamification/user-gamification.reducer";
import {IUserGamification} from "app/shared/model/user-gamification.model";
import {UserActivityGraph} from "app/modules/useractivity/useractivity";
import {AchievementContainer} from "app/modules/profile/achievements";

export interface IProfileProps extends StateProps, DispatchProps, IUserGamification, RouteComponentProps<{ login?: string }> {
}

export const Profile = (props: IProfileProps) => {

  const [diagramStatus, setDiagramStatus] = useState([]);

  const getUserLogin = (): string => {
    if (props.match.params.login) {
      return props.match.params.login;
    }
    props.history.push(`${props.match.url}/${props.currentLogin}/`);
    return props.currentLogin;
  };

  useEffect(() => {
    props.getUser(getUserLogin());
  }, []);

  useEffect(() => {
    props.getUserGamification(props.user.id);
  }, []);

  const {user} = props;
  const {userGamificationEntitiy} = props;

  useEffect(() => {
    if (userGamificationEntitiy.pointsOverTime !== undefined) {
      const finalFormatForDiagram = userGamificationEntitiy.pointsOverTime.map(Object.values);
      finalFormatForDiagram.unshift(['Date', 'Your Activity']);
      setDiagramStatus(finalFormatForDiagram);
    }
  }, [userGamificationEntitiy.pointsOverTime]);

  const getProfileName = (): string => {
    return user.firstName && user.lastName ? `${user.firstName} ${user.lastName} (${user.login})` : user.login;
  };

  const achievementCards = <AchievementContainer userAchievements={userGamificationEntitiy.userAchievements}/>

  return (
    <div>
      <Row>
        <Media>
          <Media left href="">
            <Media
              object
              src="../../../content/images/logo.svg"
              alt="No Profile Picture Available"
              className="picture"
            />
          </Media>
          <Media body>
            <Media heading>
              <b>{getProfileName()}</b>
            </Media>
            <ul className="list-inline">
              {user.authorities
                ? user.authorities.map((authority: string, i) => (
                  <li key={`user-auth-${i}`}>
                    <Badge color="info">{authority.split('_')[1]}</Badge>
                  </li>
                ))
                : null}
            </ul>
            {user.createdDate && (
              <span>
                <FontAwesomeIcon icon={faCalendarAlt} size="lg"/>{' '}
                <Translate contentKey="profile.home.memberSince">Member since </Translate>{' '}
                <b>{convertDateTimeFromServer(user.createdDate)}</b>
                <br/>
              </span>
            )}
            {user.langKey && (
              <span>
                <FontAwesomeIcon icon={faLanguage} size="lg"/> <Translate
                contentKey="profile.home.languages">Speaks </Translate>{' '}
                <b>{languages['en'].name}</b>
              </span>
            )}
          </Media>
        </Media>
      </Row>
      <h4>
        <Translate contentKey="noRiskNoFunApp.userGamification.activityGraphTitle" />
      </h4>
      <UserActivityGraph history={props.history} location={props.location} match={props.match} user={props.user}
                         currentLogin={props.currentLogin} userGamificationEntitiy={userGamificationEntitiy}
                         getUser={props.getUser} getUserGamification={props.getUserGamification}/>

      <h4>
        <Translate contentKey="noRiskNoFunApp.userGamification.achievementsTitle" />
      </h4>
      <div>{achievementCards}</div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState, {userGamification}: IRootState) => ({
  user: storeState.userManagement.user,
  currentLogin: storeState.authentication.account.login,
  userGamificationEntitiy: storeState.userGamification.entity
});

const mapDispatchToProps = {
  getUser,
  getUserGamification
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Profile);
