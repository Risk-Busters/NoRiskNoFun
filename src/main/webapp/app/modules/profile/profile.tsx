import React, { useState, useEffect } from 'react';
import { getUser } from '../administration/user-management/user-management.reducer';
import { IRootState } from 'app/shared/reducers';
import { RouteComponentProps } from 'react-router-dom';
import { connect } from 'react-redux';
import { Translate } from 'react-jhipster';
import { Badge, Media, Row, Card, CardText, CardBody, CardTitle, CardSubtitle, Col } from 'reactstrap';
import './profile.scss';
import { languages } from 'app/config/translation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLanguage } from '@fortawesome/free-solid-svg-icons/faLanguage';
import { faCalendarAlt } from '@fortawesome/free-solid-svg-icons/faCalendarAlt';
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { faMedal } from '@fortawesome/free-solid-svg-icons/faMedal';
import { faTrophy } from '@fortawesome/free-solid-svg-icons/faTrophy';

// TODO: Interface and Achievement List to be replaced when Gamification Concept is done.
interface AchievementMock {
  title: string;
  description: string;
  icon: IconDefinition;
}

const achievementList: Array<AchievementMock> = [
  { title: 'Cool Dude', description: 'Got this achievement since this person is the coolest person of all persons alive.', icon: faMedal },
  { title: 'Poor Dev', description: 'Got this achievement as this person was brave enough to touch the code.', icon: faTrophy }
];

export interface IProfileProps extends StateProps, DispatchProps, RouteComponentProps<{ login?: string }> {}

export const Profile = (props: IProfileProps) => {
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

  const { user } = props;

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
                <FontAwesomeIcon icon={achievement.icon} size="10x" />
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
                <FontAwesomeIcon icon={faCalendarAlt} size="lg" />{' '}
                <Translate contentKey="profile.home.memberSince">Member since </Translate>{' '}
                <b>{convertDateTimeFromServer(user.createdDate)}</b>
                <br />
              </span>
            )}
            {user.langKey && (
              <span>
                <FontAwesomeIcon icon={faLanguage} size="lg" /> <Translate contentKey="profile.home.languages">Speaks </Translate>{' '}
                <b>{languages['en'].name}</b>
              </span>
            )}
          </Media>
        </Media>
      </Row>
      <h4>Achievements</h4>
      <Row>{achievementCards(achievementList)}</Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  user: storeState.userManagement.user,
  currentLogin: storeState.authentication.account.login
});

const mapDispatchToProps = { getUser };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Profile);
