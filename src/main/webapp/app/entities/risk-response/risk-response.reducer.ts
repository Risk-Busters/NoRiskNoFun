import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRiskResponse, defaultValue } from 'app/shared/model/risk-response.model';

export const ACTION_TYPES = {
  FETCH_RISKRESPONSE_LIST: 'riskResponse/FETCH_RISKRESPONSE_LIST',
  FETCH_RISKRESPONSE: 'riskResponse/FETCH_RISKRESPONSE',
  CREATE_RISKRESPONSE: 'riskResponse/CREATE_RISKRESPONSE',
  UPDATE_RISKRESPONSE: 'riskResponse/UPDATE_RISKRESPONSE',
  DELETE_RISKRESPONSE: 'riskResponse/DELETE_RISKRESPONSE',
  RESET: 'riskResponse/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRiskResponse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RiskResponseState = Readonly<typeof initialState>;

// Reducer

export default (state: RiskResponseState = initialState, action): RiskResponseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RISKRESPONSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RISKRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RISKRESPONSE):
    case REQUEST(ACTION_TYPES.UPDATE_RISKRESPONSE):
    case REQUEST(ACTION_TYPES.DELETE_RISKRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RISKRESPONSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RISKRESPONSE):
    case FAILURE(ACTION_TYPES.CREATE_RISKRESPONSE):
    case FAILURE(ACTION_TYPES.UPDATE_RISKRESPONSE):
    case FAILURE(ACTION_TYPES.DELETE_RISKRESPONSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RISKRESPONSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RISKRESPONSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RISKRESPONSE):
    case SUCCESS(ACTION_TYPES.UPDATE_RISKRESPONSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RISKRESPONSE):
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

const apiUrl = 'api/risk-responses';

// Actions

export const getEntities: ICrudGetAllAction<IRiskResponse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RISKRESPONSE_LIST,
  payload: axios.get<IRiskResponse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRiskResponse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RISKRESPONSE,
    payload: axios.get<IRiskResponse>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRiskResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RISKRESPONSE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRiskResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RISKRESPONSE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRiskResponse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RISKRESPONSE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
