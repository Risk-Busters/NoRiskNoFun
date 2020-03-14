import axios from 'axios';
import { ICrudGetAction } from 'react-jhipster';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IProjectActivity } from 'app/shared/model/project-activity.model';

export const ACTION_TYPES = {
  FETCH_PROJECT_ACTIVITY: 'project/FETCH_PROJECT_ACTIVITY',
  RESET: 'project/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjectActivity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProjectActivityState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjectActivityState = initialState, action): ProjectActivityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJECT_ACTIVITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECT_ACTIVITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECT_ACTIVITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrlProjectActivity = 'api/project-activity';

// Actions

export const getProjectActivity: ICrudGetAction<IProjectActivity> = id => {
  const requestUrl = `${apiUrlProjectActivity}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJECT_ACTIVITY,
    payload: axios.get<IProjectActivity>(requestUrl)
  };
};
