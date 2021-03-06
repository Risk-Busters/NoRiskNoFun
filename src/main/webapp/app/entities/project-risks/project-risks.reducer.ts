import axios from 'axios';
import { ICrudDeleteAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { FAILURE, REQUEST, SUCCESS } from 'app/shared/reducers/action-type.util';

import { defaultValue, IProjectRisks, IRiskDiscussionVM } from 'app/shared/model/project-risks.model';

export const ACTION_TYPES = {
  FETCH_PROJECTRISKS_LIST: 'projectRisks/FETCH_PROJECTRISKS_LIST',
  FETCH_PROPOSEDPROJECTRISKS_LIST: 'projectRisks/FETCH_PROPOSEDPROJECTRISKS_LIST',
  FETCH_TOBEDISCUSSEDPROJECTRISKS_LIST: 'projectRisks/FETCH_TOBEDISCUSSEDPROJECTRISKS_LIST',
  FETCH_PROJECTRISKS: 'projectRisks/FETCH_PROJECTRISKS',
  CREATE_PROJECTRISKS: 'projectRisks/CREATE_PROJECTRISKS',
  UPDATE_PROJECTRISKS: 'projectRisks/UPDATE_PROJECTRISKS',
  DELETE_PROJECTRISKS: 'projectRisks/DELETE_PROJECTRISKS',
  ADD_LIKE_TO_PROJECT_RISK: 'projectRisks/ADD_LIKE_TO_PROJECT_RISK',
  ADD_PERSON_IN_CHARGE_FOR_PROJECT_RISK: 'projectRisks/ADD_PERSON_IN_CHARGE_FOR_PROJECT_RISK',
  RESET: 'projectRisks/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  projectRiskEntities: [] as ReadonlyArray<IProjectRisks>,
  proposedProjectRiskEntities: [] as ReadonlyArray<IProjectRisks>,
  toBeDiscussedProjectRiskEntities: [] as ReadonlyArray<IProjectRisks>,
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
    case REQUEST(ACTION_TYPES.FETCH_TOBEDISCUSSEDPROJECTRISKS_LIST):
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
    case REQUEST(ACTION_TYPES.ADD_LIKE_TO_PROJECT_RISK):
    case REQUEST(ACTION_TYPES.ADD_PERSON_IN_CHARGE_FOR_PROJECT_RISK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROJECTRISKS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TOBEDISCUSSEDPROJECTRISKS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.CREATE_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.UPDATE_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.DELETE_PROJECTRISKS):
    case FAILURE(ACTION_TYPES.ADD_LIKE_TO_PROJECT_RISK):
    case FAILURE(ACTION_TYPES.ADD_PERSON_IN_CHARGE_FOR_PROJECT_RISK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST):
      return {
        ...state,
        loading: false,
        proposedProjectRiskEntities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TOBEDISCUSSEDPROJECTRISKS_LIST):
      return {
        ...state,
        loading: false,
        toBeDiscussedProjectRiskEntities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTRISKS_LIST):
      return {
        ...state,
        loading: false,
        projectRiskEntities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROJECTRISKS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROJECTRISKS):
    case SUCCESS(ACTION_TYPES.UPDATE_PROJECTRISKS):
    case SUCCESS(ACTION_TYPES.ADD_LIKE_TO_PROJECT_RISK):
    case SUCCESS(ACTION_TYPES.ADD_PERSON_IN_CHARGE_FOR_PROJECT_RISK):
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
const apiUrlDiscussProjectRisks = 'api/discuss-project-risks';
const apiUrlDiscussion = 'api/project-risks-discussion';
const apiUrlAddLike = 'api/like-project-risks';
const apiUrlAddPersonInCharge = 'api/person-in-charge-project-risks';

// Actions

export const getEntities: ICrudGetAllAction<IProjectRisks> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROJECTRISKS_LIST,
  payload: axios.get<IProjectRisks>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getProposedProjectRisks: ICrudGetAllAction<IProjectRisks> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROPOSEDPROJECTRISKS_LIST,
  payload: axios.get<IProjectRisks>(`${apiUrlProposedProjectRisks}?cacheBuster=${new Date().getTime()}`)
});

export const getToBeDiscussedProjectRisks: ICrudGetAllAction<IProjectRisks> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TOBEDISCUSSEDPROJECTRISKS_LIST,
  payload: axios.get<IProjectRisks>(`${apiUrlDiscussProjectRisks}?cacheBuster=${new Date().getTime()}`)
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

export const createDiscussion: ICrudPutAction<IRiskDiscussionVM> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTRISKS,
    payload: axios.post(apiUrlDiscussion, cleanEntity(entity))
  });
  dispatch(getEntities());
  dispatch(getProposedProjectRisks());
  dispatch(getToBeDiscussedProjectRisks());
  return result;
};

export const updateEntity: ICrudPutAction<IProjectRisks> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTRISKS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  dispatch(getProposedProjectRisks());
  dispatch(getToBeDiscussedProjectRisks());
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

export const addLikeForProjectRisk: ICrudPutAction<IProjectRisks> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.ADD_LIKE_TO_PROJECT_RISK,
    payload: axios.post(apiUrlAddLike, cleanEntity(entity))
  });
  dispatch(getEntities());
  dispatch(getProposedProjectRisks());
  dispatch(getToBeDiscussedProjectRisks());
  return result;
};

export const addPersonInChargeForProjectRisk: ICrudPutAction<IProjectRisks> = entity => async dispatch => {
  return await dispatch({
    type: ACTION_TYPES.UPDATE_PROJECTRISKS,
    payload: axios.post(apiUrlAddPersonInCharge, cleanEntity(entity))
  });
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
