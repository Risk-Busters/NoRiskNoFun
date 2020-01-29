import React from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {getItemType, translate, Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {getUsers} from 'app/modules/administration/user-management/user-management.reducer';
import {createEntity, getEntity, reset, updateEntity} from './user-gamification.reducer';
import { AchievementType } from 'app/shared/model/enumerations/achievment-type.model';
import {mapIdList} from "app/shared/util/entity-utils";
import value from "*.json";
import {IUserGamification} from "app/shared/model/user-gamification.model";

export interface IUserGamificationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export interface IUserGamificationUpdateState {
  isNew: boolean;
  idsachievementType: any[];
  userId: string;
}

export class UserGamificationUpdate extends React.Component<IUserGamificationUpdateProps, IUserGamificationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsachievementType: [],
      userId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  UNSAFE_componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const {userGamificationEntity} = this.props;
      const achievements: { type: AchievementType; name: string }[] = [];
      values.userAchievements.forEach(achievement => {
        achievements.push({type: AchievementType[achievement], name: achievement})
      });
      values.userAchievements = achievements;

      const entity = {
        ...userGamificationEntity,
          ...values
      };

      console.log(entity);

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/user-gamifications');
  };

  render() {
    const {userGamificationEntity, users, loading, updating} = this.props;
    const {isNew} = this.state;

    // TODO: typisierung mit interface hier und im userGamification interface
    // TODO: auslagern aus der Komponente in das Model

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="noRiskNoFunApp.userGamification.home.createOrEditLabel">
              <Translate contentKey="noRiskNoFunApp.userGamification.home.createOrEditLabel">Create or edit a
                UserGamification</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userGamificationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="user-gamification-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="user-gamification-id" type="text" className="form-control" name="id" required
                             readOnly/>
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="pointsScoreLabel" for="user-gamification-pointsScore">
                    <Translate contentKey="noRiskNoFunApp.userGamification.pointsScore">Points Score</Translate>
                  </Label>
                  <AvField id="user-gamification-pointsScore" type="string" className="form-control"
                           name="pointsScore"/>
                </AvGroup>
                <AvGroup>
                  <Label for="user-gamification-user">
                    <Translate contentKey="noRiskNoFunApp.userGamification.user">User</Translate>
                  </Label>
                  <AvInput id="user-gamification-user" type="select" className="form-control" name="userId">
                    <option value="" key="0"/>
                    {users
                      ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
                        </option>
                      ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="user-gamification-achievementType">
                    <Translate contentKey="noRiskNoFunApp.userGamification.achievementType">Achievement Type</Translate>
                  </Label>
                  <AvInput
                    id="user-gamification-achievementType"
                    type="select"
                    multiple
                    className="form-control"
                    name="userAchievements"
                    value={(!isNew && userGamificationEntity.userAchievements) || 'PROJECT_MEMBER'}
                  >
                    <option
                      value="PROJECT_MEMBER">{translate('noRiskNoFunApp.userGamification.achievements.PROJECT_MEMBER')}</option>
                    <option
                      value="RISK_SAGE">{translate('noRiskNoFunApp.userGamification.achievements.RISK_SAGE')}</option>
                    <option
                      value="RISK_OWNER">{translate('noRiskNoFunApp.userGamification.achievements.RISK_OWNER')}</option>
                    <option
                      value="RISK_MASTER">{translate('noRiskNoFunApp.userGamification.achievements.RISK_MASTER')}</option>
                    <option
                      value="RISK_BUSTER">{translate('noRiskNoFunApp.userGamification.achievements.RISK_BUSTER')}</option>
                    <option
                      value="PROJECT_MANAGER">{translate('noRiskNoFunApp.userGamification.achievements.PROJECT_MANAGER')}</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-gamifications" replace color="info">
                  <FontAwesomeIcon icon="arrow-left"/>
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save"/>
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  userGamificationEntity: storeState.userGamification.entity,
  loading: storeState.userGamification.loading,
  updating: storeState.userGamification.updating,
  updateSuccess: storeState.userGamification.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserGamificationUpdate);
