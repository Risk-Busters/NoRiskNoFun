import React from 'react';
import {Card, CardBody, CardSubtitle, CardText, CardTitle, Col} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IconDefinition} from '@fortawesome/fontawesome-svg-core';
import {faMedal} from '@fortawesome/free-solid-svg-icons/faMedal';
import {faTrophy} from '@fortawesome/free-solid-svg-icons/faTrophy';
import {faUsers} from '@fortawesome/free-solid-svg-icons/faUsers';
import {faCrown} from '@fortawesome/free-solid-svg-icons/faCrown';
import {faAward} from '@fortawesome/free-solid-svg-icons/faAward';

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

// TODO: adapt: liste mit achievents als props übergeben
export interface IAchievementsProps {personalAchievementList: Array<AchievementMock>}

function Achievements(props: IAchievementsProps) {

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

  return achievementCards(achievementList);
}

export default Achievements;
