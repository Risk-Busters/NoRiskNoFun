import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IActivity } from 'app/shared/model/activity.model';

export const ACTION_TYPES = {
  FETCH_ACTIVITY_LIST: 'activity/FETCH_ACTIVITY_LIST',
  FETCH_ACTIVITY: 'activity/FETCH_ACTIVITY',
  DELETE_ACTIVITY: 'activity/DELETE_ACTIVITY',
  RESET: 'activity/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IActivity>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ActivityState = Readonly<typeof initialState>;

// Reducer

export default (state: ActivityState = initialState, action): ActivityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACTIVITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACTIVITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.DELETE_ACTIVITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACTIVITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACTIVITY):
    case FAILURE(ACTION_TYPES.DELETE_ACTIVITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACTIVITY_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ACTIVITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACTIVITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/activities';

// Actions

export const getEntities: ICrudGetAllAction<IActivity> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACTIVITY_LIST,
    payload: axios.get<IActivity>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IActivity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACTIVITY,
    payload: axios.get<IActivity>(requestUrl)
  };
};

export const deleteEntity: ICrudDeleteAction<IActivity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACTIVITY,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
