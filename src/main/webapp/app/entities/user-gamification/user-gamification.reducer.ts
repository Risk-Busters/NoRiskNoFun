import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';
import { IUserGamification, defaultValue } from 'app/shared/model/user-gamification.model';
export const ACTION_TYPES = {
  FETCH_USERGAMIFICATION_LIST: 'userGamification/FETCH_USERGAMIFICATION_LIST',
  FETCH_USERGAMIFICATION: 'userGamification/FETCH_USERGAMIFICATION',
  CREATE_USERGAMIFICATION: 'userGamification/CREATE_USERGAMIFICATION',
  UPDATE_USERGAMIFICATION: 'userGamification/UPDATE_USERGAMIFICATION',
  DELETE_USERGAMIFICATION: 'userGamification/DELETE_USERGAMIFICATION',
  RESET: 'userGamification/RESET'
};
const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserGamification>,
  achievements: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};
export type UserGamificationState = Readonly<typeof initialState>;
// Reducer
export default (state: UserGamificationState = initialState, action): UserGamificationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERGAMIFICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERGAMIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERGAMIFICATION):
    case REQUEST(ACTION_TYPES.UPDATE_USERGAMIFICATION):
    case REQUEST(ACTION_TYPES.DELETE_USERGAMIFICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERGAMIFICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERGAMIFICATION):
    case FAILURE(ACTION_TYPES.CREATE_USERGAMIFICATION):
    case FAILURE(ACTION_TYPES.UPDATE_USERGAMIFICATION):
    case FAILURE(ACTION_TYPES.DELETE_USERGAMIFICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERGAMIFICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERGAMIFICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERGAMIFICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_USERGAMIFICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERGAMIFICATION):
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
const apiUrl = 'api/user-gamifications';
// Actions
export const getEntities: ICrudGetAllAction<IUserGamification> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERGAMIFICATION_LIST,
  payload: axios.get<IUserGamification>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});
export const getEntity: ICrudGetAction<IUserGamification> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERGAMIFICATION,
    payload: axios.get<IUserGamification>(requestUrl)
  };
};
export const createEntity: ICrudPutAction<IUserGamification> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERGAMIFICATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};
export const updateEntity: ICrudPutAction<IUserGamification> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERGAMIFICATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};
export const deleteEntity: ICrudDeleteAction<IUserGamification> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERGAMIFICATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};
export const reset = () => ({
  type: ACTION_TYPES.RESET
});
