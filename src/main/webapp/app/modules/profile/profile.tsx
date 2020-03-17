import React, {useEffect, useState} from 'react';
import {getUser} from '../administration/user-management/user-management.reducer';
import {IRootState} from 'app/shared/reducers';
import {RouteComponentProps} from 'react-router-dom';
import {connect} from 'react-redux';
import {Translate} from 'react-jhipster';
import {Badge, Card, CardBody, CardSubtitle, CardText, CardTitle, Col, Media, Row, Spinner} from 'reactstrap';
import './profile.scss';
import {languages} from 'app/config/translation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faLanguage} from '@fortawesome/free-solid-svg-icons/faLanguage';
import {faCalendarAlt} from '@fortawesome/free-solid-svg-icons/faCalendarAlt';
import {convertDateTimeFromServer} from 'app/shared/util/date-utils';
import {IconDefinition} from '@fortawesome/fontawesome-svg-core';
import {faMedal} from '@fortawesome/free-solid-svg-icons/faMedal';
import {faTrophy} from '@fortawesome/free-solid-svg-icons/faTrophy';
import {faUsers} from '@fortawesome/free-solid-svg-icons/faUsers';
import {faCrown} from '@fortawesome/free-solid-svg-icons/faCrown';
import {faAward} from '@fortawesome/free-solid-svg-icons/faAward';
import {getUserGamification} from "app/entities/user-gamification/user-gamification.reducer";
import {Chart} from "react-google-charts";
import {IUserGamification} from "app/shared/model/user-gamification.model";

// TODO: Interface and Achievement List to be replaced when Gamification Concept is done.
interface AchievementMock {
  title: string;
  description: string;
  icon: IconDefinition;
}

// TODO: adapt amounts
const achievementList: Array<AchievementMock> = [
  { title: 'Project member', description: 'Achievement for being a respectable project member.', icon: faUsers },
  { title: 'Risk owner', description: 'Achievement for owning TODO: XYZ risks (being the person in charge).', icon: faTrophy },
  { title: 'Risk sage', description: 'Achievement for reviewing and contributing TODO: XYZ risks.', icon: faAward },
  { title: 'Risk master', description: 'Achievement being active part of TODO: XYZ risk ranking processes.', icon: faAward },
  { title: 'Risk buster', description: 'Achievement for successful contribution of TODO: XYZ risk responses.', icon: faMedal },
  { title: 'Project manager', description: 'Your are a project manager!', icon: faCrown }
];

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

  const achievementCards = (achievements: Array<AchievementMock>) => {
    return achievements.map((achievement, index) => {
      return (
        <Col key={`achievement-id-${index}`} sm="6" md="4" lg="3">
          <Card className="achievement">
            <CardBody>
              <CardTitle>
                <FontAwesomeIcon icon={achievement.icon} size="10x"/>
              </CardTitle>
              <CardSubtitle>
                <b>{achievement.title}</b>
              </CardSubtitle>
              <CardText>{achievement.description}</CardText>
            </CardBody>
          </Card>
        </Col>
      );
    });
  };

  return (
    <div>
      <Row>
        <Media>
          <Media left href="">
            <Media
              object
              src="../../../content/images/jhipster_family_member_0_head-192.png"
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
      <h4>Your Activity</h4>
      <Chart
        width={'500px'}
        height={'300px'}
        chartType="AreaChart"
        loader={<Spinner color="primary"/>}
        data={diagramStatus}
        options={{
          title: 'Your Activity',
          hAxis: {title: 'Time', titleTextStyle: {color: '#333'}},
          vAxis: {title: 'Activity', minValue: 0},
          // For the legend to fit, we make the chart area smaller
          chartArea: {width: '50%', height: '70%'},
          // lineWidth: 25
        }}
        // For tests
        rootProps={{'data-testid': '1'}}
      />

      <h4>Achievements</h4>
      <Row>{achievementCards(achievementList)}</Row>
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
