import React, { useEffect } from 'react';
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
import Achievements from "app/entities/user-gamification/achievements";
import {IconDefinition} from "@fortawesome/fontawesome-common-types";
import {faMedal} from '@fortawesome/free-solid-svg-icons/faMedal';
import {faTrophy} from '@fortawesome/free-solid-svg-icons/faTrophy';
import {faUsers} from '@fortawesome/free-solid-svg-icons/faUsers';
import {faCrown} from '@fortawesome/free-solid-svg-icons/faCrown';
import {faAward} from '@fortawesome/free-solid-svg-icons/faAward';

// TODO: needs to be removed later when redux actions is correctly implemented
interface AchievementMock {
  title: string;
  description: string;
  icon: IconDefinition;
}
const achievementList: Array<AchievementMock> = [
  { title: 'Project member', description: 'Achievement for being a respectable project member.', icon: faUsers },
  { title: 'Risk owner', description: 'Achievement for owning TODO: XYZ risks (being the person in charge).', icon: faTrophy },
  { title: 'Risk sage', description: 'Achievement for reviewing and contributing TODO: XYZ risks.', icon: faAward },
  { title: 'Risk master', description: 'Achievement being active part of TODO: XYZ risk ranking processes.', icon: faAward },
  { title: 'Risk buster', description: 'Achievement for successful contribution of TODO: XYZ risk responses.', icon: faMedal },
  { title: 'Project manager', description: 'Your are a project manager!', icon: faCrown }
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
      <Row>
        <Achievements personalAchievementList={achievementList}/>
      </Row>
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
