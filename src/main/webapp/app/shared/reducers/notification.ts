import axios from 'axios';

import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

export const ACTION_TYPES = {
  SAVE_DEVICE_TOKEN: 'notification/SAVE_DEVICE_TOKEN'
};

const initialState = {
  loading: false,
  errorMessage: null as string // Errors returned from server side
};

export type NotificationState = Readonly<typeof initialState>;

// Reducer

export default (state: NotificationState = initialState, action): NotificationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SAVE_DEVICE_TOKEN):
      return {
        ...state,
        loading: true
      };
    case FAILURE(ACTION_TYPES.SAVE_DEVICE_TOKEN):
      return {
        ...initialState,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SAVE_DEVICE_TOKEN):
      return {
        ...state,
        loading: false
      };
    default:
      return state;
  }
};

export const sendDeviceToken = (deviceToken: string) => async dispatch => {
  await dispatch({
    type: ACTION_TYPES.SAVE_DEVICE_TOKEN,
    payload: axios.post('api/deviceToken', { token: deviceToken })
  });
};
