import * as firebase from 'firebase/app';
import 'firebase/messaging';
import { toast } from 'react-toastify';

import { firebaseConfig } from 'app/config/constants';
import { sendDeviceToken } from 'app/shared/reducers/notification';

let messaging;

export function initFirebaseMessaging() {
  firebase.initializeApp(firebaseConfig);
  messaging = firebase.messaging();
}
export const pushAPI: boolean = 'PushManager' in window;

export const isPermissionGranted = (): boolean => {
  if (pushAPI) {
    if (Notification.permission === 'granted') {
      return true;
    } else if (Notification.permission === 'denied') {
      return true;
    }
    return false;
  }
  // If PushManager does not exists return true to avoid further processing
  return true;
};

export const requestNotificationPermission = (store): boolean => {
  messaging
    .getToken()
    .then(refreshedToken => {
      store.dispatch(sendDeviceToken(refreshedToken));
      return true;
    })
    .catch(err => {
      console.log('Unable to retrieve refreshed token ', err);
      return true;
    });
  return true;
};

export const onTokenRefreshHandler = store => {
  messaging.onTokenRefresh(() => {
    messaging
      .getToken()
      .then(refreshedToken => {
        store.dispatch(sendDeviceToken(refreshedToken));
      })
      .catch(err => {
        console.log('Unable to retrieve refreshed token ', err);
      });
  });
};

export const onMessageHandler = () => {
  messaging.onMessage(payload => {
    toast.info(payload.data.message);
  });
};
