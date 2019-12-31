import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProjectRisks, defaultValue } from 'app/shared/model/project-risks.model';

export const ACTION_TYPES = {
  FETCH_PROJECTRISKS_LIST: 'projectRisks/FETCH_PROJECTRISKS_LIST',
  FETCH_PROPOSEDPROJECTRISKS_LIST: 'projectRisks/FETCH_PROPOSEDPROJECTRISKS_LIST',
  FETCH_PROJECTRISKS: 'projectRisks/FETCH_PROJECTRISKS',
  CREATE_PROJECTRISKS: 'projectRisks/CREATE_PROJECTRISKS',
  UPDATE_PROJECTRISKS: 'projectRisks/UPDATE_PROJECTRISKS',
  DELETE_PROJECTRISKS: 'projectRisks/DELETE_PROJECTRISKS',
  RESET: 'projectRisks/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProjectRisks>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProjectRisksState = Readonly<typeof initialState>;

// Reducer

export default (state: ProjectRisksState = initialState, action): ProjectRisksState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROJECTRISKS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROJECTRISKS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROJECTRISKS):
    case REQUEST(ACTION_TYPES.UPDATE_PROJECTRISKS):
    case REQUEST(ACTION_TYPES.DELETE_PROJECTRISKS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECTRISKS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.CREATE_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.UPDATE_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.DELETE_PROJECTRISKS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTRISKS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTRISKS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJECTRISKS):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJECTRISKS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROJECTRISKS):
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

const apiUrl = 'api/project-risks';
const apiUrlProposedProjectRisks = 'api/proposed-project-risks';

// Actions

export const getEntities: ICrudGetAllAction<IProjectRisks> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJECTRISKS_LIST,
  payload: axios.get<IProjectRisks>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getProposedProjectRisks: ICrudGetAllAction<IProjectRisks> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST,
  payload: axios.get<IProjectRisks>(`${apiUrlProposedProjectRisks}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProjectRisks> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROJECTRISKS,
    payload: axios.get<IProjectRisks>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProjectRisks> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROJECTRISKS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProjectRisks> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTRISKS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProjectRisks> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROJECTRISKS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
