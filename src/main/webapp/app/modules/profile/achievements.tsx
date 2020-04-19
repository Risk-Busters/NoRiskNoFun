import { Card, CardText, CardBody, CardHeader, Container, Col, Row } from 'reactstrap';
import React from 'react';
import { Translate } from 'react-jhipster';
import './achievements.scss';


export interface IAchievementProps {
  userAchievements: Array<{}>;
}


export const AchievementContainer = (props: IAchievementProps) => {

  const achievements = new Map([
    ['PROJECT_MEMBER', { Description: 'memberDescription', Icon: 'ProjectMemberBadge.svg' }],
    ['PROJECT_MANAGER', { Description: 'pmDescription', Icon: 'ProjectManagerBadge.svg' }]
  ]);

  function buildAchievementTile(AchievementTitle) {
    const achievement = achievements.get(AchievementTitle.name);
    return (
        <Card className={'AchievementCard'}>
          <CardHeader><Translate
            contentKey={`noRiskNoFunApp.userGamification.achievementNames.${AchievementTitle.name}`}/></CardHeader>
          <CardBody>
            <img src={`/content/images/${achievement.Icon}`} alt="Achievement"/>
            <CardText><Translate
              contentKey={`noRiskNoFunApp.userGamification.achievementDescriptions.${achievement.Description}`}/></CardText>
          </CardBody>
        </Card>
    );
  }

  return (
      <div className={'AchievementContainer'}>
        {props.userAchievements ? props.userAchievements.map(buildAchievementTile) : 'No Achievements'}
      </div>
  );
};
