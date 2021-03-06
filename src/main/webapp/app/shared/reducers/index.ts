import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import risk, {RiskState} from 'app/entities/risk/risk.reducer';
// prettier-ignore
import riskResponse, {RiskResponseState} from 'app/entities/risk-response/risk-response.reducer';
// prettier-ignore
import project, {ProjectState} from 'app/entities/project/project.reducer';
// prettier-ignore
import projectActivity, {ProjectActivityState} from 'app/entities/project/project-activity.reducer';
// prettier-ignore
import projectRisks, {ProjectRisksState} from 'app/entities/project-risks/project-risks.reducer';
import userGamification, { UserGamificationState } from 'app/entities/user-gamification/user-gamification.reducer';
import activity, { ActivityState } from 'app/entities/activity/activity.reducer';
import notification, { NotificationState } from 'app/shared/reducers/notification';

/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly risk: RiskState;
  readonly riskResponse: RiskResponseState;
  readonly project: ProjectState;
  readonly projectActivity: ProjectActivityState;
  readonly projectRisks: ProjectRisksState;
  readonly userGamification: UserGamificationState;
  readonly activity: ActivityState;
  readonly notification: NotificationState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  risk,
  riskResponse,
  project,
  projectActivity,
  projectRisks,
  userGamification,
  activity,
  notification,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
